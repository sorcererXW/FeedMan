package com.sorcererxw.feedman.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.RssApp;
import com.sorcererxw.feedman.data.FeedEntry;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.feedly.FeedlyClient;
import com.sorcererxw.feedman.util.DisplayUtil;
import com.sorcererxw.feedman.util.Readability;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class ArticleActivity extends SlideInAndOutAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.frameLayout_article_webview_container)
    FrameLayout mWebviewContainer;

    @BindView(R.id.collapsingToolbarLayout_article)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.nestedScrollView_article)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.linearLayout_article_bottom_bar)
    LinearLayout mBottomBar;

    @BindView(R.id.imageView_article_font)
    ImageView mFontButton;

    @BindView(R.id.imageView_article_readability)
    ImageView mReadabilityButton;

    @BindView(R.id.imageView_article_share)
    ImageView mShareButton;

    @BindView(R.id.imageView_article_scroll_top)
    ImageView mScrollTopButton;

    @BindView(R.id.textView_article_published)
    TextView mPublishedText;

    @BindView(R.id.textView_article_origin_title)
    TextView mOriginTitleText;

    private Db mDb;

    private int mFontSize = 2;

    private Context mContext;

    private boolean mReadabilitied = false;

    private FeedEntry mFeedEntry;

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedEntry = getIntent().getParcelableExtra("entry");
        if (mFeedEntry == null) {
            finish();
        }

        mContext = this;

        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mCollapsingToolbarLayout.setTitle(mFeedEntry.title());
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        mCollapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(mFeedEntry.url()));
                startActivity(browserIntent);
            }
        });

        mOriginTitleText.setText(mFeedEntry.subscriptionTitle());

        mPublishedText.setText(
                new SimpleDateFormat("MM/dd/yyyy").format(new Date(mFeedEntry.published())));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v,
                                       int scrollX, int scrollY,
                                       int oldScrollX, int oldScrollY) {
                if (oldScrollY < scrollY) {
                    mBottomBar.animate().translationY(mBottomBar.getHeight()).start();
                } else {
                    mBottomBar.animate().translationY(0).start();
                }
            }
        });

        showContent(mFeedEntry.content());
        FeedlyClient feedlyClient = RssApp.getFeedlyClient(this);
        mDb = RssApp.getDb(this);
        feedlyClient.markAsRead(Collections.singletonList(mFeedEntry.id()))
                .subscribeOn(Schedulers.newThread()).subscribe(
                new Action1<String[]>() {
                    @Override
                    public void call(String[] strings) {
                        mDb.entries().markAsRead(Arrays.asList(strings));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable);
                    }
                });

        mFontButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(ArticleActivity.this, R.layout.layout_article_font_sheet,
                        null);
                SeekBar seekBar =
                        (SeekBar) view.findViewById(R.id.seekBar_font_sizer);
                seekBar.setProgress(mFontSize);

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mFontSize = progress;
                        setFontSize(mFontSize);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                new MaterialDialog.Builder(ArticleActivity.this)
                        .customView(view, false)
                        .backgroundColorRes(R.color.colorBackground)
                        .build().show();
            }
        });

        mReadabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(mReadabilitied).map(new Func1<Boolean, String>() {
                    @Override
                    public String call(Boolean aBoolean) {
                        if (aBoolean) {
                            return mFeedEntry.content();
                        } else {
                            Readability readability = new Readability(mFeedEntry.content());
                            readability.init();
                            return readability.outerHtml();
                        }
                    }
                })
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            private ProgressDialog mDialog;

                            @Override
                            public void onStart() {
                                super.onStart();
                                mDialog = new ProgressDialog(mContext);
                                mDialog.setCanceledOnTouchOutside(false);
                                mDialog.setCancelable(false);
                                mDialog.show();
                            }

                            @Override
                            public void onCompleted() {
                                mDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Timber.e(e);
                                mDialog.dismiss();
                            }

                            @Override
                            public void onNext(String content) {
                                showContent(content);
                                if (mReadabilitied) {
                                    mReadabilityButton.setImageResource(
                                            R.drawable.ic_format_indent_increase_white_24dp);
                                } else {
                                    mReadabilityButton.setImageResource(
                                            R.drawable.ic_format_align_justify_white_24dp);
                                }
                                mReadabilitied = !mReadabilitied;
                                mNestedScrollView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mNestedScrollView.smoothScrollTo(0, 0);
                                    }
                                });
                            }
                        });
            }
        });

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mFeedEntry.url());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via..."));
            }
        });

        mScrollTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollView.smoothScrollTo(0, 0);
            }
        });
    }

    private void showContent(String content) {
        mWebviewContainer.removeAllViews();
        mWebView = new WebView(this);
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:document.body.style.margin=\"8%\"; void 0");
            }
        });
        mWebView.setLayoutParams(layoutParams);
        setFontSize(mFontSize);
        mWebView.getSettings().setJavaScriptEnabled(true);
        String style = "<style>"
                + "img{display: inline;height: auto;max-width: 90%;border-radius: 10px; box-shadow: 0px 2px 10px 2px #bdbdbd; margin-bottom:2px;} "
                + "iframe{display: inline;height: auto;max-width: 90%;} "
                + "</style>";
        mWebView.loadData(style + content, "text/html; charset=utf-8", "UTF-8");
        mWebviewContainer.addView(mWebView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFontSize(int level) {
        float fontSize;
        switch (level) {
            case 0:
                fontSize = getResources().getDimension(R.dimen.font_size_1);
                break;
            case 1:
                fontSize = getResources().getDimension(R.dimen.font_size_2);
                break;
            case 2:
                fontSize = getResources().getDimension(R.dimen.font_size_3);
                break;
            case 3:
                fontSize = getResources().getDimension(R.dimen.font_size_4);
                break;
            case 4:
                fontSize = getResources().getDimension(R.dimen.font_size_5);
                break;
            default:
                fontSize = getResources().getDimension(R.dimen.font_size_3);
        }
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDefaultFontSize(
                DisplayUtil.px2sp(ArticleActivity.this, fontSize));
    }

}
