package com.sorcererxw.feedman.api.feedly;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/8
 */
public class FeedlyServiceProvider {
    public static final String API_BASE_URL = "http://cloud.feedly.com";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL);

    public static FeedlyService GsonService() {
        return new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(FeedlyService.class);
    }
}
