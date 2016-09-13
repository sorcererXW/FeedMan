package com.sorcererxw.feedman.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/13
 */
public class Prefs {
    private SharedPreferences mPreferences;

    public FeedManPreference<String> currentAccount;

    public Prefs(Context context) {
        mPreferences = context.getSharedPreferences("FeedMan", Context.MODE_PRIVATE);
        currentAccount = new FeedManPreference<>(mPreferences, "current_account", "");
    }


}
