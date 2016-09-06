package com.sorcererxw.feedman.ui.activities.base;

import android.view.KeyEvent;

import com.sorcererxw.feedman.ui.fragments.base.BaseFragment;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/1
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    protected abstract int getFragmentContainerId();

    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    private BaseFragment mLastFragment = null;
    private BaseFragment mCurrentFragment = null;

    public void addFragment(BaseFragment fragment) {
        addFragment(fragment, fragment.getClass().getSimpleName());
    }

    public void addFragment(BaseFragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContainerId(), fragment, tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
