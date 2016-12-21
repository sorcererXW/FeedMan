package com.sorcererxw.feedman.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;

import java.lang.reflect.InvocationTargetException;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final Class[] tables = new Class[]{EntryTable.class, SubscriptionTable.class};

    public DbOpenHelper(Context context) {
        super(context, "rss.db", null, VERSION);
    }

    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
        db.enableWriteAheadLogging();
    }

    public void onCreate(SQLiteDatabase db) {
        int i = 0;
        Class[] clsArr = tables;
        int length = clsArr.length;
        while (i < length) {
            try {
                clsArr[i].getDeclaredMethod("onCreate",
                        new Class[]{SQLiteDatabase.class})
                        .invoke(null, db);
                i++;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int i = 0;
        Class[] clsArr = tables;
        int length = clsArr.length;
        while (i < length) {
            try {
                clsArr[i].getDeclaredMethod("onUpdate",
                        new Class[]{SQLiteDatabase.class, Integer.TYPE, Integer.TYPE}).invoke(null,
                        db, oldVersion, newVersion);
                i++;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
