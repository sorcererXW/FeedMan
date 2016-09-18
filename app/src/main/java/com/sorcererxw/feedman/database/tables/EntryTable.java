package com.sorcererxw.feedman.database.tables;

import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.database.TableBuilder;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/15
 */
public class EntryTable {
    public static final String ACCOUNT_ID = "account_id";
    public static final String AUTHOR = "author";
    public static final String CACHED = "cached";
    public static final String CONTENT = "content";
    public static final String ID = "id";
    public static final String PUBLISHED = "published";
    public static final String READ_TIMESTAMP = "read_timestamp";
    public static final String STARRED = "starred";
    public static final String SUBSCRIPTION_ID = "subscription_id";
    public static final String SUMMARY = "summary";
    public static final String TABLE_NAME = "entry";
    public static final String THUMBNAIL = "thumbnail";
    public static final String TITLE = "title";
    public static final String UNREAD = "unread";
    public static final String URL = "url";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(new TableBuilder(TABLE_NAME)
                .addTextColumns(TableBuilder.FLAG_NOT_NULL,
                        ID,
                        ACCOUNT_ID)
                .addTextColumns(
                        SUBSCRIPTION_ID,
                        TITLE,
                        AUTHOR,
                        CONTENT,
                        SUMMARY,
                        URL,
                        THUMBNAIL)
                .addIntegerColumns(
                        PUBLISHED,
                        READ_TIMESTAMP)
                .addBooleanColumns(
                        UNREAD,
                        STARRED,
                        CACHED)
                .addPrimaryKeys(
                        ID,
                        ACCOUNT_ID)
                .build());
        db.execSQL("CREATE INDEX entry_subscription_id ON entry (subscription_id)");
    }

    public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
