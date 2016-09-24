package com.sorcererxw.feedman.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyAccessToken;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */

@AutoValue
public abstract class FeedAccessToken implements Parcelable {

    public abstract String refreshToken();

    public abstract String accessToken();

    public static FeedAccessToken from(String accessToken, String refreshToken) {
        return new AutoValue_FeedAccessToken(accessToken, refreshToken);
    }

    public static FeedAccessToken from(FeedlyAccessToken accessToken) {
        return new AutoValue_FeedAccessToken(
                accessToken.getAccessToken(),
                accessToken.getRefreshToken());
    }
}
