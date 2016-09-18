package com.sorcererxw.feedman.ui.adapters;

import android.content.Context;

import com.sorcererxw.feedman.network.api.feedly.FeedlySearchResultBean;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/6
 */
public class FeedSearchAdapter extends BaseTextAdapter<FeedlySearchResultBean.ResultsBean> {
    public FeedSearchAdapter(Context context) {
        super(context);
    }

    @Override
    protected String convert(FeedlySearchResultBean.ResultsBean bean) {
        return bean.getTitle();
    }
}
