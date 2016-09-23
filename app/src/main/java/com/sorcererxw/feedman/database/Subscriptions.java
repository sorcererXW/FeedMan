package com.sorcererxw.feedman.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.style.QuoteSpan;

import com.sorcererxw.feedman.database.Categories;
import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.models.FeedCategory;
import com.sorcererxw.feedman.models.FeedSubscription;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyStream;
import com.sorcererxw.feedman.util.Queries;
import com.squareup.sqlbrite.BriteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/23
 */

public class Subscriptions {
    private BriteDatabase mDatabase;

    public Subscriptions(BriteDatabase database) {
        mDatabase = database;
    }

    public Observable<List<FeedSubscription>> getSubscriptions(Account account,
                                                               boolean includeReadSubscriptions) {
        String query = includeReadSubscriptions ?
                Queries.SubscriptionQueries.all :
                Queries.SubscriptionQueries.unread;
        return mDatabase.createQuery(
                Arrays.asList(
                        SubscriptionTable.TABLE_NAME,
                        EntryTable.TABLE_NAME),
                query,
                account.id()
        )
                .mapToList(new Func1<Cursor, FeedSubscription>() {
                    @Override public FeedSubscription call(Cursor cursor) {
                        return FeedSubscription.from(cursor);
                    }
                });
    }

    public Observable<List<FeedSubscription>> getSubscriptions(Account account,
                                                               FeedCategory category,
                                                               boolean includeReadSubscriptions) {
        String query = includeReadSubscriptions ?
                Queries.SubscriptionQueries.category_all :
                Queries.SubscriptionQueries.category_unread;
        return mDatabase.createQuery(
                Arrays.asList(
                        SubscriptionTable.TABLE_NAME,
                        EntryTable.TABLE_NAME),
                query,
                account.id(),
                category.id())
                .mapToList(new Func1<Cursor, FeedSubscription>() {
                    @Override public FeedSubscription call(Cursor cursor) {
                        return FeedSubscription.from(cursor);
                    }
                });
    }

    public List<FeedSubscription> getAllSubscription(Account account) {
        List<FeedSubscription> result = new ArrayList<>();
        Cursor cursor = mDatabase.query("SELECT * FROM subscription WHERE account_id = ?",
                account.id());
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(FeedSubscription.from(cursor));
            }
        }
        return result;
    }

    public Observable<List<String>> getAllSubscriptionsIcons() {
        return mDatabase.createQuery(SubscriptionTable.TABLE_NAME,
                "SELECT visual_url FROM subscription",
                new String[0])
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    public void refreshSubscriptions(Account account, List<FeedSubscription> subscriptions) {
        BriteDatabase.Transaction transaction = mDatabase.newTransaction();
        mDatabase.delete(SubscriptionTable.TABLE_NAME, "account_id = ?", account.id());
        for (FeedSubscription subscription : subscriptions) {
            mDatabase.insert(SubscriptionTable.TABLE_NAME,
                    subscription.toContentValues(),
                    SQLiteDatabase.CONFLICT_REPLACE);
            if (subscription.categories() != null) {
                for (FeedCategory category : subscription.categories()) {
                    ContentValues contentValues = category.toContentValue();
                    contentValues.put(SubscriptionTable.ACCOUNT_ID, account.id());
                    // TODO: 2016/9/23 subscriptionCategory
//                    contentValues.put();
//                    mDatabase.insert()
                }
            }
        }
        transaction.markSuccessful();
        transaction.end();
    }

    public void markAsRead(Account account, String subscriptionId, long olderThan) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntryTable.UNREAD, false);
        mDatabase.update(EntryTable.TABLE_NAME, contentValues,
                "account_id = ? AND subscription_id = ? AND published <= ? ",
                account.id(), subscriptionId, String.valueOf(olderThan));
    }

    public void removeAccount(String accountId) {
        mDatabase.delete(SubscriptionTable.TABLE_NAME, "account_id = ?", accountId);
    }

    public Observable<FeedSubscription> remove(FeedSubscription subscription) {
        mDatabase.delete(SubscriptionTable.TABLE_NAME, "account_id = ? AND id = ?",
                subscription.accountId(), subscription.id());
        mDatabase.delete(EntryTable.TABLE_NAME,
                "account_id = ? AND subscription_id = ? AND starred = 0",
                subscription.accountId(), subscription.id());
        return Observable.just(subscription);
    }
}
