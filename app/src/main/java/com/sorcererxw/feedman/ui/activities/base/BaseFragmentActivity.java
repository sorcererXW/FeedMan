package com.sorcererxw.feedman.ui.activities.base;

import android.os.Bundle;
import android.view.KeyEvent;

import com.sorcererxw.feedman.ui.fragments.base.BaseFragment;

import java.util.Stack;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/1
 */
public abstract class BaseFragmentActivity extends BaseActivity {
  protected abstract int getFragmentContainerId();

    protected abstract BaseFragment getFirstFragment();

    @Override
    protected void init(Bundle saveInstance) {
        addFragment(getFirstFragment());
    }

    public void addFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().add(getFragmentContainerId(), fragment)
                .commit();
    }


    public void removeFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
