package com.sorcererxw.feedman.database;

import android.content.Context;
import android.database.Cursor;

import com.sorcererxw.feedman.database.executors.Entries;
import com.sorcererxw.feedman.database.executors.Subscriptions;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Date;

import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class Db {
    private BriteDatabase mDatabase;

    private Entries mEntries;

    private Subscriptions mSubscriptions;

    public Db(Context context) {
        mDatabase = new SqlBrite.Builder().logger(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        }).build().wrapDatabaseHelper(new DbOpenHelper(context), Schedulers.immediate());

        mEntries = new Entries(mDatabase);
        mSubscriptions = new Subscriptions(mDatabase);
    }

    public Subscriptions subscriptions() {
        return mSubscriptions;
    }

    public Entries entries() {
        return mEntries;
    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static String getString(Cursor cursor, String columnName, String fallbackValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex > -1) {
            return cursor.getString(columnIndex);
        }
        return fallbackValue;
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == 1;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static long getLong(Cursor cursor, String columnName, long fallbackValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex > -1) {
            return cursor.getLong(columnIndex);
        }
        return fallbackValue;
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static int getInt(Cursor cursor, String columnName, int fallbackValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex > -1) {
            return cursor.getInt(columnIndex);
        }
        return fallbackValue;
    }

    public static Date getDate(Cursor cursor, String columnName) {
        return new Date(cursor.getLong(cursor.getColumnIndex(columnName)));
    }
}
