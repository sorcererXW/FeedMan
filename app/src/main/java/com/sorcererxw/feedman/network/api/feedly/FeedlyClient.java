package com.sorcererxw.feedman.network.api.feedly;

import android.content.Context;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.AccessToken;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import rx.Observable;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class FeedlyClient {
    private FeedlyService mFeedlyService;
    private String mEndpoint;
    private String mClientId;
    private String mClientSecret;
    private String mAuthRedirectUrl;
    private String mScope;
    private Account mAccount;
    private AccessToken mAccessToken;

    public FeedlyClient(Context context) {
        this(context, null);
    }

    public FeedlyClient(Context context, Account account) {
        mEndpoint = "https://" + context.getString(R.string.feedly_endpoint);
        mClientId = context.getString(R.string.feedly_client_id);
        mClientSecret = context.getString(R.string.feedly_client_secret);
        mAuthRedirectUrl = context.getString(R.string.feedly_redirect_url);
        mScope = context.getString(R.string.feedly_scope);

        if (account != null) {
            mAccount = account;
            mAccessToken = account.getAccessToken();
        }

        mFeedlyService = new Retrofit.Builder()
                .baseUrl(mEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                if (mAccessToken != null) {
                                    request = request.newBuilder()
                                            .addHeader("Authorization",
                                                    "OAuth " + mAccessToken.getAccessToken())
                                            .build();
                                }
                                return chain.proceed(request);
                            }
                        }).build()
                )
                .build()
                .create(FeedlyService.class);
    }

    public String getAuthenticationUrl() {
        return mEndpoint + "/v3/auth/auth?" + "response_type=code" + "&client_id=" + mClientId
                + "&redirect_uri=" + mAuthRedirectUrl + "&scope=" + mScope;
    }

    public String getAuthenticationRedirectUrl() {
        return mAuthRedirectUrl;
    }

    public Observable<Account> authenticate(String authenticationCode) {
        return mFeedlyService.getAccessToken(
                authenticationCode,
                mClientId,
                mClientSecret,
                mAuthRedirectUrl,
                "authorization_code"
        )
                .flatMap(new Func1<AccessToken, Observable<FeedlyProfile>>() {
                    @Override
                    public Observable<FeedlyProfile> call(AccessToken token) {
                        KLog.d(token.toString());
                        mAccessToken = token;
                        return mFeedlyService.getProfile();
                    }
                })
                .flatMap(new Func1<FeedlyProfile, Observable<Account>>() {
                    @Override
                    public Observable<Account> call(FeedlyProfile profile) {
                        String accountLabel = profile.getEmail();
                        if (TextUtils.isEmpty(accountLabel)) {
                            if (profile.getGivenName() != null && profile.getFamilyName() != null) {
                                accountLabel =
                                        profile.getGivenName() + " " + profile.getFamilyName();
                            } else if (profile.getTwitter() != null) {
                                accountLabel = "@" + profile.getTwitter();
                            }
                        }
                        mAccount = new Account.Builder()
                                .accessToken(mAccessToken)
                                .id(profile.getId())
                                .label(accountLabel)
                                .build();
                        return Observable.just(mAccount);
                    }
                });
    }

    public Observable<AccessToken> authenticateTest(String authenticationCode) {
        return mFeedlyService.getAccessToken(
                authenticationCode,
                mClientId,
                mClientSecret,
                mAuthRedirectUrl,
                "authorization_code"
        );
    }

    public Observable<List<FeedlySubscription>> getSubscriptions() {
        return mFeedlyService.getSubscriptions();
    }

    public Observable<FeedlyStream> getFeedSteam(String streamId, int count, boolean unreadOnly) {
        return mFeedlyService.getFeedStream(streamId, count, unreadOnly);
    }

    public static class UnreadContentResponse {
        private final String mContinuation;
        private final List<FeedlyEntry> mEntries;

        public UnreadContentResponse(List<FeedlyEntry> entries, String continuation) {
            mContinuation = continuation;
            mEntries = entries;
        }

        public String getContinuation() {
            return mContinuation;
        }

        public List<FeedlyEntry> getEntries() {
            return mEntries;
        }
    }

    public UnreadContentResponse getUnreadContent(long newerThan, String continuation)
            throws IOException {
        Call<FeedlyStream> call;
        String id = "/user/" + mAccount.getId() + "/category/global.all";
        List<FeedlyEntry> entryList = new ArrayList<>();
        if (continuation != null) {
            if (newerThan > 0) {
                call = mFeedlyService.getFeedStreamCall(id, 400, true, newerThan, continuation);
            } else {
                call = mFeedlyService.getFeedStreamCall(id, 400, true, continuation);
            }
        } else {
            if (newerThan > 0) {
                call = mFeedlyService.getFeedStreamCall(id, 400, true, newerThan);
            } else {
                call = mFeedlyService.getFeedStreamCall(id, 400, true);
            }
        }
        FeedlyStream stream = NetworkUtil.executeApiCall(call);
        for (FeedlyEntry entry : stream.getItems()) {
            entryList.add(entry);
        }
        return new UnreadContentResponse(entryList, stream.getContinuation());
    }

    public Observable<String[]> markAsRead(List<String> entryIds) {
        return updateReadStatus(FeedlyReadMarker.read((String[]) entryIds.toArray()));
    }

    public Observable<String[]> markAsUnread(List<String> entryIds) {
        return updateReadStatus(FeedlyReadMarker.unread((String[]) entryIds.toArray()));
    }

    private Observable<String[]> updateReadStatus(final FeedlyReadMarker marker) {
        return mFeedlyService.updateEntriesStatus(marker)
                .flatMap(new Func1<Void, Observable<String[]>>() {
                    @Override
                    public Observable<String[]> call(Void aVoid) {
                        return Observable.just(marker.getEntryIds());
                    }
                });
    }

    public Observable<String> markAsStarred(final String entryId) {
        return mFeedlyService.updateEntriesStatus(FeedlyReadMarker.starred(entryId))
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return Observable.just(entryId);
                    }
                });
    }

    public Observable<String> markAsUnstarred(final String entryId) {
        return mFeedlyService.updateEntriesStatus(FeedlyReadMarker.unstarred(entryId))
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return Observable.just(entryId);
                    }
                });
    }

}
