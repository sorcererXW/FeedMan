package com.sorcererxw.feedman.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.FeedlyFeedBean;
import com.sorcererxw.feedman.models.FeedlySearchResultBean;
import com.sorcererxw.feedman.ui.activities.MainActivity;
import com.sorcererxw.feedman.ui.fragments.FeedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/3
 */
public class SourceAdapter extends BaseTextAdapter<FeedlyFeedBean> {

    public SourceAdapter(Context context) {
        super(context);
    }

    @Override
    protected String convert(FeedlyFeedBean bean) {
        return bean.getTitle();
    }


}
