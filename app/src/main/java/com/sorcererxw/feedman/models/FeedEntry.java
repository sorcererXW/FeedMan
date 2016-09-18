package com.sorcererxw.feedman.models;

import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

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

    public static FeedEntry from(Cursor cursor){
        return new AutoValue_FeedEntry.Builder()
                .build();
    }

    public static Builder builder(){
        return new AutoValue_FeedEntry.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder accountId(String str);

        public abstract Builder author(String str);

        public abstract FeedEntry build();

        public abstract Builder content(String str);

        public abstract Builder id(String str);

        public abstract Builder published(Date date);

        public abstract Builder readTimestamp(Date date);

        public abstract Builder starred(boolean z);

        public abstract Builder subscriptionId(String str);

        public abstract Builder subscriptionLabel(String str);

        public abstract Builder subscriptionVisualUrl(String str);

        public abstract Builder summary(String str);

        public abstract Builder thumbnail(String str);

        public abstract Builder title(String str);

        public abstract Builder unread(boolean z);

        public abstract Builder url(String str);
    }
}
