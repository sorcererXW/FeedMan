package com.sorcererxw.feedman.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.data.FeedEntry;
import com.sorcererxw.feedman.data.FeedSubscription;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.feedly.FeedlyClient;
import com.sorcererxw.feedman.ui.adapters.BaseTextAdapter;
import com.sorcererxw.feedman.ui.adapters.EntryAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.widget.LinearLayout.VERTICAL;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class EntryActivity extends SlideInAndOutAppCompatActivity {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private EntryAdapter mAdapter;

    private FeedlyClient mFeedlyClient;

    private Db mDb;

    public static final int TYPE_UNREAD = 0x0;
    public static final int TYPE_ALL = 0x1;
    public static final int TYPE_SUBSCRIPTION = 0x2;

    private int mType;
    private FeedSubscription mFeedSubscription;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mType = getIntent().getIntExtra("type", 0x0);
        if (mType == TYPE_SUBSCRIPTION) {
            mFeedSubscription = getIntent().getParcelableExtra("subscription");
            if (mFeedSubscription == null) {
                finish();
            }
        }

        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);

        mAdapter = new EntryAdapter(this);
        mAdapter.setOnItemLongClickListener(new EntryAdapter.OnItemLongClickListener() {
            private static final String MARK_AS_READ = "mark as read";
            private static final String MARK_AS_UNREAD = "mark as unread";
            private static final String MARK_AS_STARRED = "mark as starred";
            private static final String MARK_AS_UNSTARRED = "mark as unstarred";

            @Override
            public void onItemLongClick(final FeedEntry entry) {
                final BottomSheetDialog dialog = new BottomSheetDialog(mContext);
                RecyclerView recyclerView = new RecyclerView(mContext);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                recyclerView.setLayoutParams(layoutParams);
                BaseTextAdapter adapter = new BaseTextAdapter(mContext);
                List<String> list = new ArrayList<>();
                if (entry.unread()) {
                    list.add(MARK_AS_READ);
                } else {
                    list.add(MARK_AS_UNREAD);
                }
                adapter.setData(list);
                adapter.setOnItemClickListener(new BaseTextAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String text) {
                        if (MARK_AS_UNREAD.equals(text)) {
                            mFeedlyClient.markAsUnread(Collections.singletonList(entry.id()))
                                    .subscribeOn(Schedulers.newThread()).subscribe(
                                    new Action1<String[]>() {
                                        @Override
                                        public void call(String[] strings) {
                                            mDb.entries().markAsUnread(Arrays.asList(strings));
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            Timber.e(throwable);
                                        }
                                    });
                        } else if (MARK_AS_READ.equals(text)) {
                            mFeedlyClient.markAsRead(Collections.singletonList(entry.id()))
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
                        }
                        dialog.dismiss();
                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, VERTICAL, false));
                dialog.setContentView(recyclerView);
                dialog.show();
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        mRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.smoothScrollToPosition(0);
                            }
                        });
                        return super.onDoubleTap(e);
                    }
                });
        mToolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mFeedlyClient = FeedManApp.getFeedlyClient(this);
        mDb = FeedManApp.getDb(this);

        Observable<List<FeedEntry>> dbObservable;
        switch (mType) {
            case TYPE_UNREAD:
                setActionBarTitle("Unread");
                dbObservable = mDb.entries().getUnreadEntries();
                break;
            case TYPE_ALL:
                setActionBarTitle("All");
                dbObservable = mDb.entries().getAllEntries();
                break;
            case TYPE_SUBSCRIPTION:
                setActionBarTitle(mFeedSubscription.title());
                dbObservable = mDb.entries().getSubscriptionEntries(mFeedSubscription.id());
                break;
            default:
                setActionBarTitle("Unread");
                dbObservable = mDb.entries().getUnreadEntries();
        }
        dbObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FeedEntry>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onNext(List<FeedEntry> feedEntries) {
                        mAdapter.setEntries(feedEntries);
                    }
                });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        refreshData();
    }

    private void refreshData() {
        mDb.entries().getLastPublish()
                .flatMap(new Func1<Long, Observable<List<FeedEntry>>>() {
                    @Override
                    public Observable<List<FeedEntry>> call(Long lastPublished) {
                        try {
                            return mFeedlyClient.getUnreadEntries(lastPublished);
                        } catch (IOException e) {
                            Timber.e(e);
                            List<FeedEntry> list = new ArrayList<>();
                            return Observable.just(list);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<FeedEntry>>() {
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
                    public void onNext(List<FeedEntry> feedEntries) {
                        mDb.entries().addEntries(feedEntries);
                    }
                });
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

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

}
