package com.sorcererxw.feedman.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class NetworkUtil {
    public static boolean isOnWifi(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(1);
        if (wifiInfo != null && wifiInfo.isConnectedOrConnecting()) {
            return true;
        }
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting() && netInfo.getType() == 1) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static <T> T executeApiCall(Call<T> call) throws IOException {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response.body();
        }
        throw new IOException("message: " + response.message() + "\nresponse: " + response.code());
    }

}
