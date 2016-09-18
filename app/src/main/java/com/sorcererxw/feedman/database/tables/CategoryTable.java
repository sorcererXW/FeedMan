package com.sorcererxw.feedman.database.tables;

import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.database.TableBuilder;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */
public class CategoryTable {
    public static final String ACCOUNT_ID = "account_id";
    public static final String ID = "id";
    public static final String LABEL = "label";
    public static final String TABLE_NAME = "category";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(new TableBuilder(TABLE_NAME)
                .addTextColumns(TableBuilder.FLAG_NOT_NULL, ID, ACCOUNT_ID, LABEL)
                .addPrimaryKeys(ID, ACCOUNT_ID)
                .build()
        );
    }

    public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
