package com.sorcererxw.feedman.network.sync;

import android.content.Context;

import com.socks.library.KLog;
import com.sorcererxw.feedman.models.FeedAccount;
import com.sorcererxw.feedman.models.FeedEntry;
import com.sorcererxw.feedman.models.FeedSubscription;
import com.sorcererxw.feedman.network.api.ApiRequestException;
import com.sorcererxw.feedman.network.api.feedly.FeedlyClient;
import com.squareup.sqlbrite.BriteDatabase;

import java.io.IOException;
import java.util.Date;
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
                                FeedAccount account) {
        super(context, account);
        mClient = new FeedlyClient(context, account);
    }

    @Override
    public Func1<List<String>, Observable<String[]>> markAsRead() {
        return new Func1<List<String>, Observable<String[]>>() {
            @Override public Observable<String[]> call(List<String> entriesIds) {
                return mClient.markAsRead(entriesIds);
            }
        };
    }

    @Override
    public Func1<String, Observable<String>> markAsStarred() {
        return new Func1<String, Observable<String>>() {
            @Override public Observable<String> call(String entryId) {
                return mClient.markAsStarred(entryId);
            }
        };
    }

    @Override
    public Func1<List<String>, Observable<String[]>> markAsUnread() {
        return new Func1<List<String>, Observable<String[]>>() {
            @Override public Observable<String[]> call(List<String> entriesIds) {
                return mClient.markAsUnread(entriesIds);
            }
        };
    }

    @Override
    public Func1<String, Observable<String>> markAsUnstarred() {
        return new Func1<String, Observable<String>>() {
            @Override public Observable<String> call(String entryId) {
                return mClient.markAsUnstarred(entryId);
            }
        };
    }

    @Override
    public void refreshToken() {
        try {
            getDB().accounts().updateAccount(FeedAccount.from(
                    getAccount(),
                    mClient.refreshAccessToken(getAccount().accessToken()))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Observable<Void> subscribe(String feedId) {
        return mClient.subscribe(feedId);
    }

    @Override
    public void sync() {
        try {
            doSync();
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof ApiRequestException
                    && ((ApiRequestException) e).getErrorCode() == 401) {
                refreshToken();
                return;
            }
            KLog.d("There was an error while syncing Feedly account.");
        }

    }

    @Override
    public Func1<FeedSubscription, Observable<FeedSubscription>> unsubscribe() {
        return new Func1<FeedSubscription, Observable<FeedSubscription>>() {
            @Override public Observable<FeedSubscription> call(FeedSubscription subscription) {
                return mClient.unsubscribe(subscription);
            }
        };
    }

    private void doSync() throws IOException {
        List<FeedSubscription> currentSubscriptions = refreshSubscription();
        BriteDatabase.Transaction transaction = getDB().newTransaction();
        try {
            refreshCategories();
            List<FeedSubscription> updatedSubscriptions = refreshSubscription();
            transaction.markSuccessful();
            long fetchNewerThan = getLastSyncPreference().isContain() ?
                    getLastSyncPreference().getValue().getTime() : 0;
            BriteDatabase.Transaction contentTransaction = getDB().newTransaction();
            try {
                refreshContent(fetchNewerThan, currentSubscriptions, updatedSubscriptions);
                contentTransaction.markSuccessful();
                uploadMarkers();
                downloadMarkers(fetchNewerThan);
                getLastSyncPreference().setValue(new Date());
                refreshStarredItems();
            } finally {
                contentTransaction.end();
            }
        } finally {
            transaction.end();
        }

    }

    private void uploadMarkers() {

    }

    public void refreshContent(long fetchItemsNewerThan,
                               List<FeedSubscription> oldSubscription,
                               List<FeedSubscription> newSubscription) throws IOException {
        int total = 0;
        FeedlyClient.UnreadContentResponse unreadContentResponse = null;
        do {
            unreadContentResponse = mClient.getUnreadContent(fetchItemsNewerThan,
                    unreadContentResponse != null ? unreadContentResponse.getContinuation() : null);
            getDB().entries().addEntries(unreadContentResponse.getEntries());
            total += unreadContentResponse.getEntries().size();
        } while (unreadContentResponse.getContinuation() != null);
        if (fetchItemsNewerThan > 0) {
            for (FeedSubscription subscription : newSubscription) {
                if (!oldSubscription.contains(subscription)) {
                    List<FeedEntry> entries = mClient.getSubscriptionContent(subscription.id(), 0);
                    getDB().entries().addEntries(entries);
                    total += entries.size();
                }
            }
        }
    }

    private List<FeedSubscription> refreshSubscription() throws IOException {
        return null;
    }

    private void refreshCategories() throws IOException {
        getDB().categories().updateCategories(getAccount(), mClient.getCategories());
    }

    private void refreshStarredItems() throws IOException {
        BriteDatabase.Transaction transaction = getDB().newTransaction();
        getDB().entries().clearStarredEntries(getAccount());
        getDB().entries().addEntries(mClient.getSavedEntries());
        transaction.markSuccessful();
        transaction.end();
    }

    private void downloadMarkers(long fetchNewerThan) {
        BriteDatabase.Transaction transaction = getDB().newTransaction();
        
    }
}
