package com.sorcererxw.feedman;

import android.app.Application;
import android.content.Context;

import com.jakewharton.processphoenix.ProcessPhoenix;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.feedly.FeedlyAccount;
import com.sorcererxw.feedman.feedly.FeedlyClient;
import com.sorcererxw.feedman.feedly.FeedlyToken;
import com.sorcererxw.feedman.util.Prefs;
import com.sorcererxw.feedman.util.ScheduleManager;
import com.sorcererxw.feedman.util.TextUtil;

import org.w3c.dom.Text;

import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class FeedManApp extends Application {
    private Prefs mPrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        mPrefs = Prefs.getInstance(this);
        mDb = new Db(this);
        mScheduleManager = new ScheduleManager(this);
        mScheduleManager.startSyncIfNeed();

        if (!TextUtil.isEmpty(mPrefs.getFeedlyAccessToken().getValue())
                && !TextUtil.isEmpty(mPrefs.getFeedlyRefreshToken().getValue())
                && !TextUtil.isEmpty(mPrefs.getFeedlyAccountId().getValue())) {
            FeedlyToken feedlyToken = new FeedlyToken();
            feedlyToken.setAccessToken(mPrefs.getFeedlyAccessToken().getValue());
            feedlyToken.setRefreshToken(mPrefs.getFeedlyRefreshToken().getValue());
            FeedlyAccount feedlyAccount = new FeedlyAccount();
            feedlyAccount.setId(mPrefs.getFeedlyAccountId().getValue());
            feedlyAccount.setFeedlyToken(feedlyToken);
            mFeedlyClient = new FeedlyClient(this, feedlyAccount);
        }
    }

    public static Prefs prefs(Context context) {
        return ((FeedManApp) context.getApplicationContext()).mPrefs;
    }

    public static void restartApp(Context context) {
        ProcessPhoenix.triggerRebirth(context);
    }

    private FeedlyClient mFeedlyClient = null;

    public static FeedlyClient getFeedlyClient(Context context) {
        return ((FeedManApp) context.getApplicationContext()).mFeedlyClient;
    }

    private Db mDb;

    public static Db getDb(Context context) {
        return getFeedManApp(context).mDb;
    }

    private static FeedManApp getFeedManApp(Context context) {
        return (FeedManApp) context.getApplicationContext();
    }

    private ScheduleManager mScheduleManager;

    public static ScheduleManager getScheduleManager(Context context) {
        return getFeedManApp(context).mScheduleManager;
    }
}
