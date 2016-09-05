package com.sorcererxw.feedman.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/4
 */
public class FeedlyFeedBean implements Parcelable {


    /**
     * language : en
     * state : alive
     * velocity : 180.3
     * curated : false
     * title : Engadget
     * id : feed/http://feeds.engadget.com/weblogsinc/engadget
     * topics : ["tech","gadgets"]
     * featured : false
     * sponsored : false
     * subscribers : 123
     * website : http://www.engadget.com/
     */

    @SerializedName("language")
    private String mLanguage;
    @SerializedName("state")
    private String mState;
    @SerializedName("velocity")
    private double mVelocity;
    @SerializedName("curated")
    private boolean mCurated;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("id")
    private String mId;
    @SerializedName("featured")
    private boolean mFeatured;
    @SerializedName("sponsored")
    private boolean mSponsored;
    @SerializedName("subscribers")
    private int mSubscribers;
    @SerializedName("website")
    private String mWebsite;
    @SerializedName("topics")
    private List<String> mTopics;

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public double getVelocity() {
        return mVelocity;
    }

    public void setVelocity(double velocity) {
        mVelocity = velocity;
    }

    public boolean isCurated() {
        return mCurated;
    }

    public void setCurated(boolean curated) {
        mCurated = curated;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isFeatured() {
        return mFeatured;
    }

    public void setFeatured(boolean featured) {
        mFeatured = featured;
    }

    public boolean isSponsored() {
        return mSponsored;
    }

    public void setSponsored(boolean sponsored) {
        mSponsored = sponsored;
    }

    public int getSubscribers() {
        return mSubscribers;
    }

    public void setSubscribers(int subscribers) {
        mSubscribers = subscribers;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public List<String> getTopics() {
        return mTopics;
    }

    public void setTopics(List<String> topics) {
        mTopics = topics;
    }

    @Override
    public String toString() {
        return "FeedlyFeedBean{" +
                "mLanguage='" + mLanguage + '\'' +
                ", mState='" + mState + '\'' +
                ", mVelocity=" + mVelocity +
                ", mCurated=" + mCurated +
                ", mTitle='" + mTitle + '\'' +
                ", mId='" + mId + '\'' +
                ", mFeatured=" + mFeatured +
                ", mSponsored=" + mSponsored +
                ", mSubscribers=" + mSubscribers +
                ", mWebsite='" + mWebsite + '\'' +
                ", mTopics=" + mTopics +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLanguage);
        dest.writeString(this.mState);
        dest.writeDouble(this.mVelocity);
        dest.writeByte(this.mCurated ? (byte) 1 : (byte) 0);
        dest.writeString(this.mTitle);
        dest.writeString(this.mId);
        dest.writeByte(this.mFeatured ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mSponsored ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mSubscribers);
        dest.writeString(this.mWebsite);
        dest.writeStringList(this.mTopics);
    }

    public FeedlyFeedBean() {
    }

    protected FeedlyFeedBean(Parcel in) {
        this.mLanguage = in.readString();
        this.mState = in.readString();
        this.mVelocity = in.readDouble();
        this.mCurated = in.readByte() != 0;
        this.mTitle = in.readString();
        this.mId = in.readString();
        this.mFeatured = in.readByte() != 0;
        this.mSponsored = in.readByte() != 0;
        this.mSubscribers = in.readInt();
        this.mWebsite = in.readString();
        this.mTopics = in.createStringArrayList();
    }

    public static final Parcelable.Creator<FeedlyFeedBean> CREATOR =
            new Parcelable.Creator<FeedlyFeedBean>() {
                @Override
                public FeedlyFeedBean createFromParcel(Parcel source) {
                    return new FeedlyFeedBean(source);
                }

                @Override
                public FeedlyFeedBean[] newArray(int size) {
                    return new FeedlyFeedBean[size];
                }
            };
}
