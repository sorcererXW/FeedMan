package com.sorcererxw.feedman.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.CheckResult;

import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.models.FeedCategory;
import com.sorcererxw.feedman.models.FeedEntry;
import com.sorcererxw.feedman.models.FeedSubscription;
import com.sorcererxw.feedman.util.Queries;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
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

    public Observable<Integer> getUncachedUnreadEntriesCount() {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.uncached_unread_count,
                new String[0])
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                })
                .first();
    }

    public Observable<List<FeedEntry>> getUncachedUnreadEntries(int limit, int offset) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.uncached_unread,
                String.valueOf(limit),
                String.valueOf(offset))
                .mapToList(new Func1<Cursor, FeedEntry>() {
                    @Override
                    public FeedEntry call(Cursor cursor) {
                        return FeedEntry.from(cursor);
                    }
                })
                .first();
    }

    @CheckResult
    public Observable<List<FeedEntry>> getAllEntries(Account account, int limit, int offset) {
        return getPaginateEntries(Queries.EntryQueries.all, limit, offset,
                account.id());
    }

    @CheckResult
    public Observable<List<FeedEntry>> getUnreadEntries(Account account, int limit, int offset,
                                                        long includeReadSince) {
        return getPaginateEntries(Queries.EntryQueries.unread, limit, offset,
                account.id());
    }


    @CheckResult
    public Observable<List<FeedEntry>> getEntries(FeedSubscription subscription,
                                                  int limit,
                                                  int offset,
                                                  boolean includeReadEntries,
                                                  long includeReadSince) {
        if (includeReadEntries) {
            return getPaginateEntries(Queries.EntryQueries.subscription_all, limit, offset,
                    subscription.id(),
                    subscription.accountId());
        } else {
            return getPaginateEntries(Queries.EntryQueries.subscription_unread, limit, offset,
                    subscription.id(),
                    subscription.accountId(),
                    String.valueOf(includeReadSince));
        }
    }

    @CheckResult
    public Observable<List<FeedEntry>> getEntries(FeedCategory category,
                                                  int limit,
                                                  int offset,
                                                  boolean includeReadEntries,
                                                  long includeReadSince) {
        if (includeReadEntries) {
            return getPaginateEntries(Queries.EntryQueries.category_all, limit, offset,
                    category.id(),
                    category.accountId(),
                    category.accountId());
        } else {
            return getPaginateEntries(Queries.EntryQueries.category_unread, limit, offset,
                    category.id(),
                    category.accountId(),
                    category.accountId(),
                    String.valueOf(includeReadSince));
        }
    }

    @CheckResult
    public Observable<List<FeedEntry>> getStarredEntries(FeedCategory category, int limit,
                                                         int offset) {
        return getPaginateEntries(Queries.EntryQueries.category_starred, limit, offset,
                category.id(),
                category.accountId(),
                category.accountId());
    }

    @CheckResult
    private Observable<List<String>> getAllUnreadEntriesIds(String accountId) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.unread_ids,
                accountId)
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    private Observable<List<String>> getAllOlderUnreadEntriesIds(String accountId,
                                                                 FeedEntry entry) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.older_unread_ids,
                accountId,
                String.valueOf(entry.published().getTime()))
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    private Observable<List<String>> getAllNewerUnreadEntriesIds(String accountId,
                                                                 FeedEntry entry) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.newer_unread_ids,
                accountId,
                String.valueOf(entry.published().getTime()))
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    private Observable<List<String>> getUnreadEntriesIds(FeedSubscription subscription) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.subscription_unread_ids,
                subscription.id(),
                subscription.accountId())
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }


    @CheckResult
    private Observable<List<String>> getOlderUnreadEntriesIds(FeedSubscription subscription,
                                                              FeedEntry entry) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.subscription_older_unread_ids,
                subscription.id(),
                subscription.accountId(),
                String.valueOf(entry.published().getTime()))
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    private Observable<List<String>> getNewerUnreadEntriesIds(FeedSubscription subscription,
                                                              FeedEntry entry) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.subscription_newer_unread_ids,
                subscription.id(),
                subscription.accountId(),
                String.valueOf(entry.published().getTime()))
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    private Observable<List<String>> getUnreadEntriesIds(FeedCategory category) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.category_unread_ids,
                category.id(),
                category.accountId(),
                category.accountId())
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    private Observable<List<String>> getOlderUnreadEntriesIds(FeedCategory category,
                                                              FeedEntry entry) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.category_older_unread_ids,
                category.id(),
                category.accountId(),
                category.accountId(),
                String.valueOf(entry.published().getTime()))
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    private Observable<List<String>> getNewerUnreadEntriesIds(FeedCategory category,
                                                              FeedEntry entry) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.category_newer_unread_ids,
                category.id(),
                category.accountId(),
                category.accountId(),
                String.valueOf(entry.published().getTime()))
                .mapToList(new Func1<Cursor, String>() {
                    @Override
                    public String call(Cursor cursor) {
                        return cursor.getString(0);
                    }
                })
                .first();
    }

    @CheckResult
    public Observable<String> markAsRead(String accountId, String entryId) {
        return markAs(accountId, entryId, false);
    }

    @CheckResult
    public Observable<String> markAsUnread(String accountId, String entryId) {
        return markAs(accountId, entryId, true);
    }

    private Observable<String> markAs(final String accountId,
                                      final String entryId,
                                      final boolean unread) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Entries.this.updateUnreadStatus(accountId, entryId, unread);
                subscriber.onNext(entryId);
                subscriber.onCompleted();
            }
        });
    }

    @CheckResult
    public Observable<String> updateStarredStatus(final String accountId,
                                                  final String entryId,
                                                  final boolean starred) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ContentValues values = new ContentValues();
                Entries.this.mDatabase.update(EntryTable.TABLE_NAME, values,
                        "account_id = ? AND id = ?", accountId, entryId);
                subscriber.onNext(entryId);
                subscriber.onCompleted();
            }
        });
    }

    private void updateUnreadStatus(String accountId, String entryId, boolean unread) {
        updateUnreadStatus(accountId, Arrays.asList(new String[]{entryId}), unread);
    }

    private void updateUnreadStatus(String accountId, List<String> entriesIds, boolean unread) {
        ContentValues values = new ContentValues();
        values.put(EntryTable.UNREAD, unread);
        if (!unread) {
            values.put(EntryTable.READ_TIMESTAMP, new Date().getTime());
        }
        BriteDatabase.Transaction t = mDatabase.newTransaction();
        for (String id : entriesIds) {
            mDatabase.update(EntryTable.TABLE_NAME, values,
                    "account_id = ? AND id = ?", accountId, id);
        }
        t.markSuccessful();
        t.end();
    }

    public void markAsReadImmediate(String accountId, String entryId) {
        updateUnreadStatus(accountId, entryId, false);
    }

    public void markAsUnreadImmediate(String accountId, String entryId) {
        updateUnreadStatus(accountId, entryId, true);
    }

    public Observable<List<String>> markAllAsRead(final String accountId) {
        return getAllUnreadEntriesIds(accountId)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(accountId, entriesIds, false);
                    }
                });
    }

    public Observable<List<String>> markOlderAsRead(final String accountId, FeedEntry entry) {
        return getAllOlderUnreadEntriesIds(accountId, entry)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(accountId, entriesIds, false);
                    }
                });
    }

    public Observable<List<String>> markNewerAsRead(final String accountId, FeedEntry entry) {
        return getAllNewerUnreadEntriesIds(accountId, entry)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(accountId, entriesIds, false);
                    }
                });
    }

    @CheckResult
    public Observable<List<String>> markAsRead(final FeedSubscription subscription) {
        return getUnreadEntriesIds(subscription)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(subscription.accountId(), entriesIds, false);
                    }
                });
    }

    @CheckResult
    public Observable<List<String>> markAsRead(final FeedCategory category) {
        return getUnreadEntriesIds(category)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entries) {
                        updateUnreadStatus(category.accountId(), entries, false);
                    }
                });
    }

    @CheckResult
    public Observable<List<String>> markOlderAsRead(final FeedSubscription subscription,
                                                    FeedEntry entry) {
        return getOlderUnreadEntriesIds(subscription, entry)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(subscription.accountId(), entriesIds, false);
                    }
                });
    }

    @CheckResult
    public Observable<List<String>> markNewerAsRead(final FeedSubscription subscription,
                                                    FeedEntry entry) {
        return getNewerUnreadEntriesIds(subscription, entry)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(subscription.accountId(), entriesIds, false);
                    }
                });
    }

    @CheckResult
    public Observable<List<String>> markOlderAsRead(final FeedCategory category,
                                                    FeedEntry entry) {
        return getOlderUnreadEntriesIds(category, entry)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(category.accountId(), entriesIds, false);
                    }
                });
    }

    @CheckResult
    public Observable<List<String>> markNewerAsRead(final FeedCategory category,
                                                    FeedEntry entry) {
        return getNewerUnreadEntriesIds(category, entry)
                .doOnNext(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> entriesIds) {
                        updateUnreadStatus(category.accountId(), entriesIds, false);
                    }
                });
    }

    @CheckResult
    public Observable<Integer> getUnreadCount(Account account) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.unread_count,
                account.id())
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                });
    }

    @CheckResult
    public Observable<Integer> getAllCount(Account account) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.all_count,
                account.id())
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                });
    }

    @CheckResult
    public Observable<Integer> getStarredCount(Account account) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.starred_count,
                account.id())
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                });
    }

    @CheckResult
    public Observable<Integer> getUnreadCount(Account account, FeedCategory category) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.category_unread_count,
                account.id(), category.id(), account.id())
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                });
    }

    @CheckResult
    public Observable<Integer> getAllCount(Account account, FeedCategory category) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME, Queries.EntryQueries.category_all_count,
                account.id(), category.id(), account.id())
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                });
    }

    @CheckResult
    public Observable<Integer> getStarredCount(Account account, FeedCategory category) {
        return mDatabase.createQuery(EntryTable.TABLE_NAME,
                Queries.EntryQueries.category_starred_count,
                account.id(), category.id(), account.id())
                .mapToOne(new Func1<Cursor, Integer>() {
                    @Override
                    public Integer call(Cursor cursor) {
                        return cursor.getInt(0);
                    }
                });
    }

    public void removeReadEntries(int thresholdInDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -thresholdInDays);
        mDatabase.delete(EntryTable.TABLE_NAME,
                "unread = 0 AND starred = 0 AND starred = 0 AND published < ?",
                String.valueOf(c.getTimeInMillis()));
    }

    public void removeOrphanedEntries() {
        mDatabase.execute(
                "DELETE FROM entry WHERE subscription_id NOT IN (SELECT id FROM subscription) AND starred = 0");
    }

    public void clearStarredEntries(Account account) {
        ContentValues values = new ContentValues();
        values.put(EntryTable.TABLE_NAME, false);
        mDatabase.update(EntryTable.TABLE_NAME, values, "account_id = ?", account.id());
    }

    public void removedAccount(String accountId) {
        mDatabase.delete(EntryTable.TABLE_NAME, "account_id = ?", accountId);
    }

    public void markAsCached(FeedEntry entry) {
        ContentValues values = new ContentValues();
        values.put(EntryTable.CACHED, true);
        mDatabase.update(EntryTable.TABLE_NAME, values, "account_id = ? AND id = ?",
                entry.accountId(), entry.id());
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
