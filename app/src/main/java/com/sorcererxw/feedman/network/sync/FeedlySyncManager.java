package com.sorcererxw.feedman.network.sync;

import android.content.Context;

import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.network.api.feedly.FeedlyClient;
import com.squareup.sqlbrite.BriteDatabase;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/16
 */
public class FeedlySyncManager extends SyncManager {
    private FeedlyClient mClient;

    protected FeedlySyncManager(Context context,
                                Account account) {
        super(context, account);
        mClient = new FeedlyClient(context, account);
    }

    @Override
    public Func1<List<String>, Observable<String[]>> markAsRead() {
        return null;
    }

    @Override
    public Func1<String, Observable<String>> markAsStarred() {
        return null;
    }

    @Override
    public Func1<List<String>, Observable<String[]>> markAsUnread() {
        return null;
    }

    @Override
    public Func1<String, Observable<String>> markAsUnstarred() {
        return null;
    }

    @Override
    public void refreshToken() {

    }

    @Override
    public Observable<Void> subscribe(String str) {
        return null;
    }

    @Override
    public void sync() {

    }

    @Override
    public Func1<Subscription, Observable<Subscription>> unsubscribe() {
        return null;
    }

    private void doSync() throws IOException {
        List<Subscription> currentSubscriptions = refreshSubscription();
        BriteDatabase.Transaction transaction = getDB().newTransaction();


    }

    public void refreshContent(long fetchItemsNewerThan,
                               List<Subscription> oldSubscription,
                               List<Subscription> newSubscription) throws IOException {
        FeedlyClient.UnreadContentResponse unreadContentResponse = null;
        do {
            unreadContentResponse = mClient.getUnreadContent(fetchItemsNewerThan,
                    unreadContentResponse != null ? unreadContentResponse.getContinuation() : null);

        }
    }

    private List<Subscription> refreshSubscription() throws IOException {
    }
}
