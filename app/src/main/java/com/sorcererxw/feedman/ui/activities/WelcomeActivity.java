package com.sorcererxw.feedman.ui.activities;

import android.os.Bundle;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.ui.activities.base.BaseActivity;
import com.sorcererxw.feedman.ui.activities.base.BaseFragmentActivity;
import com.sorcererxw.feedman.ui.fragments.WelcomeFragment;
import com.sorcererxw.feedman.ui.fragments.base.BaseFragment;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class WelcomeActivity extends BaseFragmentActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.frameLayout_welcome_container;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return WelcomeFragment.newInstance();
    }
}
