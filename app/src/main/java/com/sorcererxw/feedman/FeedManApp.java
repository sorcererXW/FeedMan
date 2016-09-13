package com.sorcererxw.feedman;

import android.app.Application;
import android.content.Context;

import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.util.Prefs;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedManApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private static FeedManApp getInstance(Context context) {
        return (FeedManApp) context.getApplicationContext();
    }

    private Prefs mPrefs;

    public static Prefs getPrefs(Context context) {
        FeedManApp app = getInstance(context);
        if (app.mPrefs == null) {
            app.mPrefs = new Prefs(context);
        }
        return app.mPrefs;
    }

    private DB mDB;

    public static DB getDB(Context context) {
        FeedManApp app = getInstance(context);
        if (app.mDB == null) {
            app.mDB = new DB(context);
        }
        return app.mDB;
    }
}
