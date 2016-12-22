package com.sorcererxw.feedman.ui.activities;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.feedly.FeedlyAccount;
import com.sorcererxw.feedman.feedly.FeedlyClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.webView_auth)
    WebView mWebView;

    private FeedlyClient mFeedlyClient;

    private CompositeSubscription mSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        CookieManager.getInstance().removeAllCookie();
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);

        mFeedlyClient = new FeedlyClient(this, null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null || !url.startsWith(getAuthenticationRedirectUrl())) {
                    return false;
                }
                mSubscription.add(authenticate(Uri.parse(url).getQueryParameter("code"))
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<FeedlyAccount>() {
                            @Override
                            public void call(FeedlyAccount account) {
                                FeedManApp.prefs(AuthActivity.this).getFeedlyAccessToken()
                                        .setValue(account.getFeedlyToken().getAccessToken(), true);
                                FeedManApp.prefs(AuthActivity.this).getFeedlyRefreshToken()
                                        .setValue(account.getFeedlyToken().getRefreshToken(), true);
                                FeedManApp.prefs(AuthActivity.this).getFeedlyAccountId()
                                        .setValue(account.getId(), true);
                                FeedManApp.restartApp(AuthActivity.this);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Timber.e(throwable);
                            }
                        }));
                return true;
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(getAuthenticationUrl());
    }

    protected Observable<FeedlyAccount> authenticate(String code) {
        return mFeedlyClient.authenticate(code);
    }

    protected String getAuthenticationRedirectUrl() {
      return mFeedlyClient.getAuthenticationRedirectUrl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    protected String getAuthenticationUrl() {
        return mFeedlyClient.getAuthenticationUrl();
    }
}
