package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableStringBuilder;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.BuildConfig;
import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;
import com.sorcererxw.feedman.network.api.feedly.FeedlyEntry;

import java.util.Date;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */

@AutoValue
public abstract class FeedEntry implements Parcelable {
    public abstract String accountId();

    @Nullable
    public abstract String author();

    @Nullable
    public abstract String content();

    public abstract String id();

    public abstract Date published();

    @Nullable
    public abstract Date readTimestamp();

    public abstract boolean starred();

    @Nullable
    public abstract String subscriptionId();

    @Nullable
    public abstract String subscriptionLabel();

    @Nullable
    public abstract String subscriptionVisualUrl();

    @Nullable
    public abstract String summary();

    @Nullable
    public abstract String thumbnail();

    @Nullable
    public abstract String title();

    public abstract boolean unread();

    @Nullable
    public abstract String url();

    public static FeedEntry from(Cursor cursor) {
        return builder()
                .id(DB.Getter.getString(cursor, EntryTable.ID))
                .accountId(DB.Getter.getString(cursor, EntryTable.ACCOUNT_ID))
                .subscriptionId(DB.Getter.getString(cursor, EntryTable.SUBSCRIPTION_ID))
                .subscriptionLabel(DB.Getter.getString(cursor, DB.SUBSCRIPTION_TITLE_ALIAS))
                .subscriptionVisualUrl(
                        DB.Getter.getString(cursor, DB.SUBSCRIPTION_VISUAL_URL_ALIAS))
                .title(DB.Getter.getString(cursor, EntryTable.TITLE))
                .author(DB.Getter.getString(cursor, EntryTable.AUTHOR))
                .published(DB.Getter.getDate(cursor, EntryTable.PUBLISHED))
                .unread(DB.Getter.getBoolean(cursor, EntryTable.UNREAD))
                .readTimestamp(DB.Getter.getDate(cursor, EntryTable.READ_TIMESTAMP))
                .content(DB.Getter.getString(cursor, EntryTable.CONTENT))
                .summary(DB.Getter.getString(cursor, EntryTable.SUMMARY))
                .url(DB.Getter.getString(cursor, EntryTable.URL))
                .starred(DB.Getter.getBoolean(cursor, EntryTable.STARRED))
                .thumbnail(DB.Getter.getString(cursor, EntryTable.THUMBNAIL))
                .build();
    }

    public static Builder builder(FeedlyEntry feedlyEntry) {
        Builder builder = builder()
                .id(feedlyEntry.getId())
                .title(feedlyEntry.getTitle())
                .author(feedlyEntry.getAuthor())
                .subscriptionId(feedlyEntry.getSubscriptionId())
                .unread(feedlyEntry.isUnread())
                .content(feedlyEntry.getContent())
                .summary(extractSummary(feedlyEntry.getContent()))
                .starred(false)
                .url(feedlyEntry.getUrl())
                .thumbnail(feedlyEntry.getThumbnailUrl());
        if (feedlyEntry.getUpdated() > 0) {
            builder.published(new Date(feedlyEntry.getUpdated()));
        } else {
            builder.published(new Date(feedlyEntry.getPublished()));
        }
        return builder;
    }

    private static String extractSummary(String content) {
        if (content == null) {
            return null;
        }
        return Html.fromHtml(content,
                new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String s) {
                        return new ShapeDrawable();
                    }
                }, null)
                .toString()
                .replace("\ufffc", BuildConfig.VERSION_NAME)
                .replaceAll("\n{2,}", "\n")
                .trim();
    }

    public static Builder builder() {
        return new AutoValue_FeedEntry.Builder();
    }

    public static Builder builder(FeedEntry feedEntry) {
        return new AutoValue_FeedEntry.Builder(feedEntry);
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder accountId(String accountId);

        public abstract Builder author(String author);

        public abstract FeedEntry build();

        public abstract Builder content(String content);

        public abstract Builder id(String id);

        public abstract Builder published(Date published);

        public abstract Builder readTimestamp(Date readTimeStamp);

        public abstract Builder starred(boolean starred);

        public abstract Builder subscriptionId(String subscriptionId);

        public abstract Builder subscriptionLabel(String subscriptionLabel);

        public abstract Builder subscriptionVisualUrl(String subscriptionVisualUrl);

        public abstract Builder summary(String summary);

        public abstract Builder thumbnail(String thumbnail);

        public abstract Builder title(String title);

        public abstract Builder unread(boolean unread);

        public abstract Builder url(String url);
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(EntryTable.ID, id());
        values.put(EntryTable.ACCOUNT_ID, accountId());
        return null;
    }
}
