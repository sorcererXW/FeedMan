package com.sorcererxw.feedman.ui.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.ui.fragments.FeedFragment;

import butterknife.BindView;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/1
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.frameLayout_main_container)
    FrameLayout mContainer;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.frameLayout_main_container;
    }

    @Override
    protected void init(Bundle saveInstance) {
        addFragment(FeedFragment.newInstance());
    }
}
