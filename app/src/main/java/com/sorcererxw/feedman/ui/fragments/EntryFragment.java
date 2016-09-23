package com.sorcererxw.feedman.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.network.api.feedly.FeedlyClient;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyEntry;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyStream;
import com.sorcererxw.feedman.ui.adapters.BaseTextAdapter;
import com.sorcererxw.feedman.ui.fragments.base.BaseFragment;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class EntryFragment extends BaseFragment {

    private static final String KEY_FEED_ID = "key-feed-id";

    public static EntryFragment newInstance(String feedId) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FEED_ID, feedId);
        EntryFragment fragment = new EntryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mFeedID;

    public EntryFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_entry;
    }

    @BindView(R.id.recyclerView_fragment_entry_list)
    RecyclerView mRecyclerView;

    private BaseTextAdapter<FeedlyEntry> mAdapter;

    @Override
    protected void init(View view, Bundle saveInstance) {
        mFeedID = getArguments().getString(KEY_FEED_ID);

        FeedlyClient client = new FeedlyClient(getContext(),
                FeedManApp.getDB(getContext()).getAccounts()
                        .getAccount(FeedManApp.getPrefs(getContext()).getCurrentAccount().getValue()));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new BaseTextAdapter<FeedlyEntry>(getContext()) {
            @Override
            protected String convert(FeedlyEntry bean) {
                return bean.getTitle();
            }

            @Override
            public boolean isBold(FeedlyEntry bean) {
                return bean.isUnread();
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        client.getFeedStream(mFeedID, 20, false)
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
