package com.sorcererxw.feedman.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.socks.library.KLog;
import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.network.api.feedly.FeedlyClient;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyEntry;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyStream;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;
import com.sorcererxw.feedman.ui.adapters.BaseTextAdapter;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class EntryActivity extends BaseActivity {

    private static final String KEY_FEED_ID = "key_feed_id";

    public static void startActivity(Context context, String feedId) {
        Intent intent = new Intent(context, EntryActivity.class);
        intent.putExtra(KEY_FEED_ID, feedId);
        context.startActivity(intent);
    }

    @BindView(R.id.recyclerView_activity_entry_list)
    RecyclerView mRecyclerView;

    private String mFeedId;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        mFeedId = intent.getStringExtra(KEY_FEED_ID);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_entry;
    }

    private BaseTextAdapter<FeedlyEntry> mAdapter;

    @Override
    protected void init(Bundle saveInstance) {

        FeedlyClient client = new FeedlyClient(this,
                FeedManApp.getDB(this).getAccounts()
                        .getAccount(FeedManApp.getPrefs(this).getCurrentAccount().getValue()));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new BaseTextAdapter<FeedlyEntry>(this) {
            @Override
            protected String convert(FeedlyEntry bean) {
                return bean.getTitle();
            }

            @Override
            public boolean isBold(FeedlyEntry bean) {
                return bean.isUnread();
            }
        };
        mAdapter.setOnItemClickListener(new BaseTextAdapter.OnItemClickListener<FeedlyEntry>() {
            @Override
            public void onItemClick(FeedlyEntry entry) {
                KLog.d(entry.toString());
//                  ContentActivity.startActivity(EntryActivity.this, entry);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        client.getFeedStream(mFeedId, 20, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FeedlyStream>() {
                    @Override
                    public void call(FeedlyStream feedlyStream) {
                        mAdapter.setData(feedlyStream.getItems());
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
