package com.sorcererxw.feedman.ui.fragments.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.socks.library.KLog;
import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.ui.activities.MainActivity;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public abstract class WebAuthFragment extends BaseFragment {
    @BindView(R.id.webView_fragment_web_auth)
    WebView mWebView;

    protected abstract Observable<Account> authenticate(String str);

    protected abstract String getAuthenticationRedirectUrl();

    protected abstract String getAuthenticationUrl();

    private final CompositeSubscription mSubscription = new CompositeSubscription();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web_auth;
    }

    @Override
    protected void init(View view, Bundle saveInstance) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean aBoolean) {
                KLog.d("clear cookie success: " + aBoolean);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                KLog.d(url);
                if (url == null || !url.startsWith(getAuthenticationRedirectUrl())) {
                    return false;
                }
                mSubscription.add(authenticate(Uri.parse(url).getQueryParameter("code"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Account>() {
                            @Override
                            public void call(Account account) {
                                WebAuthFragment.this.addAccount(account);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Toast.makeText(WebAuthFragment.this.getContext(),
                                        "Cannot add account", Toast.LENGTH_SHORT).show();
                                KLog.d(throwable.getMessage());
                                throwable.printStackTrace();
                            }
                        })
                );
                return true;
            }
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                KLog.d(request.getUrl());
//                if (request.getUrl() == null || !request.getUrl().toString()
//                        .startsWith(getAuthenticationRedirectUrl())) {
//                    return false;
//                }
//                KLog.d(request.getUrl().getQueryParameter("code"));
//                mSubscription.add(authenticate(request.getUrl().getQueryParameter("code"))
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Action1<Account>() {
//                            @Override
//                            public void call(Account account) {
//                                WebAuthFragment.this.addAccount(account);
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                Toast.makeText(WebAuthFragment.this.getContext(),
//                                        "Cannot add account", Toast.LENGTH_SHORT).show();
//                                throwable.printStackTrace();
//                            }
//                        })
//                );
//
//                return true;
//            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(getAuthenticationUrl());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
    }

    protected void addAccount(Account account) {
        Toast.makeText(getContext(), "has account", Toast.LENGTH_SHORT).show();
        FeedManApp.getPrefs(getContext()).currentAccount.setValue(account.getId());
        FeedManApp.getDB(getContext()).getAccounts().addAccount(account);
        Intent intent = new Intent(getHoldingActivity(), MainActivity.class);
        getHoldingActivity().startActivity(intent);
    }
}
