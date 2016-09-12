package com.sorcererxw.feedman.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.ui.fragments.base.BaseFragment;

import butterknife.OnClick;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class WelcomeFragment extends BaseFragment {
    @OnClick(R.id.button_fragment_welcome_login)
    void onLoginClick(View view) {
        addFragment(FeedlyAuthFragment.newInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welcome;
    }

    @Override
    protected void init(View view, Bundle saveInstance) {

    }

    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    public WelcomeFragment() {

    }


}
