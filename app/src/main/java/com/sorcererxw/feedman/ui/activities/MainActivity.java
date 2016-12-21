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
import android.view.View;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.RssApp;
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
        mFeedlyClient = RssApp.getFeedlyClient(this);
        mDb = RssApp.getDb(this);

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
                        list.add(new CategoryAdapter.CategoryItem("Unread", null, null,
                                CategoryAdapter.TYPE_SIMPLE, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toEntryPage(EntryActivity.TYPE_UNREAD, null);
                            }
                        }));
                        list.add(new CategoryAdapter.CategoryItem("All", null, null,
                                CategoryAdapter.TYPE_SIMPLE, new View.OnClickListener() {
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

    public void toEntryPage(int type, FeedSubscription feedSubscription) {
        Intent intent = new Intent(this, EntryActivity.class);
        intent.putExtra("type", type);
        if (feedSubscription != null) {
            intent.putExtra("subscription", feedSubscription);
        }
        startActivity(intent);
        MainActivity.this.overridePendingTransition(R.anim.slide_right_in, android.R.anim.fade_out);
    }

    //
//    private FeedlyClient mFeedlyClient;
//
//    private EntryAdapter mAdapter;
//
//    private Db mDb;
//
//    private Context mContext;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//
//        mContext = this;
//
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitleTextColor(Color.WHITE);
//
//        if (TextUtil.isEmpty(RssApp.prefs(this).getFeedlyAccessToken().getValue())
//                || TextUtil.isEmpty(RssApp.prefs(this).getFeedlyRefreshToken().getValue())
//                || TextUtil.isEmpty(RssApp.prefs(this).getFeedlyAccountId().getValue())) {
//            Intent intent = new Intent(this, AuthActivity.class);
//            startActivity(intent);
//            finish();
//        }
//
//        FeedlyToken feedlyToken = new FeedlyToken();
//        feedlyToken.setAccessToken(RssApp.prefs(this).getFeedlyAccessToken().getValue());
//        feedlyToken.setRefreshToken(RssApp.prefs(this).getFeedlyRefreshToken().getValue());
//        FeedlyAccount account = new FeedlyAccount();
//        account.setFeedlyToken(feedlyToken);
//        account.setId(RssApp.prefs(this).getFeedlyAccountId().getValue());
//
//        RssApp.setupFeedlyClient(this, account);
//        mFeedlyClient = RssApp.getFeedlyClient(this);
//
//        mAdapter = new EntryAdapter(this);
//        mAdapter.setOnItemLongClickListener(new EntryAdapter.OnItemLongClickListener() {
//            private static final String MARK_AS_READ = "mark as read";
//            private static final String MARK_AS_UNREAD = "mark as unread";
//            private static final String MARK_AS_STARRED = "mark as starred";
//            private static final String MARK_AS_UNSTARRED = "mark as unstarred";
//
//            @Override
//            public void onItemLongClick(final FeedEntry entry) {
//                final BottomSheetDialog dialog = new BottomSheetDialog(mContext);
//                RecyclerView recyclerView = new RecyclerView(mContext);
//                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//                recyclerView.setLayoutParams(layoutParams);
//
//                BaseTextAdapter adapter = new BaseTextAdapter(mContext);
//                List<String> list = new ArrayList<>();
//                if (entry.unread()) {
//                    list.add(MARK_AS_READ);
//                } else {
//                    list.add(MARK_AS_UNREAD);
//                }
//                adapter.setData(list);
//                adapter.setOnItemClickListener(new BaseTextAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position, String text) {
//                        if (MARK_AS_UNREAD.equals(text)) {
//                            mFeedlyClient.markAsUnread(Collections.singletonList(entry.id()))
//                                    .subscribeOn(Schedulers.newThread()).subscribe(
//                                    new Action1<String[]>() {
//                                        @Override
//                                        public void call(String[] strings) {
//                                            mDb.entries().markAsUnread(Arrays.asList(strings));
//                                        }
//                                    }, new Action1<Throwable>() {
//                                        @Override
//                                        public void call(Throwable throwable) {
//                                            Timber.e(throwable);
//                                        }
//                                    });
//                        } else if (MARK_AS_READ.equals(text)) {
//                            mFeedlyClient.markAsRead(Collections.singletonList(entry.id()))
//                                    .subscribeOn(Schedulers.newThread()).subscribe(
//                                    new Action1<String[]>() {
//                                        @Override
//                                        public void call(String[] strings) {
//                                            mDb.entries().markAsRead(Arrays.asList(strings));
//                                        }
//                                    }, new Action1<Throwable>() {
//                                        @Override
//                                        public void call(Throwable throwable) {
//                                            Timber.e(throwable);
//                                        }
//                                    });
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, VERTICAL, false));
//                dialog.setContentView(recyclerView);
//                dialog.show();
//            }
//        });
//        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setLayoutManager(
//                new LinearLayoutManager(this, VERTICAL, false));
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//            }
//        });
//        mDb = RssApp.getDb(this);
//        mDb.entries().getAllEntries()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<FeedEntry>>() {
//                    @Override
//                    public void onCompleted() {
//                        updateUnread();
//                        initData();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e(e);
//                    }
//
//                    @Override
//                    public void onNext(List<FeedEntry> entries) {
//                        mAdapter.setEntries(entries);
//                    }
//                });
//
//        initData();
//
//        setupDrawer();
//
//        mToolbar.setOnTouchListener(new View.OnTouchListener() {
//            private boolean mClicked = false;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        if (mClicked) {
//                            mClicked = false;
//                            if (mAdapter.getItemCount() > 0) {
//                                mRecyclerView.smoothScrollToPosition(0);
//                            }
//                        } else {
//                            mClicked = true;
//                            v.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mClicked = false;
//                                }
//                            }, 500);
//                        }
//                        break;
//                }
//                return false;
//            }
//        });
//    }
//
//    public void initData() {
//        mDb.entries().getLastPublish().flatMap(new Func1<Long, Observable<List<FeedEntry>>>() {
//            @Override
//            public Observable<List<FeedEntry>> call(Long newerThan) {
//                Timber.d("newer than: " + newerThan);
//                try {
//                    return mFeedlyClient.getUnreadEntries(newerThan);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    List<FeedEntry> list = new ArrayList<>();
//                    return Observable.just(list);
//                }
//            }
//        }).flatMap(new Func1<List<FeedEntry>, Observable<List<FeedEntry>>>() {
//            @Override
//            public Observable<List<FeedEntry>> call(List<FeedEntry> entries) {
//                mDb.entries().addEntries(entries);
//                return Observable.just(entries);
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<FeedEntry>>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        setRefreshing(true);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        setRefreshing(false);
//                        updateUnread();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e(e);
//                        setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onNext(List<FeedEntry> feedEntries) {
//                    }
//                });
//    }
//
//    private void updateUnread() {
//        mDb.entries().getUnreadEntriesCount()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        if (getSupportActionBar() == null) {
//                            return;
//                        }
//                        String title = "Feeds" + " (" + integer + ") ";
//                        getSupportActionBar().setTitle(title);
//                    }
//                });
//    }
//
//    private void setRefreshing(final boolean refreshing) {
//        if (mSwipeRefreshLayout.isRefreshing() == refreshing) {
//            return;
//        }
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(refreshing);
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateUnread();
//    }
//
//    private Drawer mDrawer;
//
//    private void setupDrawer() {
//        mDrawer = new DrawerBuilder().withActivity(this)
//                .withHeaderDivider(false)
//                .withActionBarDrawerToggleAnimated(true)
//                .withCloseOnClick(true)
//                .withToolbar(mToolbar)
//                .withHeader(R.layout.layout_drawer_header)
//                .withSliderBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground))
//                .withHeaderHeight(DimenHolder.fromDp(178))
//                .build();
//
//        final ExpandableDrawerItem expandableDrawerItem =
//                new ExpandableDrawerItem().withName("Subscriptions").withSelectable(false);
//        try {
//            mFeedlyClient.getSubscriptions()
//                    .flatMap(
//                            new Func1<List<FeedSubscription>, Observable<List<FeedSubscription>>>() {
//                                @Override
//                                public Observable<List<FeedSubscription>> call(
//                                        List<FeedSubscription> feedSubscriptions) {
//                                    mDb.subscriptions().addSubscriptions(feedSubscriptions);
//                                    return mDb.subscriptions().getSubscriptions();
//                                }
//                            })
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<List<FeedSubscription>>() {
//                        @Override
//                        public void call(List<FeedSubscription> subscriptions) {
//                            for (FeedSubscription subscription : subscriptions) {
//                                expandableDrawerItem.withSubItems(
//                                        new SecondaryDrawerItem()
//                                                .withName(subscription.title())
//                                                .withTag(subscription.id())
//                                );
//                            }
//                        }
//                    });
//        } catch (IOException e) {
//            Timber.e(e);
//        }
//
//        mDrawer.addItems(
//                expandableDrawerItem,
//                new PrimaryDrawerItem().withOnDrawerItemClickListener(
//                        new Drawer.OnDrawerItemClickListener() {
//                            @Override
//                            public boolean onItemClick(View view, int position,
//                                                       IDrawerItem drawerItem) {
//                                Intent intent = new Intent(mContext, SettingsActivity.class);
//                                startActivity(intent);
//                                MainActivity.this.overridePendingTransition(R.anim.slide_right_in,
//                                        android.R.anim.fade_out);
//
//                                return false;
//                            }
//                        }).withName("Settings").withSelectable(false),
//                new DividerDrawerItem(),
//                new PrimaryDrawerItem().withOnDrawerItemClickListener(
//                        new Drawer.OnDrawerItemClickListener() {
//                            @Override
//                            public boolean onItemClick(View view, int position,
//                                                       IDrawerItem drawerItem) {
//                                logout();
//                                return false;
//                            }
//                        }).withName("Logout").withSelectable(false));
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = new MenuInflater(this);
//        inflater.inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_filter:
//                mAdapter.setOnlyUnread(!mAdapter.isOnlyUnread());
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void logout() {
//        mDb.entries().clearEntries()
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        RssApp.prefs(MainActivity.this)
//                                .getFeedlyAccountId().setValue("", true);
//                        RssApp.prefs(MainActivity.this)
//                                .getFeedlyAccessToken().setValue("", true);
//                        RssApp.prefs(MainActivity.this)
//                                .getFeedlyRefreshToken().setValue("", true);
//                        RssApp.restartApp(MainActivity.this);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Timber.e(throwable);
//                    }
//                });
//    }

}
