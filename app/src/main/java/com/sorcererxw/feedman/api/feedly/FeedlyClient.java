package com.sorcererxw.feedman.api.feedly;

import android.content.Context;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.AccessToken;
import com.sorcererxw.feedman.models.Account;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
            mAccessToken = account.getAccessToken();
        }

        mFeedlyService = new Retrofit.Builder()
                .baseUrl(mEndpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
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
}
