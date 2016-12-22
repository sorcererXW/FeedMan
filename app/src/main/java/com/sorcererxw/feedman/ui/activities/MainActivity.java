package com.sorcererxw.feedman.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.data.FeedSubscription;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.feedly.FeedlyClient;
import com.sorcererxw.feedman.ui.adapters.CategoryAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private FeedlyClient mFeedlyClient;
    private Db mDb;

    private CategoryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mFeedlyClient = FeedManApp.getFeedlyClient(this);
        mDb = FeedManApp.getDb(this);

        mAdapter = new CategoryAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mDb.subscriptions().getSubscriptions()
                .map(new Func1<List<FeedSubscription>, List<CategoryAdapter.CategoryItem>>() {
                    @Override
                    public List<CategoryAdapter.CategoryItem> call(
                            List<FeedSubscription> subscriptions) {
                        List<CategoryAdapter.CategoryItem> list = new ArrayList<>();
                        list.add(new CategoryAdapter.CategoryItem(
                                "Unread",
                                new IconicsDrawable(MainActivity.this,
                                        GoogleMaterial.Icon.gmd_inbox)
                                        .sizeDp(24)
                                        .color(Color.BLACK)
                                        .alpha(128)
                                        .paddingDp(4),
                                null,
                                CategoryAdapter.TYPE_SIMPLE,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        toEntryPage(EntryActivity.TYPE_UNREAD, null);
                                    }
                                }));
                        list.add(new CategoryAdapter.CategoryItem(
                                "All",
                                new IconicsDrawable(MainActivity.this,
                                        GoogleMaterial.Icon.gmd_done_all)
                                        .sizeDp(24)
                                        .color(Color.BLACK)
                                        .alpha(128)
                                        .paddingDp(4),
                                null,
                                CategoryAdapter.TYPE_SIMPLE,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        toEntryPage(EntryActivity.TYPE_ALL, null);
                                    }
                                }));
                        if (subscriptions.size() == 0) {
                            return list;
                        }
                        list.add(new CategoryAdapter.CategoryItem("Subscriptions", null, null,
                                CategoryAdapter.TYPE_LABEL, null));
                        for (final FeedSubscription subscription : subscriptions) {
                            list.add(new CategoryAdapter.CategoryItem(subscription.title(), null,
                                    subscription.visualUrl(), CategoryAdapter.TYPE_SUBSCRIPTION,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            toEntryPage(EntryActivity.TYPE_SUBSCRIPTION,
                                                    subscription);
                                        }
                                    }));
                        }

                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CategoryAdapter.CategoryItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onNext(List<CategoryAdapter.CategoryItem> categoryItems) {
                        mAdapter.setCategoryItemList(categoryItems);
                    }
                });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
    }

    public void refreshData() {
        try {
            mFeedlyClient.getSubscriptions()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<List<FeedSubscription>>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            setRefreshing(true);
                        }

                        @Override
                        public void onCompleted() {
                            setRefreshing(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e);
                            setRefreshing(false);
                        }

                        @Override
                        public void onNext(List<FeedSubscription> subscriptions) {
                            mDb.subscriptions().addSubscriptions(subscriptions);
                        }
                    });
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    private void setRefreshing(final boolean refreshing) {
        if (refreshing == mSwipeRefreshLayout.isRefreshing()) {
            return;
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(refreshing);
            }
        });
    }

    private void toEntryPage(int type, FeedSubscription feedSubscription) {
        Intent intent = new Intent(this, EntryActivity.class);
        intent.putExtra("type", type);
        if (feedSubscription != null) {
            intent.putExtra("subscription", feedSubscription);
        }
        toSubactivity(intent);
    }

    private void toSubactivity(Intent intent) {
        startActivity(intent);
        MainActivity.this.overridePendingTransition(R.anim.slide_right_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            toSubactivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
