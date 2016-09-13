package com.sorcererxw.feedman.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sorcererxw.feedman.database.tables.AccountTable;

import java.lang.reflect.InvocationTargetException;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/13
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 4;
    private static final Class[] tables =
            new Class[]{AccountTable.class};

    public DBOpenHelper(Context context) {
        this(context, "FeedMan.db", null, VERSION);
    }

    public DBOpenHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version,
                        DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
        db.enableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int i = 0;
        Class[] clsArr = tables;
        int length = clsArr.length;
        while (i < length) {
            try {
                clsArr[i].getDeclaredMethod("onCreate", new Class[]{SQLiteDatabase.class})
                        .invoke(null, new Object[]{db});
                i++;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int i = 0;
        Class[] clsArr = tables;
        int length = clsArr.length;
        while (i < length) {
            try {
                clsArr[i].getDeclaredMethod("onUpdate",
                        new Class[]{SQLiteDatabase.class, Integer.TYPE, Integer.TYPE}).invoke(null,
                        new Object[]{db, Integer.valueOf(oldVersion), Integer.valueOf(newVersion)});
                i++;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
