package com.sorcererxw.feedman.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.feedly.FeedlyAccount;
import com.sorcererxw.feedman.feedly.FeedlyToken;
import com.sorcererxw.feedman.util.TextUtil;

import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (TextUtil.isEmpty(FeedManApp.prefs(this).getFeedlyAccessToken().getValue())
                || TextUtil.isEmpty(FeedManApp.prefs(this).getFeedlyRefreshToken().getValue())
                || TextUtil.isEmpty(FeedManApp.prefs(this).getFeedlyAccountId().getValue())) {
            Intent intent = new Intent(this, AuthActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }
}
