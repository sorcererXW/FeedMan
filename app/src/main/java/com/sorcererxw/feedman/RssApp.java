package com.sorcererxw.feedman;

import android.app.Application;
import android.content.Context;

import com.jakewharton.processphoenix.ProcessPhoenix;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.feedly.FeedlyAccount;
import com.sorcererxw.feedman.feedly.FeedlyClient;
import com.sorcererxw.feedman.util.Prefs;

import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class RssApp extends Application {
    private Prefs mPrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mPrefs = Prefs.getInstance(this);
        mDb = new Db(this);
    }

    public static Prefs prefs(Context context) {
        return ((RssApp) context.getApplicationContext()).mPrefs;
    }

    public static void restartApp(Context context) {
        ProcessPhoenix.triggerRebirth(context);
    }

    private FeedlyClient mFeedlyClient;

    public static void setupFeedlyClient(Context context, FeedlyAccount feedlyAccount) {
        ((RssApp) context.getApplicationContext()).mFeedlyClient =
                new FeedlyClient(context.getApplicationContext(), feedlyAccount);
    }

    public static FeedlyClient getFeedlyClient(Context context) {
        return ((RssApp) context.getApplicationContext()).mFeedlyClient;
    }

    private Db mDb;

    public static Db getDb(Context context) {
        return getRssApp(context).mDb;
    }

    private static RssApp getRssApp(Context context) {
        return (RssApp) context.getApplicationContext();
    }
}
