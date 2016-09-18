package com.sorcererxw.feedman.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.sorcererxw.feedman.FeedManApp;
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
    protected void init(Bundle saveInstance) {
        if (FeedManApp.getPrefs(this).getCurrentAccount().isContain()
                && FeedManApp.getPrefs(this).getCurrentAccount().getValue().length() > 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.init(saveInstance);
        }
    }

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
