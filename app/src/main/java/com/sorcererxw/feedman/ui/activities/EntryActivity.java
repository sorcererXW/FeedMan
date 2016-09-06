package com.sorcererxw.feedman.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.socks.library.KLog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.FeedlyEntryBean;
import com.sorcererxw.feedman.models.FeedlyStreamBean;
import com.sorcererxw.feedman.network.FeedlyService;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;
import com.sorcererxw.feedman.ui.adapters.BaseTextAdapter;
import com.sorcererxw.feedman.ui.adapters.EntryAdapter;
import com.sorcererxw.feedman.util.TempBag;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/5
 */
public class EntryActivity extends BaseActivity {
    public static final String ARG_FEED_ID = "arg_feed_id";

    private String mFeedId;

    @BindView(R.id.recyclerView_entry_list)
    RecyclerView mRecyclerView;

    private EntryAdapter mAdapter;

    private FeedlyService mService;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_entry;
    }

    @Override
    protected void init(Bundle saveInstance) {
        mFeedId = getIntent().getStringExtra(ARG_FEED_ID);
        mAdapter = new EntryAdapter(this);
        mAdapter.setOnItemClickListener(new BaseTextAdapter.OnItemClickListener<FeedlyEntryBean>() {
            @Override
            public void onItemClick(FeedlyEntryBean feedlyEntryBean) {
                TempBag.sTempEntry = feedlyEntryBean;
                Intent intent = new Intent(EntryActivity.this, ContentActivity.class);
                EntryActivity.this.startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mService = new Retrofit.Builder()
                .baseUrl("http://cloud.feedly.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FeedlyService.class);

        loadData();
    }

    private void loadData() {
        mService.getStream(mFeedId, 20)
                .enqueue(new Callback<FeedlyStreamBean>() {
                    @Override
                    public void onResponse(Call<FeedlyStreamBean> call,
                                           Response<FeedlyStreamBean> response) {
                        for (String id : response.body().getIds()) {
                            KLog.d(id);
                            mService.getEntry(id)
                                    .enqueue(new Callback<List<FeedlyEntryBean>>() {
                                        @Override
                                        public void onResponse(Call<List<FeedlyEntryBean>> call,
                                                               Response<List<FeedlyEntryBean>> response) {
                                            if (response.body() != null
                                                    && response.body().size() > 0) {
                                                mAdapter.addData(response.body().get(0));
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<FeedlyEntryBean>> call,
                                                              Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<FeedlyStreamBean> call, Throwable t) {

                    }
                });
    }
}
