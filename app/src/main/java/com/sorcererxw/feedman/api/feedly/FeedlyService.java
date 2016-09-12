package com.sorcererxw.feedman.api.feedly;

import com.sorcererxw.feedman.models.AccessToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/3
 */
public interface FeedlyService {
    @GET("/v3/search/feeds")
    Call<FeedlySearchResultBean> getSearchResult(@Query("query") String query,
                                                 @Query("count") int count);

    @GET("/v3/feeds/{feedId}")
    Call<FeedlyFeedBean> getFeed(@Path("feedId") String feedId);

    @GET("/v3/streams/contents")
    Observable<FeedlyStream> getFeedStream(@Query("streamId") String streamId,
                                           @Query("count") int count,
                                           @Query("unreadOnly") boolean unreadOnly);

    @GET("/v3/streams/{streamId}/ids")
    Call<FeedlyStream> getStream(@Path("streamId") String streamId,
                                       @Query("count") int count,
                                       @Query("unreadOnly") boolean unreadOnly);

    @GET("/v3/entries/{entryId}")
    Call<List<FeedlyEntryBean>> getEntry(@Path("entryId") String entryId);

    @POST("/v3/auth/token")
    @FormUrlEncoded
    Observable<AccessToken> getAccessToken(@Field("code") String code,
                                           @Field("client_id") String clientId,
                                           @Field("client_secret") String clientSecret,
                                           @Field("redirect_uri") String redirectUri,
                                           @Field("grant_type") String grantType);

    @GET("/v3/profile")
    Observable<FeedlyProfile> getProfile();

    @GET("/v3/subscriptions")
    Observable<List<FeedlySubscription>> getSubscriptions();
}
