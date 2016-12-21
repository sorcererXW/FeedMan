package com.sorcererxw.feedman.database.tables;

import android.database.sqlite.SQLiteDatabase;

import com.sorcererxw.feedman.database.TableBuilder;

import static com.sorcererxw.feedman.database.TableBuilder.FLAG_NOT_NULL;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class SubscriptionTable {
    public static final String TABLE_NAME = "subscription";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String WEBSITE = "website";
    public static final String VISUAL_URL = "visual_url";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(new TableBuilder(TABLE_NAME)
                .addTextColumn(ID, FLAG_NOT_NULL)
                .addTextColumn(TITLE, FLAG_NOT_NULL)
                .addTextColumn(WEBSITE)
                .addTextColumn(VISUAL_URL)
                .addPrimaryKeyColumn(ID)
                .build());
    }
}