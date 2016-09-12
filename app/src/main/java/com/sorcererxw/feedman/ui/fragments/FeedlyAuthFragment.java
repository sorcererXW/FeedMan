package com.sorcererxw.feedman.ui.fragments;

import android.content.Context;

import com.socks.library.KLog;
import com.sorcererxw.feedman.models.AccessToken;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.api.feedly.FeedlyClient;
import com.sorcererxw.feedman.ui.fragments.base.WebAuthFragment;

import rx.Observable;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class FeedlyAuthFragment extends WebAuthFragment {

    private FeedlyClient mFeedlyClient;

    public static FeedlyAuthFragment newInstance() {
        FeedlyAuthFragment fragment = new FeedlyAuthFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFeedlyClient = new FeedlyClient(context);
    }

    @Override
    protected Observable<Account> authenticate(String code) {
        KLog.d(code);
        return mFeedlyClient.authenticate(code);
    }

    @Override
    protected String getAuthenticationRedirectUrl() {
        return mFeedlyClient.getAuthenticationRedirectUrl();
    }

    @Override
    protected String getAuthenticationUrl() {
        return mFeedlyClient.getAuthenticationUrl();
    }


}
