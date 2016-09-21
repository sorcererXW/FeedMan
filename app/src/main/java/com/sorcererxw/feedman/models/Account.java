package com.sorcererxw.feedman.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.database.tables.AccountTable;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class Account {
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public long getSubscriptionsCount() {
        return mSubscriptionsCount;
    }

    public void setSubscriptionsCount(long subscriptionsCount) {
        mSubscriptionsCount = subscriptionsCount;
    }

    public long getUnreadCount() {
        return mUnreadCount;
    }

    public void setUnreadCount(long unreadCount) {
        mUnreadCount = unreadCount;
    }

    public AccessToken getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        mAccessToken = accessToken;
    }

    public static class Builder {
        private Account mAccount;

        public Builder() {
            mAccount = new Account();
        }

        public Builder subscriptionsCount(long count) {
            mAccount.setSubscriptionsCount(count);
            return this;
        }

        public Builder unreadCount(long count) {
            mAccount.setUnreadCount(count);
            return this;
        }

        public Builder label(String label) {
            mAccount.setLabel(label);
            return this;
        }

        public Builder accessToken(AccessToken token) {
            mAccount.setAccessToken(token);
            return this;
        }

        public Builder id(String id) {
            mAccount.setId(id);
            return this;
        }

        public Account build() {
            return mAccount;
        }
    }

    private String mId;
    private String mLabel;
    private long mSubscriptionsCount;
    private long mUnreadCount;
    private AccessToken mAccessToken;

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AccountTable.LABEL, mLabel);
        values.put(AccountTable.ID, mId);
        values.put(AccountTable.ACCESS_TOKEN, mAccessToken.getAccessToken());
        values.put(AccountTable.REFRESH_TOKEN, mAccessToken.getRefreshToken());
        values.put(AccountTable.TYPE, "feedly");
        return values;
    }

    public static Account from(Cursor cursor) {
        Builder builder = new Builder()
                .id(DB.Getter.getString(cursor, AccountTable.ID))
                .label(DB.Getter.getString(cursor, AccountTable.LABEL));
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(DB.Getter.getString(cursor, AccountTable.ACCESS_TOKEN));
        accessToken
                .setRefreshToken(DB.Getter.getString(cursor, AccountTable.REFRESH_TOKEN));
        builder.accessToken(accessToken);
        return builder.build();
    }
}
