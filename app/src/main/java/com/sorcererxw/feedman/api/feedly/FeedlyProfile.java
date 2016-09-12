package com.sorcererxw.feedman.api.feedly;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/9
 */
public class FeedlyProfile {

    /**
     * wave : 2013.7
     * locale : en
     * gender : male
     * givenName : Jim
     * facebook :
     * id : c805fcbf-3acf-4302-a97e-d82f9d7c897f
     * familyName : Smith
     * twitter : jimsmith
     * picture : https://www.google.com/profile_images/1771656873/bigger.jpg
     * reader : 9080770707070700
     * google : 115562565652656565656
     * email : jim.smith@gmail.com
     */

    @SerializedName("wave")
    private String mWave;
    @SerializedName("locale")
    private String mLocale;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("givenName")
    private String mGivenName;
    @SerializedName("facebook")
    private String mFacebook;
    @SerializedName("id")
    private String mId;
    @SerializedName("familyName")
    private String mFamilyName;
    @SerializedName("twitter")
    private String mTwitter;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("reader")
    private String mReader;
    @SerializedName("google")
    private String mGoogle;
    @SerializedName("email")
    private String mEmail;

    public String getWave() {
        return mWave;
    }

    public void setWave(String wave) {
        mWave = wave;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String locale) {
        mLocale = locale;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getGivenName() {
        return mGivenName;
    }

    public void setGivenName(String givenName) {
        mGivenName = givenName;
    }

    public String getFacebook() {
        return mFacebook;
    }

    public void setFacebook(String facebook) {
        mFacebook = facebook;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFamilyName() {
        return mFamilyName;
    }

    public void setFamilyName(String familyName) {
        mFamilyName = familyName;
    }

    public String getTwitter() {
        return mTwitter;
    }

    public void setTwitter(String twitter) {
        mTwitter = twitter;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getReader() {
        return mReader;
    }

    public void setReader(String reader) {
        mReader = reader;
    }

    public String getGoogle() {
        return mGoogle;
    }

    public void setGoogle(String google) {
        mGoogle = google;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
