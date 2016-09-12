package com.sorcererxw.feedman.ui.adapters;

import android.content.Context;

import com.sorcererxw.feedman.api.feedly.FeedlyEntryBean;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/5
 */
public class EntryAdapter extends BaseTextAdapter<FeedlyEntryBean> {
    public EntryAdapter(Context context) {
        super(context);
    }

    @Override
    protected String convert(FeedlyEntryBean bean) {
        return bean.getTitle();
    }
}
