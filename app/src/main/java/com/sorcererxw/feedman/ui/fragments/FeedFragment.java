package com.sorcererxw.feedman.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.FeedSubscription;
import com.sorcererxw.feedman.network.api.feedly.FeedlyClient;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlySubscription;
import com.sorcererxw.feedman.ui.activities.EntryActivity;
import com.sorcererxw.feedman.ui.adapters.BaseTextAdapter;
import com.sorcererxw.feedman.ui.fragments.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedFragment extends BaseFragment {

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    @BindView(R.id.recyclerView_fragment_feed_list)
    RecyclerView mRecyclerView;

    private BaseTextAdapter<FeedSubscription> mAdapter;

    @Override
    protected void init(View view, Bundle saveInstance) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new BaseTextAdapter<FeedSubscription>(getContext()) {
            @Override
            protected String convert(FeedSubscription bean) {
                return bean.title();
            }
        };

        mAdapter.setOnItemClickListener(
                new BaseTextAdapter.OnItemClickListener<FeedSubscription>() {
                    @Override
                    public void onItemClick(FeedSubscription feedSubscription) {
                        EntryActivity.startActivity(getContext(), feedSubscription.id());
//                        FeedFragment.this
//                                .addFragment(EntryFragment.newInstance(feedlySubscription.getId()));
                    }
                });

        mRecyclerView.setAdapter(mAdapter);

        FeedlyClient client = new FeedlyClient(getContext(),
                FeedManApp.getDB(getContext()).getAccounts()
                        .getAccount(
                                FeedManApp.getPrefs(getContext()).getCurrentAccount().getValue()));

        client.getSubscriptions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FeedSubscription>>() {
                    @Override
                    public void call(List<FeedSubscription> feedSubscriptions) {
                        mAdapter.setData(feedSubscriptions);
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
