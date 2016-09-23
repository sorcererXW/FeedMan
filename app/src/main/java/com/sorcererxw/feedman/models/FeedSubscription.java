package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.SubscriptionTable;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyCategory;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlyStream;
import com.sorcererxw.feedman.network.api.feedly.model.FeedlySubscription;
import com.sorcererxw.feedman.util.UniversalConverter;

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

    public static Builder builder() {
        return new AutoValue_FeedSubscription.Builder();
    }

    public static FeedSubscription from(final Account account,
                                        FeedlySubscription feedlySubscription) {
        return builder()
                .accountId(account.id())
                .id(feedlySubscription.getId())
                .title(feedlySubscription.getTitle())
                .visualUrl(feedlySubscription.getVisualUrl())
                .website(feedlySubscription.getWebsite())
                .categories(UniversalConverter.convertList(
                        feedlySubscription.getCategories(),
                        new UniversalConverter.Converter<FeedlyCategory, FeedCategory>() {
                            @Override public FeedCategory convert(FeedlyCategory origin) {
                                return FeedCategory.from(account, origin);
                            }
                        })
                )
                .build();
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
    public interface Builder {
        Builder accountId(String accountId);

        FeedSubscription build();

        Builder id(String id);

        Builder title(String title);

        Builder unread(int unread);

        Builder visualUrl(String visualUrl);

        Builder website(String website);

        Builder categories(List<FeedCategory> categoryList);
    }
}
