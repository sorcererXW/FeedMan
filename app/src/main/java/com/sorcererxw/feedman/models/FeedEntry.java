package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Html;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.BuildConfig;
import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyEntry;

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
    public interface Builder {
        Builder accountId(String accountId);

        Builder author(String author);

        FeedEntry build();

        Builder content(String content);

        Builder id(String id);

        Builder published(Date published);

        Builder readTimestamp(Date readTimeStamp);

        Builder starred(boolean starred);

        Builder subscriptionId(String subscriptionId);

        Builder subscriptionLabel(String subscriptionLabel);

        Builder subscriptionVisualUrl(String subscriptionVisualUrl);

        Builder summary(String summary);

        Builder thumbnail(String thumbnail);

        Builder title(String title);

        Builder unread(boolean unread);

        Builder url(String url);
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(EntryTable.ID, id());
        values.put(EntryTable.ACCOUNT_ID, accountId());
        return null;
    }
}
