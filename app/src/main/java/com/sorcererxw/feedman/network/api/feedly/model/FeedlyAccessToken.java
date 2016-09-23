package com.sorcererxw.feedman.network.api.feedly.model;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/23
 */

public class FeedlyAccessToken  {

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
}
