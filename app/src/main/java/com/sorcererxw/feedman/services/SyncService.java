package com.sorcererxw.feedman.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.data.FeedEntry;
import com.sorcererxw.feedman.data.FeedSubscription;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.feedly.FeedlyClient;
import com.sorcererxw.feedman.ui.activities.WelcomeActivity;
import com.sorcererxw.feedman.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class SyncService extends IntentService {
    public SyncService() {
        super("SyncService");
    }

    private static boolean mRunning = false;

    public static void doSync(Context context) {
        if (mRunning) {
            Timber.d("running");
            return;
        }
        Intent intent = new Intent(context, SyncService.class);
        context.startService(intent);
    }

    private FeedlyClient mFeedlyClient;

    @Override
    protected void onHandleIntent(Intent intent) {
        mFeedlyClient = FeedManApp.getFeedlyClient(this);
        if (mFeedlyClient == null) {
            return;
        }
        if (FeedManApp.prefs(this).autoSyncOnlyWifi().getValue() && !NetworkUtil.isOnWifi(this)) {
            return;
        }
        final Db db = FeedManApp.getDb(this);


        try {
            mFeedlyClient.getSubscriptions()
                    .doOnNext(new Action1<List<FeedSubscription>>() {
                        @Override
                        public void call(List<FeedSubscription> feedSubscriptions) {
                            db.subscriptions().addSubscriptions(feedSubscriptions);
                        }
                    })
                    .flatMap(new Func1<List<FeedSubscription>, Observable<Long>>() {
                        @Override
                        public Observable<Long> call(List<FeedSubscription> feedSubscriptions) {
                            return db.entries().getLastPublish();
                        }
                    })
                    .flatMap(new Func1<Long, Observable<List<FeedEntry>>>() {
                        @Override
                        public Observable<List<FeedEntry>> call(Long aLong) {
                            try {
                                return mFeedlyClient.getUnreadEntries(aLong);
                            } catch (IOException e) {
                                Timber.e(e);
                                List<FeedEntry> list = new ArrayList<>();
                                return Observable.just(list);
                            }
                        }
                    })
                    .doOnNext(new Action1<List<FeedEntry>>() {
                        @Override
                        public void call(List<FeedEntry> feedEntries) {
                            db.entries().addEntries(feedEntries);
//                            WebView webView = new WebView(SyncService.this);
//                            webView.getSettings()
//                            for (FeedEntry feedEntry : feedEntries) {
//
//                            }
                        }
                    })
                    .subscribe(new Subscriber<List<FeedEntry>>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            mRunning = true;
                        }

                        @Override
                        public void onCompleted() {
                            mRunning = false;
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e);
                            mRunning = false;
                        }

                        @Override
                        public void onNext(List<FeedEntry> feedEntries) {
                            if (feedEntries.size() <= 0) {
                                return;
                            }
                            pushNotifaction(feedEntries.size());
                        }
                    });
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    private void pushNotifaction(int count) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext()).setSmallIcon(R.drawable.ic_rss_feed_white_24dp)
                .setContentTitle("Feed Man")
                .setContentText("Downloaded " + count + " articles");
        mBuilder.setTicker("Feed Man Download Finish");
        mBuilder.setLargeIcon(
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mBuilder.setAutoCancel(true);

        Intent resultIntent = new Intent(getApplicationContext(),
                WelcomeActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
