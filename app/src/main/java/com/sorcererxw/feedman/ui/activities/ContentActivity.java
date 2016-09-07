package com.sorcererxw.feedman.ui.activities;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.models.FeedlyEntryBean;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;
import com.sorcererxw.feedman.util.TempBag;

import butterknife.BindView;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/5
 */
public class ContentActivity extends BaseActivity {

    @BindView(R.id.textView_activity_content_content)
    TextView mContent;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_content;
    }

    @Override
    protected void init(Bundle saveInstance) {
        FeedlyEntryBean bean = TempBag.sTempEntry;
        if (bean == null) {
            finish();
        }
        if (bean != null) {
            mContent.setText(Html.fromHtml(bean.getSummary().getContent()));
        }
    }
}
