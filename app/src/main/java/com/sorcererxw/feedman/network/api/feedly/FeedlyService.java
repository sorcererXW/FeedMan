package com.sorcererxw.feedman.network.api.feedly;

import com.sorcererxw.feedman.network.api.feedly.model.FeedlyAccessToken;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyCategory;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyProfile;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyReadMarker;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyStream;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlySubscription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("/v3/streams/contents")
    Observable<FeedlyStream> getFeedStream(@Query("streamId") String streamId,
                                           @Query("count") int count,
                                           @Query("unreadOnly") boolean unreadOnly);

    @GET("/v3/streams/contents")
    Observable<FeedlyStream> getFeedStream(@Query("streamId") String streamId,
                                           @Query("count") int count,
                                           @Query("unreadOnly") boolean unreadOnly,
                                           @Query("newerThan") long newerThan);

    @GET("/v3/streams/contents")
    Observable<FeedlyStream> getFeedStream(@Query("streamId") String streamId,
                                           @Query("count") int count,
                                           @Query("unreadOnly") boolean unreadOnly,
                                           @Query("continuation") String continuation);

    @GET("/v3/streams/contents")
    Observable<FeedlyStream> getFeedStream(@Query("streamId") String streamId,
                                           @Query("count") int count,
                                           @Query("unreadOnly") boolean unreadOnly,
                                           @Query("newerThan") long newerThan,
                                           @Query("continuation") String continuation);

    @GET("/v3/streams/contents")
    Call<FeedlyStream> getFeedStreamCall(@Query("streamId") String streamId,
                                         @Query("count") int count,
                                         @Query("unreadOnly") boolean unreadOnly);

    @GET("/v3/streams/contents")
    Call<FeedlyStream> getFeedStreamCall(@Query("streamId") String streamId,
                                         @Query("count") int count,
                                         @Query("unreadOnly") boolean unreadOnly,
                                         @Query("newerThan") long newerThan);

    @GET("/v3/streams/contents")
    Call<FeedlyStream> getFeedStreamCall(@Query("streamId") String streamId,
                                         @Query("count") int count,
                                         @Query("unreadOnly") boolean unreadOnly,
                                         @Query("continuation") String continuation);

    @GET("/v3/streams/contents")
    Call<FeedlyStream> getFeedStreamCall(@Query("streamId") String streamId,
                                         @Query("count") int count,
                                         @Query("unreadOnly") boolean unreadOnly,
                                         @Query("newerThan") long newerThan,
                                         @Query("continuation") String continuation);

    @POST("/v3/auth/token")
    @FormUrlEncoded
    Observable<FeedlyAccessToken> getAccessToken(@Field("code") String code,
                                                 @Field("client_id") String clientId,
                                                 @Field("client_secret") String clientSecret,
                                                 @Field("redirect_uri") String redirectUri,
                                                 @Field("grant_type") String grantType);

    @POST("/v3/auth/token")
    @FormUrlEncoded
    Observable<FeedlyAccessToken> refreshAccessToken(@Field("refresh_token") String refreshToken,
                                                     @Field("client_id") String clientId,
                                                     @Field("client_secret") String clientSecret,
                                                     @Field("grant_type") String grantType);

    Call<FeedlyAccessToken> refreshAccessTokenCall(@Field("refresh_token") String refreshToken,
                                                   @Field("client_id") String clientId,
                                                   @Field("client_secret") String clientSecret,
                                                   @Field("grant_type") String grantType);

    @GET("/v3/categories")
    Call<List<FeedlyCategory>> getCategories();

    @GET("/v3/profile")
    Observable<FeedlyProfile> getProfile();

    @GET("/v3/subscriptions")
    Observable<List<FeedlySubscription>> getSubscriptions();

    @POST("/v3/markers")
    Observable<Void> updateEntriesStatus(@Body FeedlyReadMarker feedlyReadMarker);

    // Authorization is required
    @DELETE("/v3/subscriptions/{id}")
    Observable<Void> unsubscribe(@Path("id") String feedId);

    @POST("/v3/subscriptions")
    Observable<Void> subscribe(@Body FeedlySubscription feedlySubscription);

}
