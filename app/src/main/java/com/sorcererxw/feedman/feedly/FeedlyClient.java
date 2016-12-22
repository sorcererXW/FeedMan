package com.sorcererxw.feedman.feedly;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sorcererxw.feedman.data.FeedEntry;
import com.sorcererxw.feedman.data.FeedSubscription;
import com.sorcererxw.feedman.util.GsonDateAdapter;
import com.sorcererxw.feedman.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class FeedlyClient {
    /*
https://feedly.com/v3/auth/auth?response_type=code&client_id=quote&redirect_uri=http://localhost&scope=https://cloud.feedly.com/subscriptions
 */
    private String mScope = "https://cloud.feedly.com/subscriptions";
    private String mEndpoint = "https://feedly.com";
    private String mClientId = "quote";
    private String mClientSecret = "FE01GP77QW4O1N4GYGOKWL1PX1Y6";
    private String mRedirectUrl = "http://localhost";

    private FeedlyService mFeedlyService;

    private FeedlyToken mFeedlyToken;

    private Context mContext;
    private FeedlyAccount mAccount;

    public FeedlyClient(Context context, FeedlyAccount account) {
        mContext = context;
        mAccount = account;
        mFeedlyToken = account == null ? null : account.getFeedlyToken();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(Date.class, new GsonDateAdapter())
                .create();
        mFeedlyService = new Retrofit.Builder()
                .baseUrl(mEndpoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (mFeedlyToken != null) {
                            request = request.newBuilder().addHeader("Authorization",
                                    "OAuth " + mFeedlyToken.getAccessToken()).build();
                        }
                        return chain.proceed(request);
                    }
                }).build()).build().create(FeedlyService.class);
    }

    public Observable<FeedlyAccount> authenticate(String authenticationCode) {
        return mFeedlyService.getAccessToken(authenticationCode, mClientId, mClientSecret,
                mRedirectUrl, "authorization_code")
                .flatMap(new Func1<FeedlyToken, Observable<FeedlyProfile>>() {
                    @Override
                    public Observable<FeedlyProfile> call(FeedlyToken token) {
                        mFeedlyToken = token;
                        return mFeedlyService.getProfile();
                    }
                }).flatMap(new Func1<FeedlyProfile, Observable<FeedlyAccount>>() {
                    @Override
                    public Observable<FeedlyAccount> call(FeedlyProfile profile) {
                        FeedlyAccount feedlyAccount = new FeedlyAccount();
                        feedlyAccount.setFeedlyToken(mFeedlyToken);
                        feedlyAccount.setId(profile.getId());
                        mAccount = feedlyAccount;
                        return Observable.just(feedlyAccount);
                    }
                });
    }


    public String getAuthenticationUrl() {
        return mEndpoint + "/v3/auth/auth?" + "response_type=code" + "&client_id="
                + mClientId + "&redirect_uri=" + mRedirectUrl + "&scope="
                + "https://cloud.feedly.com/subscriptions";
    }

    public String getAuthenticationRedirectUrl() {
        return mRedirectUrl;
    }

    public FeedlyToken refreshAccessToken(FeedlyToken currentAccessToken)
            throws IOException {
        FeedlyToken feedlyToken = new FeedlyToken();
        feedlyToken.setAccessToken(NetworkUtil.executeApiCall(
                mFeedlyService.refreshAccessToken(currentAccessToken.getRefreshToken(),
                        mClientId, mClientSecret, "access_token")).getAccessToken());
        feedlyToken.setRefreshToken(currentAccessToken.getRefreshToken());
        return feedlyToken;
    }

    public Observable<String[]> markAsRead(List<String> entryIds) {
        return updateReadStatus(FeedlyReadMarker.read(entryIds.toArray(new String[0])));
    }

    public Observable<String[]> markAsUnread(List<String> entryIds) {
        return updateReadStatus(FeedlyReadMarker.unread(entryIds.toArray(new String[0])));
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


    public Observable<List<FeedEntry>> getUnreadEntries() throws IOException {
        return getUnreadEntries(-1);
    }

    public Observable<List<FeedSubscription>> getSubscriptions() throws IOException {
        return Observable.just(0)
                .map(new Func1<Integer, List<FeedlySubscription>>() {
                    @Override
                    public List<FeedlySubscription> call(Integer integer) {
                        Call<List<FeedlySubscription>> call = mFeedlyService.getSubscriptions();
                        List<FeedlySubscription> res = new ArrayList<>();
                        try {
                            res = NetworkUtil.executeApiCall(call);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return res;
                    }
                })
                .flatMap(new Func1<List<FeedlySubscription>, Observable<FeedlySubscription>>() {
                    @Override
                    public Observable<FeedlySubscription> call(
                            List<FeedlySubscription> feedlySubscriptions) {
                        return Observable.from(feedlySubscriptions);
                    }
                })
                .map(new Func1<FeedlySubscription, FeedSubscription>() {
                    @Override
                    public FeedSubscription call(FeedlySubscription feedlySubscription) {
                        return FeedSubscription.builder(feedlySubscription).build();
                    }
                })
                .toList();
    }

    public Observable<List<FeedEntry>> getUnreadEntries(final long newerThan) throws IOException {
        String id = "user/" + mAccount.getId() + "/category/global.all";
        return Observable.just(id)
                .map(new Func1<String, List<FeedlyEntry>>() {
                    @Override
                    public List<FeedlyEntry> call(String id) {
                        Call<FeedlyStream> call;
                        List<FeedlyEntry> result = new ArrayList<>();
                        int size = 100;
                        boolean end = false;
                        String continuation = null;
                        do {
                            if (newerThan == -1) {
                                if (continuation == null) {
                                    call = mFeedlyService.getFeedStream(id, size, false,
                                            System.currentTimeMillis() - 86400000);
                                } else {
                                    call = mFeedlyService
                                            .getFeedStream(id, size, false,
                                                    System.currentTimeMillis() - 86400000,
                                                    continuation);
                                }
                            } else {
                                if (continuation == null) {
                                    call = mFeedlyService.getFeedStream(id, size, false, newerThan);
                                } else {
                                    call = mFeedlyService
                                            .getFeedStream(id, size, false, newerThan, continuation);
                                }
                            }
                            FeedlyStream stream = null;
                            try {
                                stream = NetworkUtil.executeApiCall(call);
                            } catch (IOException e) {
                                Timber.e(e);
                            }
                            if (stream == null) {
                                break;
                            }
                            continuation = stream.getContinuation();
                            result.addAll(stream.getItems());
                            if (stream.getItems().size() < size) {
                                end = true;
                            }
                        } while (!end);
                        return result;
                    }
                }).flatMap(new Func1<List<FeedlyEntry>, Observable<FeedlyEntry>>() {
                    @Override
                    public Observable<FeedlyEntry> call(List<FeedlyEntry> entries) {
                        return Observable.from(entries);
                    }
                }).map(new Func1<FeedlyEntry, FeedEntry>() {
                    @Override
                    public FeedEntry call(FeedlyEntry feedlyEntry) {
                        return FeedEntry.builder(feedlyEntry).build();
                    }
                }).toList();
    }
}
