package com.sorcererxw.feedman.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class AccessToken implements Parcelable {

    /**
     * expires_in : 3920
     * state : ...
     * id : c805fcbf-3acf-4302-a97e-d82f9d7c897f
     * refresh_token : AQAA7rJ7InAiOjEsImEiOiJmZWVk...
     * plan : standard
     * token_type : Bearer
     * access_token : AQAAF4iTvPam_M4_dWheV_5NUL8E...
     */

    @SerializedName("expires_in")
    private int mExpiresIn;
    @SerializedName("state")
    private String mState;
    @SerializedName("id")
    private String mId;
    @SerializedName("refresh_token")
    private String mRefreshToken;
    @SerializedName("plan")
    private String mPlan;
    @SerializedName("token_type")
    private String mTokenType;
    @SerializedName("access_token")
    private String mAccessToken;

    public int getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        mExpiresIn = expiresIn;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }

    public String getPlan() {
        return mPlan;
    }

    public void setPlan(String plan) {
        mPlan = plan;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "mExpiresIn=" + mExpiresIn +
                ", mState='" + mState + '\'' +
                ", mId='" + mId + '\'' +
                ", mRefreshToken='" + mRefreshToken + '\'' +
                ", mPlan='" + mPlan + '\'' +
                ", mTokenType='" + mTokenType + '\'' +
                ", mAccessToken='" + mAccessToken + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mExpiresIn);
        dest.writeString(this.mState);
        dest.writeString(this.mId);
        dest.writeString(this.mRefreshToken);
        dest.writeString(this.mPlan);
        dest.writeString(this.mTokenType);
        dest.writeString(this.mAccessToken);
    }

    public AccessToken() {
    }

    protected AccessToken(Parcel in) {
        this.mExpiresIn = in.readInt();
        this.mState = in.readString();
        this.mId = in.readString();
        this.mRefreshToken = in.readString();
        this.mPlan = in.readString();
        this.mTokenType = in.readString();
        this.mAccessToken = in.readString();
    }

    public static final Parcelable.Creator<AccessToken> CREATOR =
            new Parcelable.Creator<AccessToken>() {
                @Override
                public AccessToken createFromParcel(Parcel source) {
                    return new AccessToken(source);
                }

                @Override
                public AccessToken[] newArray(int size) {
                    return new AccessToken[size];
                }
            };
}
