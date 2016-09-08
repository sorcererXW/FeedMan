package com.sorcererxw.feedman.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.socks.library.KLog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.FeedlyFeedBean;
import com.sorcererxw.feedman.models.FeedlySearchResultBean;
import com.sorcererxw.feedman.network.FeedlyService;
import com.sorcererxw.feedman.network.FeedlyServiceProvider;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;
import com.sorcererxw.feedman.ui.adapters.BaseTextAdapter;
import com.sorcererxw.feedman.ui.adapters.FeedAdapter;
import com.sorcererxw.feedman.ui.adapters.FeedSearchAdapter;

import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/5
 */
public class FeedActivity extends BaseActivity {
    @BindView(R.id.recyclerView_feed_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_feed_add)
    FloatingActionButton mFab;

    private FeedAdapter mAdapter;

    private FeedlyService mService = FeedlyServiceProvider.GsonService();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void init(Bundle saveInstance) {
        mAdapter = new FeedAdapter(this);
        mAdapter.setOnItemClickListener(new BaseTextAdapter.OnItemClickListener<FeedlyFeedBean>() {
            @Override
            public void onItemClick(FeedlyFeedBean feedlyFeedBean) {
                Intent intent = new Intent(FeedActivity.this, EntryActivity.class);
                intent.putExtra(EntryActivity.ARG_FEED_ID, feedlyFeedBean.getId());
                FeedActivity.this.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        startActivity(new Intent(this, AuthActivity.class));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(FeedActivity.this)
                        .customView(R.layout.layout_edittext, true)
                        .positiveText("add")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog,
                                                @NonNull DialogAction which) {
                                if (dialog.getCustomView() != null) {
                                    EditText editText = (EditText) dialog.getCustomView()
                                            .findViewById(R.id.editText);
                                    add(editText.getText().toString());
                                }
                            }
                        })
                        .build().show();
            }
        });


    }

    private void add(String s) {
        Call<FeedlySearchResultBean> call = mService.getSearchResult(s, 20);
        call.enqueue(new Callback<FeedlySearchResultBean>() {
            @Override
            public void onResponse(Call<FeedlySearchResultBean> call,
                                   Response<FeedlySearchResultBean> response) {
                FeedSearchAdapter searchAdapter = new FeedSearchAdapter(FeedActivity.this);
                searchAdapter.setData(response.body().getResults());
                final MaterialDialog dialog = new MaterialDialog.Builder(FeedActivity.this)
                        .adapter(searchAdapter, new LinearLayoutManager(FeedActivity.this,
                                LinearLayoutManager.VERTICAL, false))
                        .build();
                searchAdapter.setOnItemClickListener(
                        new BaseTextAdapter.OnItemClickListener<FeedlySearchResultBean.ResultsBean>() {
                            @Override
                            public void onItemClick(
                                    FeedlySearchResultBean.ResultsBean resultsBean) {
                                mService.getFeed(resultsBean.getFeedId())
                                        .enqueue(new Callback<FeedlyFeedBean>() {
                                            @Override
                                            public void onResponse(Call<FeedlyFeedBean> call,
                                                                   Response<FeedlyFeedBean> response) {
                                                KLog.d(response.body().toString());
                                                mAdapter.addData(response.body());
                                            }

                                            @Override
                                            public void onFailure(Call<FeedlyFeedBean> call,
                                                                  Throwable t) {
                                                t.printStackTrace();
                                            }
                                        });
                                dialog.dismiss();
                            }
                        });
                dialog.show();
            }

            @Override
            public void onFailure(Call<FeedlySearchResultBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
