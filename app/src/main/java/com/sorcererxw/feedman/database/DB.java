package com.sorcererxw.feedman.database;

import android.content.Context;
import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.BuildConfig;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Date;

import rx.schedulers.Schedulers;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/13
 */
public class DB {
    private Context mContext;

    private BriteDatabase mDatabase;

    public DB(Context context) {
        mContext = context;
        mDatabase = SqlBrite.create()
                .wrapDatabaseHelper(new DBOpenHelper(context), Schedulers.newThread());
        mDatabase.setLoggingEnabled(BuildConfig.DEBUG);

        mAccounts = new Accounts(mDatabase);
    }

    private Accounts mAccounts;

    public Accounts getAccounts() {
        return mAccounts;
    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static String getString(Cursor cursor, String columnName, String defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex >= 0) {
            return cursor.getString(columnIndex);
        } else {
            return defaultValue;
        }
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static int getInt(Cursor cursor, String columnName, int defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex >= 0) {
            return cursor.getInt(columnIndex);
        } else {
            return defaultValue;
        }
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static long getLong(Cursor cursor, String columnName, long defaultValue) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex >= 0) {
            return cursor.getLong(columnIndex);
        } else {
            return defaultValue;
        }
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == 1;
    }

    public static Date getDate(Cursor cursor, String columnName) {
        return new Date(cursor.getLong(cursor.getColumnIndex(columnName)));
    }
}
