package com.sorcererxw.feedman.ui.activities;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.api.feedly.FeedlyService;
import com.sorcererxw.feedman.api.feedly.FeedlyServiceProvider;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;

import butterknife.BindView;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/8
 */
public class AuthActivity extends BaseActivity {
    @BindView(R.id.webView_auth)
    WebView mWebView;

    private FeedlyService mFeedlyService = FeedlyServiceProvider.GsonService();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_auth;
    }

    @Override
    protected void init(Bundle saveInstance) {
        String clientId = "Sandbox";
        String authRedirect = "http://localhost";
        final String url =
                "https://feedly.com" + "/v3/auth/auth?" + "response_type=code" + "&client_id="
                        + clientId
                        + "&redirect_uri=" + authRedirect + "&scope="
                        + "https://cloud.feedly.com/subscriptions";

        mWebView.setWebViewClient(new WebViewClient() {

        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(url);
    }
}
