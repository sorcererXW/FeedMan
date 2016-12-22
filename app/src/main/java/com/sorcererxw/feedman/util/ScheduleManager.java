package com.sorcererxw.feedman.util;

import android.content.Context;

import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.services.SyncService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class ScheduleManager {
    private ScheduledExecutorService mScheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor();

    private Context mContext;

    public ScheduleManager(Context context) {
        mContext = context;
    }

    public void shutdown() {
        mScheduledExecutorService.shutdown();
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void startSyncIfNeed(){
        if (FeedManApp.prefs(mContext).getAutoSyncEnable().getValue()) {
            scheduleSyncService();
        }
    }

    public void scheduleSyncService() {
        schedule(mChangeWallpaperRunnable, 1, TimeUnit.HOURS);
    }

    public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        shutdown();
        mScheduledExecutorService.scheduleAtFixedRate(runnable, delay, delay, timeUnit);
    }

    private Runnable mChangeWallpaperRunnable = new Runnable() {
        @Override
        public void run() {
            Timber.d("run");
            SyncService.doSync(mContext);
        }
    };
}
