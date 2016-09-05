package com.sorcererxw.feedman.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.sorcererxw.feedman.ui.fragments.BaseFragment;

import butterknife.ButterKnife;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/1
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getContentViewId();

    protected abstract int getFragmentContainerId();

    protected abstract void init(Bundle saveInstance);

    @Override
    protected void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        init(saveInstance);
    }

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
