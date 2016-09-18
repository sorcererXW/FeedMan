package com.sorcererxw.feedman.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/13
 */
public class Prefs {
    private final SharedPreferences mPreferences;

    public Prefs(Context context) {
        mPreferences = context.getSharedPreferences("FeedMan", Context.MODE_PRIVATE);
    }

    public FeedManPreference<String> getCurrentAccount() {
        return new FeedManPreference<>(mPreferences, "current_account", "");
    }

    public FeedManPreference<Date> getLastSync(String accountId) {
        return new FeedManPreference<>(mPreferences, "last_sync_" + accountId, new Date(0));
    }
}
