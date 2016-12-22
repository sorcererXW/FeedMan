package com.sorcererxw.feedman.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.feedly.FeedlyEntry;

import java.util.Objects;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

@AutoValue
public abstract class FeedEntry implements Parcelable {

    public abstract String id();

    public abstract String title();

    public abstract String content();

    public abstract String summary();

    public abstract long published();

    public abstract boolean unread();

    public abstract String url();

    public abstract String subscriptionId();

    public abstract String subscriptionTitle();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(String id);

        public abstract Builder title(String title);

        public abstract Builder content(String content);

        public abstract Builder summary(String summary);

        public abstract Builder published(long published);

        public abstract Builder unread(boolean unread);

        public abstract Builder url(String url);

        public abstract Builder subscriptionId(String subscriptionId);

        public abstract Builder subscriptionTitle(String subscriptionTitle);

        public abstract FeedEntry build();
    }

    private static Builder builder() {
        return new AutoValue_FeedEntry.Builder();
    }

    public static Builder builder(FeedlyEntry feedlyEntry) {
        return builder()
                .id(feedlyEntry.getId())
                .subscriptionId(feedlyEntry.getSubscriptionId())
                .subscriptionTitle(feedlyEntry.getSubscriptionTitle())
                .title(feedlyEntry.getTitle())
                .published(feedlyEntry.getPublished())
                .unread(feedlyEntry.isUnread())
                .content(feedlyEntry.getContent())
                .summary(feedlyEntry.getSummary())
                .url(feedlyEntry.getUrl());
    }

    public static FeedEntry from(Cursor cursor) {
        return builder()
                .id(Db.getString(cursor, EntryTable.ID))
                .subscriptionId(Db.getString(cursor, EntryTable.SUBSCRIPTION_ID))
                .subscriptionTitle(Db.getString(cursor, EntryTable.SUBSCRIPTION_TITLE))
                .title(Db.getString(cursor, EntryTable.TITLE))
                .published(Db.getLong(cursor, EntryTable.PUBLISHED))
                .unread(Db.getBoolean(cursor, EntryTable.UNREAD))
                .content(Db.getString(cursor, EntryTable.CONTENT))
                .summary(Db.getString(cursor, EntryTable.SUMMARY))
                .url(Db.getString(cursor, EntryTable.URL))
                .build();
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(EntryTable.ID, id());
        values.put(EntryTable.SUBSCRIPTION_ID, subscriptionId());
        values.put(EntryTable.SUBSCRIPTION_TITLE, subscriptionTitle());
        values.put(EntryTable.TITLE, title());
        values.put(EntryTable.PUBLISHED, published());
        values.put(EntryTable.UNREAD, unread());
        values.put(EntryTable.CONTENT, content());
        values.put(EntryTable.SUMMARY, summary());
        values.put(EntryTable.URL, url());
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FeedEntry)) {
            return false;
        }
        return Objects.equals(((FeedEntry) obj).id(), id());
    }

    @Override
    public String toString() {
        return "FeedlyEntry{" +
                "\n unread=" + unread() +
                "\n, summary=" + summary() +
                "\n, content=" + content() +
                "\n, id='" + id() + '\'' +
                "\n, title='" + title() + '\'' +
                "\n, published=" + published() +
                "\n, subscriptionId=" + subscriptionId() +
                "\n, subscriptionTitle=" + subscriptionTitle() +
                "\n, url=" + url() +
                "\n}";
    }
}
