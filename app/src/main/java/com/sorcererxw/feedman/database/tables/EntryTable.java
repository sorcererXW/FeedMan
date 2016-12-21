package com.sorcererxw.feedman.database.tables;

import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.database.TableBuilder;

import static com.sorcererxw.feedman.database.TableBuilder.FLAG_NOT_NULL;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class EntryTable {
    public static final String AUTHOR = "author";
    public static final String CACHED = "cached";
    public static final String CONTENT = "content";
    public static final String ID = "id";
    public static final String PUBLISHED = "published";
    public static final String READ_TIMESTAMP = "read_timestamp";
    public static final String STARRED = "starred";
    public static final String SUMMARY = "summary";
    public static final String TABLE_NAME = "entry";
    public static final String THUMBNAIL = "thumbnail";
    public static final String TITLE = "title";
    public static final String UNREAD = "unread";
    public static final String URL = "url";
    public static final String ORIGIN_TITLE = "origin_title";
    public static final String SUBSCRIPTION_ID = "subscription_id";
    public static final String SUBSCRIPTION_TITLE = "subscription_title";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(new TableBuilder(TABLE_NAME)
                .addTextColumn(ID, FLAG_NOT_NULL)
                .addTextColumn(SUBSCRIPTION_ID)
                .addTextColumn(SUBSCRIPTION_TITLE)
                .addTextColumn(TITLE)
                .addTextColumn(AUTHOR)
                .addTextColumn(ORIGIN_TITLE)
                .addIntegerColumn(PUBLISHED)
                .addBooleanColumn(UNREAD)
                .addIntegerColumn(READ_TIMESTAMP)
                .addTextColumn(CONTENT)
                .addTextColumn(SUMMARY)
                .addTextColumn(URL)
                .addTextColumn(THUMBNAIL)
                .addBooleanColumn(STARRED)
                .addBooleanColumn(CACHED)
                .addPrimaryKeyColumn(ID)
                .build());
    }

}
