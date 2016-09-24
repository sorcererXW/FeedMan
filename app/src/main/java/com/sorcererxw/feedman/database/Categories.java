package com.sorcererxw.feedman.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.database.tables.CategoryTable;
import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;
import com.sorcererxw.feedman.models.FeedAccount;
import com.sorcererxw.feedman.models.FeedCategory;
import com.sorcererxw.feedman.util.Queries;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/21
 */

public class Categories {
    private BriteDatabase mDatabase;

    public Categories(BriteDatabase database) {
        mDatabase = database;
    }

    public Observable<List<FeedCategory>> getCategories(FeedAccount account,
                                                        boolean includeReadCategories) {
        String statement = includeReadCategories ?
                Queries.CategoryQueries.CATEGORIES_ORDERED_QUERY :
                Queries.CategoryQueries.UNREAD_CATEGORIES_ORDERED_QUERY;
        return mDatabase.createQuery(
                Arrays.asList(new String[]{
                        CategoryTable.TABLE_NAME,
                        SubscriptionTable.TABLE_NAME,
                        EntryTable.TABLE_NAME}),
                statement,
                account.id()
        )
                .flatMap(new Func1<SqlBrite.Query, Observable<List<FeedCategory>>>() {
                    @Override
                    public Observable<List<FeedCategory>> call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        List<FeedCategory> categoryList = new ArrayList<>();
                        if (cursor != null) {
                            if (cursor.moveToFirst()) {
                                do {
                                    categoryList.add(FeedCategory.from(cursor));
                                } while (cursor.moveToNext());
                            }
                            cursor.close();
                        }
                        return Observable.just(categoryList);
                    }
                });
    }

    public void updateCategories(FeedAccount account, List<FeedCategory> categoryList) {
        BriteDatabase.Transaction transaction = mDatabase.newTransaction();
        mDatabase.delete(CategoryTable.TABLE_NAME, "account_id = ?", account.id());
        for (FeedCategory category : categoryList) {
            mDatabase.insert(CategoryTable.TABLE_NAME, category.toContentValue(),
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        transaction.markSuccessful();
        transaction.end();
    }

    public void removeAccount(String accountId) {
        mDatabase.delete(CategoryTable.TABLE_NAME, "account_id = ?", accountId);
    }
}
