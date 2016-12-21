package com.sorcererxw.feedman.feedly;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
 * @date: 2016/12/18
 */

public interface FeedlyService {
    @FormUrlEncoded
    @POST("v3/auth/token")
    Observable<FeedlyToken> getAccessToken(@Field("code") String str,
                                           @Field("client_id") String str2,
                                           @Field("client_secret") String str3,
                                           @Field("redirect_uri") String str4,
                                           @Field("grant_type") String str5);

    @GET("v3/streams/contents")
    Call<FeedlyStream> getFeedStream(@Query("streamId") String str, @Query("count") int i,
                                     @Query("unreadOnly") boolean z);

    @GET("v3/streams/contents")
    Call<FeedlyStream> getFeedStream(@Query("streamId") String str, @Query("count") int i,
                                     @Query("unreadOnly") boolean z, @Query("newerThan") long j);

    @GET("v3/streams/contents")
    Call<FeedlyStream> getFeedStream(@Query("streamId") String str, @Query("count") int i,
                                     @Query("unreadOnly") boolean z, @Query("newerThan") long j,
                                     @Query("continuation") String str2);

    @GET("v3/streams/contents")
    Call<FeedlyStream> getFeedStream(@Query("streamId") String str, @Query("count") int i,
                                     @Query("unreadOnly") boolean z,
                                     @Query("continuation") String str2);

    @GET("v3/entries/{entryId}")
    Observable<FeedlyEntry> getFeedEntry(@Path("entryId") String entryId);

    @FormUrlEncoded
    @POST("v3/auth/token")
    Call<FeedlyToken> refreshAccessToken(@Field("refresh_token") String str,
                                         @Field("client_id") String str2,
                                         @Field("client_secret") String str3,
                                         @Field("grant_type") String str4);

    @GET("v3/subscriptions")
    Call<List<FeedlySubscription>> getSubscriptions();

    @GET("v3/profile")
    Observable<FeedlyProfile> getProfile();

    @POST("v3/markers")
    Observable<Void> updateEntriesStatus(@Body FeedlyReadMarker feedlyReadMarker);
}
