package com.sorcererxw.feedman.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.socks.library.KLog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.FeedlyEntryBean;
import com.sorcererxw.feedman.models.FeedlyStreamBean;
import com.sorcererxw.feedman.network.FeedlyService;
import com.sorcererxw.feedman.ui.adapters.EntryAdapter;

import java.util.ArrayList;
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
 * @date: 2016/9/3
 */
public class EntryFragment extends BaseFragment {

    private static final String KEY_FEED_ID = "key_feed_id";

    public static EntryFragment newInstance(String feedId) {
        EntryFragment fragment = new EntryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FEED_ID, feedId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mFeedId;

    @BindView(R.id.recyclerView_feed_list)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    private EntryAdapter mAdapter;

    @Override
    protected void init(View view, Bundle saveInstance) {
        mAdapter = new EntryAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        mFeedId = getArguments().getString(KEY_FEED_ID);
        final FeedlyService service = new Retrofit.Builder()
                .baseUrl("http://cloud.feedly.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FeedlyService.class);
        Call<FeedlyStreamBean> streamCall = service.getStream(mFeedId, 20);
        streamCall.enqueue(new Callback<FeedlyStreamBean>() {
            @Override
            public void onResponse(Call<FeedlyStreamBean> call,
                                   Response<FeedlyStreamBean> response) {
                for (String entryId : response.body().getIds()) {
                    KLog.d(entryId);
                    Call<List<FeedlyEntryBean>> entryCall = service.getEntry(entryId);
                    entryCall.enqueue(new Callback<List<FeedlyEntryBean>>() {
                        @Override
                        public void onResponse(Call<List<FeedlyEntryBean>> call,
                                               Response<List<FeedlyEntryBean>> response) {
                            if (response.body() != null
                                    && response.body().size() > 0) {
                                KLog.d(response.body().get(0).getTitle());
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
                t.printStackTrace();
            }
        });
    }


}
