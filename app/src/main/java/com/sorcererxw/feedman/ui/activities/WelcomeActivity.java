package com.sorcererxw.feedman.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sorcererxw.feedman.RssApp;
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

        if (TextUtil.isEmpty(RssApp.prefs(this).getFeedlyAccessToken().getValue())
                || TextUtil.isEmpty(RssApp.prefs(this).getFeedlyRefreshToken().getValue())
                || TextUtil.isEmpty(RssApp.prefs(this).getFeedlyAccountId().getValue())) {
            Intent intent = new Intent(this, AuthActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else {
            Timber.d("access token: %s\nrefresh token: %s\naccount id: %s",
                    RssApp.prefs(this).getFeedlyAccessToken().getValue(),
                    RssApp.prefs(this).getFeedlyRefreshToken().getValue(),
                    RssApp.prefs(this).getFeedlyAccountId().getValue());
            FeedlyToken feedlyToken = new FeedlyToken();
            feedlyToken.setAccessToken(RssApp.prefs(this).getFeedlyAccessToken().getValue());
            feedlyToken.setRefreshToken(RssApp.prefs(this).getFeedlyRefreshToken().getValue());
            FeedlyAccount account = new FeedlyAccount();
            account.setFeedlyToken(feedlyToken);
            account.setId(RssApp.prefs(this).getFeedlyAccountId().getValue());
            RssApp.setupFeedlyClient(this, account);
            Intent intent = new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }
}
