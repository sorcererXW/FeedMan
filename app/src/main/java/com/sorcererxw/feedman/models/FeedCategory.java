package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.CategoryTable;
import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyCategory;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */
@AutoValue
public abstract class FeedCategory implements Parcelable {
    public abstract String accountId();

    public abstract String id();

    public abstract String label();

    public abstract int unread();

    public ContentValues toContentValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoryTable.ID, id());
        contentValues.put(CategoryTable.ACCOUNT_ID, accountId());
        contentValues.put(CategoryTable.LABEL, label());
        return contentValues;
    }

    public static FeedCategory from(FeedAccount account, FeedlyCategory feedlyCategory) {
        return from(feedlyCategory.getId(),
                account.id(),
                feedlyCategory.getLabel());
    }

    public static FeedCategory from(String id, String accountId, String label) {
        return from(id, accountId, label, 0);
    }

    public static FeedCategory from(String id, String accountId, String label, int unread) {
        return new AutoValue_FeedCategory(id, accountId, label, unread);
    }

    public static FeedCategory from(Cursor cursor) {
        return from(DB.Getter.getString(cursor, CategoryTable.ID),
                DB.Getter.getString(cursor, CategoryTable.ACCOUNT_ID),
                DB.Getter.getString(cursor, CategoryTable.LABEL),
                DB.Getter.getInt(cursor, EntryTable.UNREAD, 0)
        );
    }
}
