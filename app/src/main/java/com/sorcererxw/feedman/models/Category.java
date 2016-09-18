package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.CategoryTable;
import com.sorcererxw.feedman.database.tables.EntryTable;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */
public class Category {
    private String mAccountId;
    private String mId;
    private String mLabel;
    private int mUnread;

    public ContentValues toContentValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoryTable.ID, mId);
        contentValues.put(CategoryTable.ACCOUNT_ID, mAccountId);
        contentValues.put(CategoryTable.LABEL, mLabel);
        return contentValues;
    }

    public static Category from(String id, String accountId, String label) {
        return from(id, accountId, label, 0);
    }

    public static Category from(String id, String accountId, String label, int unread) {

    }

    public static Category from(Cursor cursor) {
        return from(DB.DBContentGetter.getString(cursor, CategoryTable.ID),
                DB.DBContentGetter.getString(cursor, CategoryTable.ACCOUNT_ID),
                DB.DBContentGetter.getString(cursor, CategoryTable.LABEL),
                DB.DBContentGetter.getInt(cursor, EntryTable.UNREAD, 0)
        );
    }
}
