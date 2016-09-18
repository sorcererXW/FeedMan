package com.sorcererxw.feedman.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.sorcererxw.feedman.network.api.ApiRequestException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

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

    public static boolean isOnWifi(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static <T> T executeApiCall(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new ApiRequestException(response.message(), response.code());
    }
}