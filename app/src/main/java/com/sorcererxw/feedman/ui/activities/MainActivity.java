package com.sorcererxw.feedman.ui.activities;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;
import com.sorcererxw.feedman.ui.activities.base.BaseFragmentActivity;
import com.sorcererxw.feedman.ui.fragments.FeedFragment;
import com.sorcererxw.feedman.ui.fragments.base.BaseFragment;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class MainActivity extends BaseFragmentActivity {
    @Override
    protected int getFragmentContainerId() {
        return R.id.frameLayout_main_container;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return FeedFragment.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }
}
