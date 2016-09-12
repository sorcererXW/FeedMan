package com.sorcererxw.feedman.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class Account implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mLabel);
        dest.writeLong(this.mSubscriptionsCount);
        dest.writeLong(this.mUnreadCount);
        dest.writeParcelable(this.mAccessToken, flags);
    }

    public Account() {
    }

    protected Account(Parcel in) {
        this.mId = in.readString();
        this.mLabel = in.readString();
        this.mSubscriptionsCount = in.readLong();
        this.mUnreadCount = in.readLong();
        this.mAccessToken = in.readParcelable(AccessToken.class.getClassLoader());
    }

    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
