package com.sorcererxw.feedman.network;

import android.support.annotation.Nullable;

import com.sorcererxw.feedman.util.AutoGson;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/8
 */
@AutoGson
public abstract class FeedlyToken {
    public abstract String access_token();

    @Nullable
    public abstract String refresh_token();

}
