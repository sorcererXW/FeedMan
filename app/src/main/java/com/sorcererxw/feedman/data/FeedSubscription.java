package com.sorcererxw.feedman.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;
import com.sorcererxw.feedman.feedly.FeedlySubscription;

import java.util.Objects;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

@AutoValue
public abstract class FeedSubscription implements Parcelable {

    public abstract String id();

    public abstract String title();

    @Nullable
    public abstract String website();

    @Nullable
    public abstract String visualUrl();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(String id);

        public abstract Builder title(String title);

        public abstract Builder website(String website);

        public abstract Builder visualUrl(String visualUrl);

        public abstract FeedSubscription build();
    }

    private static Builder builder() {
        return new AutoValue_FeedSubscription.Builder();
    }

    public static Builder builder(
            FeedlySubscription feedlySubscription) {
        return builder()
                .id(feedlySubscription.getId())
                .visualUrl(feedlySubscription.getVisualUrl())
                .website(feedlySubscription.getWebsite())
                .title(feedlySubscription.getTitle());
    }

    public static FeedSubscription from(Cursor cursor) {
        return builder()
                .id(Db.getString(cursor, SubscriptionTable.ID))
                .title(Db.getString(cursor, SubscriptionTable.TITLE))
                .website(Db.getString(cursor, SubscriptionTable.WEBSITE))
                .visualUrl(Db.getString(cursor, SubscriptionTable.VISUAL_URL))
                .build();
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SubscriptionTable.ID, id());
        values.put(SubscriptionTable.TITLE, title());
        values.put(SubscriptionTable.WEBSITE, website());
        values.put(SubscriptionTable.VISUAL_URL, visualUrl());
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof FeedSubscription
                && Objects.equals(((FeedSubscription) obj).id(), id()));
    }
}
