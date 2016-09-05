package com.sorcererxw.feedman.network;

import com.sorcererxw.feedman.models.FeedlyEntryBean;
import com.sorcererxw.feedman.models.FeedlyFeedBean;
import com.sorcererxw.feedman.models.FeedlySearchResultBean;
import com.sorcererxw.feedman.models.FeedlyStreamBean;
import com.sorcererxw.feedman.models.FeedlyTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("/v3/streams/{streamId}/ids")
    Call<FeedlyStreamBean> getStream(@Path("streamId") String streamId,
                                     @Query("count") int count);

    @GET("/v3/entries/{entryId}")
    Call<List<FeedlyEntryBean>> getEntry(@Path("entryId") String entryId);
}
