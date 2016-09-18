package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
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

                .build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder accountId(String str);

        public abstract FeedSubscription build();

        public abstract Builder categories(List<FeedCategory> list);

        public abstract Builder id(String str);

        public abstract Builder title(String str);

        public abstract Builder unread(int i);

        public abstract Builder visualUrl(String str);

        public abstract Builder website(String str);
    }
}
