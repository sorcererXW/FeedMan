package com.sorcererxw.feedman.database.executors;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.data.FeedSubscription;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class Subscriptions {
    private BriteDatabase mBriteDatabase;

    public Subscriptions(BriteDatabase database) {
        mBriteDatabase = database;
    }

    public void clearSubscriptions() {
        mBriteDatabase.delete(SubscriptionTable.TABLE_NAME, null);
    }

    public Observable<List<FeedSubscription>> getSubscriptions() {
        return mBriteDatabase.createQuery(SubscriptionTable.TABLE_NAME, subscriptions.all)
                .mapToList(new Func1<Cursor, FeedSubscription>() {
                    @Override
                    public FeedSubscription call(Cursor cursor) {
                        return FeedSubscription.from(cursor);
                    }
                });
    }

    public void addSubscriptions(List<FeedSubscription> subscriptions) {
        try (BriteDatabase.Transaction transaction = mBriteDatabase.newTransaction()) {
            for (FeedSubscription subscription : subscriptions) {
                mBriteDatabase.insert(SubscriptionTable.TABLE_NAME, subscription.toContentValues(),
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        }
    }

    public static final class subscriptions {
        public static final String all = " SELECT * FROM subscription";
        //        public static final String all = " SELECT *, CASE WHEN unread > 0 THEN 1 ELSE 0 END AS has_unread FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? ) AS subs ORDER BY has_unread DESC, title COLLATE NOCASE";
        public static final String category_all =
                " SELECT *, CASE WHEN unread > 0 THEN 1 ELSE 0 END AS has_unread FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? AND id IN ( SELECT subscription_id FROM subscription_categories WHERE account_id = ? AND category_id = ? ) ) AS subs ORDER BY has_unread DESC, title COLLATE NOCASE";
        public static final String category_unread =
                " SELECT * FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? AND id IN ( SELECT subscription_id FROM subscription_categories WHERE account_id = ? AND category_id = ? ) ) AS subs WHERE unread > 0 ORDER BY title COLLATE NOCASE";
        public static final String unread =
                " SELECT * FROM ( SELECT *, ( SELECT COUNT(id) FROM entry e WHERE e.subscription_id = s.id AND e.account_id = s.account_id AND e.unread = 1 ) AS unread FROM subscription s WHERE account_id = ? ) AS subs WHERE unread > 0 ORDER BY title COLLATE NOCASE";
    }
}
