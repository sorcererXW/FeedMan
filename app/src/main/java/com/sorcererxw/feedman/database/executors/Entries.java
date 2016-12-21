package com.sorcererxw.feedman.database.executors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.data.FeedEntry;
import com.sorcererxw.feedman.database.tables.EntryTable;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class Entries {
    private BriteDatabase mBriteDatabase;

    public Entries(BriteDatabase db) {
        mBriteDatabase = db;
    }

    public void addEntries(List<FeedEntry> entries) {
        BriteDatabase.Transaction transaction = mBriteDatabase.newTransaction();
        try {
            for (FeedEntry entry : entries) {
                mBriteDatabase.insert(EntryTable.TABLE_NAME, entry.toContentValues(),
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.close();
        }
    }

    public Observable<Integer> getUnreadEntriesCount() {
        return mBriteDatabase
                .createQuery(EntryTable.TABLE_NAME, entries.unread_count, new String[0])
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                }).first();
    }

    public Observable<List<FeedEntry>> getUnreadEntries() {
        return mBriteDatabase.createQuery(EntryTable.TABLE_NAME, entries.unread_all)
                .mapToList(new Func1<Cursor, FeedEntry>() {
                    @Override
                    public FeedEntry call(Cursor cursor) {
                        return FeedEntry.from(cursor);
                    }
                });
    }

    public Observable<List<FeedEntry>> getSubscriptionEntries(String subscriptionId) {
        return mBriteDatabase.createQuery(EntryTable.TABLE_NAME,
                entries.subscription_all, subscriptionId)
                .mapToList(new Func1<Cursor, FeedEntry>() {
                    @Override
                    public FeedEntry call(Cursor cursor) {
                        return FeedEntry.from(cursor);
                    }
                });
    }

    public Observable<List<FeedEntry>> getAllEntries() {
        return mBriteDatabase.createQuery(EntryTable.TABLE_NAME, entries.all)
                .mapToList(new Func1<Cursor, FeedEntry>() {
                    @Override
                    public FeedEntry call(Cursor cursor) {
                        return FeedEntry.from(cursor);
                    }
                });
    }

    public void markAsRead(List<String> entriesId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntryTable.UNREAD, 0);
        for (String entryId : entriesId) {
            mBriteDatabase.update(EntryTable.TABLE_NAME, contentValues, "id = ?", entryId);
        }
    }

    public void markAsUnread(List<String> entriesId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EntryTable.UNREAD, 1);
        for (String entryId : entriesId) {
            mBriteDatabase.update(EntryTable.TABLE_NAME, contentValues, "id = ?", entryId);
        }
    }

    public Observable<Long> getLastPublish() {
        return mBriteDatabase
                .createQuery(EntryTable.TABLE_NAME, entries.last_publish)
                .mapToOne(new Func1<Cursor, Long>() {
                    @Override
                    public Long call(Cursor cursor) {
                        return cursor.getLong(0);
                    }
                }).first();
    }

    public Observable<Integer> clearEntries() {
        return Observable.just(mBriteDatabase.delete(EntryTable.TABLE_NAME, null));
    }

    public static final class entries {
        public static final String all = "SELECT * FROM entry ORDER BY published DESC";
        public static final String unread_all =
                "SELECT * FROM entry WHERE unread = 1 ORDER BY published DESC";
        public static final String subscription_all =
                " SELECT * FROM entry WHERE subscription_id = ? ORDER BY published DESC";
        public static final String unread_count = "SELECT COUNT (id) FROM entry WHERE unread = 1";
        public static final String last_publish = "SELECT COALESCE( MAX(published), -1) FROM entry";

        //        public static final String all =
//                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String all_count = " SELECT COUNT (id) FROM entry WHERE account_id = ?";
        //        public static final String category_all =
//                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String category_all_count =
//                " SELECT COUNT (e.id) FROM entry e WHERE account_id = ? AND subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? )";
//        public static final String category_newer_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND e.published > ? AND e.unread = 1";
//        public static final String category_older_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND e.published < ? AND e.unread = 1";
//        public static final String category_starred =
//                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.starred = 1 AND e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String category_starred_count =
//                " SELECT COUNT (e.id) FROM entry e WHERE account_id = ? AND subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND starred = 1";
//        public static final String category_unread =
//                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND (e.unread = 1 OR e.read_timestamp  > ?) ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String category_unread_count =
//                " SELECT COUNT (e.id) FROM entry e WHERE account_id = ? AND subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND unread = 1";
//        public static final String category_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.subscription_id IN ( SELECT subscription_id FROM subscription_categories WHERE category_id = ? AND account_id = ? ) AND e.account_id = ? AND e.unread = 1";
//        public static final String newer_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.account_id = ? AND e.published > ? AND e.unread = 1";
//        public static final String older_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.account_id = ? AND e.published < ? AND e.unread = 1";
//        public static final String starred =
//                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.account_id = ? AND e.starred = 1 ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String starred_count =
//                " SELECT COUNT (id) FROM entry WHERE account_id = ? AND starred = 1";
////        public static final String subscription_all =
////                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id = ? AND e.account_id = ? ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String subscription_newer_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.subscription_id = ? AND e.account_id = ? AND e.unread = 1 AND e.published > ?";
//        public static final String subscription_older_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.subscription_id = ? AND e.account_id = ? AND e.unread = 1 AND e.published < ?";
//        public static final String subscription_unread =
//                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.subscription_id = ? AND e.account_id = ? AND (e.unread = 1 OR e.read_timestamp  > ?) ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String subscription_unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.subscription_id = ? AND e.account_id = ? AND e.unread = 1";
//        public static final String uncached_unread =
//                " SELECT * FROM entry WHERE unread = 1 AND (cached != 1 OR cached is NULL) ORDER BY published DESC LIMIT ? OFFSET ?";
//        public static final String uncached_unread_count =
//                " SELECT COUNT(id) FROM entry WHERE unread = 1 AND (cached != 1 OR cached is NULL)";
//        public static final String unread =
//                " SELECT e.*, sub.title AS subscription_title, sub.visual_url AS subscription_visual_url FROM entry AS e LEFT JOIN subscription AS sub ON e.subscription_id = sub.id AND e.account_id = sub.account_id WHERE e.account_id = ? AND (e.unread = 1 OR e.read_timestamp > ?) ORDER BY published #{sort} LIMIT ? OFFSET ?";
//        public static final String unread_ids =
//                " SELECT e.id FROM entry AS e WHERE e.account_id = ? AND e.unread = 1";
    }
}
