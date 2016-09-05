package com.sorcererxw.feedman.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.socks.library.KLog;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.FeedlyFeedBean;
import com.sorcererxw.feedman.models.FeedlySearchResultBean;
import com.sorcererxw.feedman.network.FeedlyService;
import com.sorcererxw.feedman.ui.activities.MainActivity;
import com.sorcererxw.feedman.ui.adapters.BaseTextAdapter;
import com.sorcererxw.feedman.ui.adapters.SourceAdapter;

import java.util.ArrayList;
import java.util.Arrays;
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
 * @date: 2016/9/2
 */
public class FeedFragment extends BaseFragment {
    private static final String KEY_SAVE_FEED = "key_save_feed";

    @BindView(R.id.recyclerView_source_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_source_add)
    FloatingActionButton mFab;

    private SourceAdapter mAdapter;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_source;
    }

    @Override
    protected void init(View view, Bundle saveInstance) {
        mAdapter = new SourceAdapter(getContext());
        mAdapter.setOnItemClickListener(new BaseTextAdapter.OnItemClickListener<FeedlyFeedBean>() {
            @Override
            public void onItemClick(FeedlyFeedBean feedlyFeedBean) {
                addFragment(EntryFragment.newInstance(feedlyFeedBean.getId()));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (saveInstance != null && saveInstance.getParcelableArray(KEY_SAVE_FEED) != null) {
            List<FeedlyFeedBean> list = Arrays.asList(
                    (FeedlyFeedBean[]) saveInstance.getParcelableArray(KEY_SAVE_FEED));
            mAdapter.setData(list);
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getContext())
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_SAVE_FEED,
                (ArrayList<? extends Parcelable>) mAdapter.getData());
    }

    private void add(String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cloud.feedly.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final FeedlyService service = retrofit.create(FeedlyService.class);
        Call<FeedlySearchResultBean> call = service.getSearchResult(s, 20);
        call.enqueue(new Callback<FeedlySearchResultBean>() {
            @Override
            public void onResponse(Call<FeedlySearchResultBean> call,
                                   Response<FeedlySearchResultBean> response) {
                for (FeedlySearchResultBean.ResultsBean bean : response.body().getResults()) {
                    KLog.d(bean.toString());
                    service.getFeed(bean.getFeedId())
                            .enqueue(new Callback<FeedlyFeedBean>() {
                                @Override
                                public void onResponse(Call<FeedlyFeedBean> call,
                                                       Response<FeedlyFeedBean> response) {
                                    KLog.d(response.body().toString());
                                    mAdapter.addData(response.body());
                                }

                                @Override
                                public void onFailure(Call<FeedlyFeedBean> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<FeedlySearchResultBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
