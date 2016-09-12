package com.sorcererxw.feedman;

import android.app.Application;

import com.sorcererxw.feedman.models.Account;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedManApplication extends Application {
    public static Account sCurrentAccount = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
