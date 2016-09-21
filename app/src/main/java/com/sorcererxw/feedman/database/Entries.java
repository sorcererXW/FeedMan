package com.sorcererxw.feedman.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.CheckResult;

import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.models.FeedEntry;
import com.sorcererxw.feedman.models.FeedSubscription;
import com.sorcererxw.feedman.util.Queries;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import rx.Observable;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */

public class Entries {
    private BriteDatabase mDatabase;

    public Entries(BriteDatabase database) {
        mDatabase = database;
    }

    public void addEntries(List<FeedEntry> entries) {
        BriteDatabase.Transaction transaction = mDatabase.newTransaction();
        for (FeedEntry entry : entries) {
            mDatabase.insert(EntryTable.TABLE_NAME,
                    entry.toContentValues(),
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        transaction.markSuccessful();
        transaction.end();
    }

//    public Observable<Integer> getUncachedUnreadEntriesCount() {
//        mDatabase.createQuery(EntryTable.TABLE_NAME, )
//                .mapToOne(new Func1<Cursor, Integer>() {
//                    @Override
//                    public Integer call(Cursor cursor) {
//                        return cursor.getInt(0);
//                    }
//                })
//                .first();
//    }

    @CheckResult
    public Observable<List<FeedEntry>> getAllEntries(Account account, int limit, int offset) {
        return getPaginateEntries(Queries.EntryQueries.all, limit, offset, account.getId());
    }

    @CheckResult
    public Observable<List<FeedEntry>> getEntries(FeedSubscription subscription,
                                                  int limit,
                                                  int offset,
                                                  boolean includeReadEntries,
                                                  long includeReadSince) {
        if (includeReadEntries) {
            return getPaginateEntries(Queries.EntryQueries.subscription_all, limit, offset,
                    subscription.id(), subscription.accountId());
        } else {
            return getPaginateEntries(Queries.EntryQueries.subscription_unread, limit, offset,
                    subscription.id(), subscription.accountId(), String.valueOf(includeReadSince));
        }
    }


    @CheckResult
    private Observable<List<FeedEntry>> getPaginateEntries(String query,
                                                           int limit,
                                                           int offset,
                                                           String... args) {
        String sortedQuery = query.replace("#{sort}", "DESC");
        if (sortedQuery.equals(query)) {
            throw new IllegalArgumentException("Sort placeholder not found => " + query);
        }
        String[] queryArgs = Arrays.copyOf(args, args.length + 2);
        queryArgs[queryArgs.length - 2] = String.valueOf(limit);
        queryArgs[queryArgs.length - 1] = String.valueOf(offset);
        return mDatabase.createQuery(EntryTable.TABLE_NAME, sortedQuery, queryArgs)
                .mapToList(new Func1<Cursor, FeedEntry>() {
                    @Override
                    public FeedEntry call(Cursor cursor) {
                        return FeedEntry.from(cursor);
                    }
                });
    }
}
