package com.sorcererxw.feedman.network.api.feedly;

import android.content.Context;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.database.tables.AccountTable;
import com.sorcererxw.feedman.models.AccessToken;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.models.FeedCategory;
import com.sorcererxw.feedman.models.FeedEntry;
import com.sorcererxw.feedman.models.FeedSubscription;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyAccessToken;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyCategory;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyEntry;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyProfile;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyReadMarker;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyStream;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlySubscription;
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
            mAccessToken = account.accessToken();
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
                                                    "OAuth " + mAccessToken.accessToken())
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
                .flatMap(new Func1<FeedlyAccessToken, Observable<FeedlyProfile>>() {
                    @Override
                    public Observable<FeedlyProfile> call(FeedlyAccessToken token) {
                        KLog.d(token.toString());
                        mAccessToken = AccessToken.from(token);
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
                        mAccount = Account.from(mAccessToken, profile.getId(), accountLabel);
                        return Observable.just(mAccount);
                    }
                });
    }

    public AccessToken refreshAccessToken(AccessToken currentAccessToken) throws IOException {
        return AccessToken.from(
                NetworkUtil.executeApiCall(mFeedlyService.refreshAccessTokenCall(
                        currentAccessToken.refreshToken(),
                        mClientId,
                        mClientSecret,
                        AccountTable.REFRESH_TOKEN)).getAccessToken(),
                currentAccessToken.refreshToken());
    }

    public List<FeedCategory> getCategory() throws IOException {
        List<FeedCategory> categoryList = new ArrayList<>();
        for (FeedlyCategory feedlyCategory :
                NetworkUtil.executeApiCall(mFeedlyService.getCategories())) {
            categoryList.add(FeedCategory.from(mAccount, feedlyCategory));
        }
        return categoryList;
    }

    public Observable<List<FeedSubscription>> getSubscriptions() {
        return mFeedlyService.getSubscriptions().flatMapIterable(
                new Func1<List<FeedlySubscription>, Iterable<FeedlySubscription>>() {
                    @Override
                    public Iterable<FeedlySubscription> call(
                            List<FeedlySubscription> feedlySubscriptions) {
                        return feedlySubscriptions;
                    }
                })
                .map(new Func1<FeedlySubscription, FeedSubscription>() {
                    @Override
                    public FeedSubscription call(FeedlySubscription feedlySubscription) {
                        return FeedSubscription.from(mAccount, feedlySubscription);
                    }
                })
                .toList();
    }

    public Observable<FeedlyStream> getFeedStream(String streamId, int count, boolean unreadOnly) {
        return mFeedlyService.getFeedStream(streamId, count, unreadOnly);
    }

    public static class UnreadContentResponse {
        private final String mContinuation;
        private final List<FeedEntry> mEntries;

        public UnreadContentResponse(List<FeedEntry> entries, String continuation) {
            mContinuation = continuation;
            mEntries = entries;
        }

        public String getContinuation() {
            return mContinuation;
        }

        public List<FeedEntry> getEntries() {
            return mEntries;
        }
    }

    public UnreadContentResponse getUnreadContent(long newerThan, String continuation)
            throws IOException {
        Call<FeedlyStream> call;
        String id = "/user/" + mAccount.id() + "/category/global.all";
        List<FeedEntry> entryList = new ArrayList<>();
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
            entryList.add(FeedEntry.builder(entry).accountId(mAccount.id()).build());
        }
        return new UnreadContentResponse(entryList, stream.getContinuation());
    }

    public List<FeedEntry> getSubscriptionContent(String subscriptionId, long newerThan)
            throws IOException {
        List<FeedEntry> result = new ArrayList<>();
        FeedlyStream stream = null;
        do {
            Call<FeedlyStream> call;
            if (stream == null || stream.getContinuation() == null) {
                if (newerThan > 0) {
                    call = mFeedlyService.getFeedStreamCall(subscriptionId, 400, true, newerThan);
                } else {
                    call = mFeedlyService.getFeedStreamCall(subscriptionId, 400, true);
                }
            } else if (newerThan > 0) {
                call = mFeedlyService.getFeedStreamCall(subscriptionId, 400, true, newerThan,
                        stream.getContinuation());
            } else {
                call = mFeedlyService.getFeedStreamCall(subscriptionId, 400, true,
                        stream.getContinuation());
            }
            stream = NetworkUtil.executeApiCall(call);
            for (FeedlyEntry entry : stream.getItems()) {
                result.add(FeedEntry.builder(entry)
                        .accountId(mAccount.id())
                        .subscriptionId(subscriptionId)
                        .build());
            }
            if (stream.getContinuation() == null) {
                break;
            }
        } while (result.size() < 400);
        return result;
    }

    public List<FeedEntry> getSavedEntries() throws IOException {
        String streamId = "/user/" + mAccount.id() + "/tag/global.saved";
        FeedlyStream stream = null;
        List<FeedEntry> result = new ArrayList<>();
        do {
            Call<FeedlyStream> call;
            if (stream == null || stream.getContinuation() == null) {
                call = mFeedlyService.getFeedStreamCall(streamId, 10000, false);
            } else {
                call = mFeedlyService.getFeedStreamCall(streamId, 10000, false,
                        stream.getContinuation());
            }
            stream = NetworkUtil.executeApiCall(call);
            for (FeedlyEntry entry : stream.getItems()) {
                result.add(FeedEntry.builder(entry)
                        .accountId(mAccount.id())
                        .starred(true)
                        .build());
            }
        } while (stream.getContinuation() != null);
        return result;
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
