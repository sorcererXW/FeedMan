package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.AccountTable;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
@AutoValue
public abstract class Account implements Parcelable {

    @AutoValue.Builder
    public interface Builder {
        Builder subscriptionsCount(long count);

        Builder unreadCount(long count);

        Builder label(String label);

        Builder accessToken(AccessToken token);

        Builder id(String id);

        Account build();
    }

    public abstract String id();

    public abstract String label();

    public abstract long subscriptionsCount();

    public abstract long unreadCount();

    public abstract AccessToken accessToken();

    public ContentValues toContentValues() {
        // TODO: 2016/9/23 account to contentValues
        ContentValues values = new ContentValues();

        return values;
    }

    public static AutoValue_Account.Builder builder() {
        return new AutoValue_Account.Builder();
    }

    public static Account from(AccessToken accessToken, String id, String label){
        return builder()
                .accessToken(accessToken)
                .id(id)
                .label(label)
                .build();
    }

    public static Account from(Cursor cursor) {
        Builder builder = builder()
                .id(DB.Getter.getString(cursor, AccountTable.ID))
                .label(DB.Getter.getString(cursor, AccountTable.LABEL));
        AccessToken accessToken = AccessToken.from(
                DB.Getter.getString(cursor, AccountTable.ACCESS_TOKEN),
                DB.Getter.getString(cursor, AccountTable.REFRESH_TOKEN));
        builder.accessToken(accessToken);
        return builder.build();
    }
}
