package com.sorcererxw.feedman.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyEntry;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;
import com.sorcererxw.feedman.util.TemporaryBag;

import butterknife.BindView;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/5
 */
public class ContentActivity extends BaseActivity {

    private static final String KEY_FEEDLY_ENTRY = "feedly_feedly_entry";

    public static void startActivity(Context context, FeedlyEntry entry) {
        Intent intent = new Intent(context, ContentActivity.class);
        TemporaryBag.feedlyEntry = entry;
        context.startActivity(intent);
    }

    private FeedlyEntry mFeedlyEntry;

    @BindView(R.id.textView_activity_content_content)
    TextView mContent;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        mFeedlyEntry = TemporaryBag.feedlyEntry;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_content;
    }

    @Override
    protected void init(Bundle saveInstance) {
        if (mFeedlyEntry == null || mFeedlyEntry.getContent() == null) {
            finish();
        }
        mContent.setText(Html.fromHtml(mFeedlyEntry.getContent()));

    }
}
