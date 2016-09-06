package com.sorcererxw.feedman.ui.adapters;

import android.content.Context;

import com.sorcererxw.feedman.models.FeedlyFeedBean;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/3
 */
public class FeedAdapter extends BaseTextAdapter<FeedlyFeedBean> {

    public FeedAdapter(Context context) {
        super(context);
    }

    @Override
    protected String convert(FeedlyFeedBean bean) {
        return bean.getTitle();
    }


}
