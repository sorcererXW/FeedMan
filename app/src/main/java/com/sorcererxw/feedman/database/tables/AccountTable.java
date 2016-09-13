package com.sorcererxw.feedman.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.database.TableBuilder;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/13
 */
public class AccountTable {
    public static final String ACCESS_TOKEN = "access_token";
    public static final String ID = "id";
    public static final String LABEL = "label";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String TABLE_NAME = "account";
    public static final String TYPE = "type";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(new TableBuilder(TABLE_NAME)
                .addTextColumn(ID, TableBuilder.FLAG_NOT_NULL)
                .addIntegerColumn(TYPE)
                .addTextColumn(LABEL)
                .addTextColumn(ACCESS_TOKEN, TableBuilder.FLAG_NOT_NULL)
                .addTextColumn(REFRESH_TOKEN)
                .addPrimaryKeys(ID, TYPE)
                .build());
    }

    public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
