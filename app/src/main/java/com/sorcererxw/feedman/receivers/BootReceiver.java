package com.sorcererxw.feedman.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sorcererxw.feedman.FeedManApp;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/17
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FeedManApp.getScheduleManager(context).startSyncIfNeed();
    }
}