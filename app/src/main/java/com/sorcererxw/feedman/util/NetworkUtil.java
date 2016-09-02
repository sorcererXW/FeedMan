package com.sorcererxw.feedman.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/2
 */
public class NetworkUtil {

    public static boolean isWifiEnabled(Context context) {
        return isNetworkEnabled(context) && ((WifiManager) context
                .getSystemService(Context.WIFI_SERVICE)).isWifiEnabled();
    }

    public static boolean isNetworkEnabled(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}