package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */
@AutoValue
public abstract class FeedSubscription implements Parcelable {
    public abstract String accountId();

    public abstract List<FeedCategory> categories();

    public abstract String id();

    public abstract String title();

    public abstract int unread();

    @Nullable
    public abstract String visualUrl();

    @Nullable
    public abstract String website();

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubscriptionTable.ACCOUNT_ID, accountId());
        contentValues.put(SubscriptionTable.ID, id());
        contentValues.put(SubscriptionTable.TITLE, title());
        contentValues.put(SubscriptionTable.VISUAL_URL, visualUrl());
        contentValues.put(SubscriptionTable.WEBSITE, website());
        return contentValues;
    }

    public static AutoValue_FeedSubscription.Builder builder() {
        return new AutoValue_FeedSubscription.Builder();
    }

    public static FeedSubscription from(Cursor cursor) {
        return builder()
                .accountId(DB.Getter.getString(cursor, SubscriptionTable.ACCOUNT_ID))
                .id(DB.Getter.getString(cursor, SubscriptionTable.ID))
                .title(DB.Getter.getString(cursor, SubscriptionTable.TITLE))
                .visualUrl(DB.Getter.getString(cursor, SubscriptionTable.VISUAL_URL))
                .website(DB.Getter.getString(cursor, SubscriptionTable.WEBSITE))
                .build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder accountId(String accountId);

        public abstract FeedSubscription build();

        public abstract Builder id(String id);

        public abstract Builder title(String title);

        public abstract Builder unread(int unread);

        public abstract Builder visualUrl(String visualUrl);

        public abstract Builder website(String website);
    }
}
