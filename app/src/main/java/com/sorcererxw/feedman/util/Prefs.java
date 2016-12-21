package com.sorcererxw.feedman.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class Prefs {
    private static Prefs mPrefs;

    public static Prefs getInstance(Context context) {
        if (mPrefs == null) {
            mPrefs = new Prefs(context);
        }
        return mPrefs;
    }

    private final SharedPreferences mSharedPreferences;

    private Prefs(Context context) {
        mSharedPreferences = context.getSharedPreferences("RSS", Context.MODE_PRIVATE);
    }

    private static final String KEY_FEEDLY_ACCESS_TOKEN = "KEY_FEEDLY_ACCESS_TOKEN";
    private static final String KEY_FEEDLY_REFRESH_TOKEN = "KEY_FEEDLY_REFRESH_TOKEN";
    private static final String KEY_FEEDLY_ACCOUNT_ID = "KEY_FEEDLY_ACCOUNT_ID";

    public RssPreference<String> getFeedlyAccessToken() {
        return new RssPreference<>(mSharedPreferences, KEY_FEEDLY_ACCESS_TOKEN, "");
    }

    public RssPreference<String> getFeedlyRefreshToken() {
        return new RssPreference<>(mSharedPreferences, KEY_FEEDLY_REFRESH_TOKEN, "");
    }

    public RssPreference<String> getFeedlyAccountId() {
        return new RssPreference<>(mSharedPreferences, KEY_FEEDLY_ACCOUNT_ID, "");
    }
}
