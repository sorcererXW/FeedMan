package com.sorcererxw.feedman.feedly;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class FeedlyProfile {

    /**
     * twitter : jimsmith
     * google : 115562565652656565656
     * gender : male
     * id : c805fcbf-3acf-4302-a97e-d82f9d7c897f
     * picture : https://www.google.com/profile_images/1771656873/bigger.jpg
     * locale : en
     * wave : 2013.7
     * facebook :
     * email : jim.smith@gmail.com
     * givenName : Jim
     * reader : 9080770707070700
     * familyName : Smith
     */

    @SerializedName("twitter")
    private String twitter;
    @SerializedName("google")
    private String google;
    @SerializedName("gender")
    private String gender;
    @SerializedName("id")
    private String id;
    @SerializedName("picture")
    private String picture;
    @SerializedName("locale")
    private String locale;
    @SerializedName("wave")
    private String wave;
    @SerializedName("facebook")
    private String facebook;
    @SerializedName("email")
    private String email;
    @SerializedName("givenName")
    private String givenName;
    @SerializedName("reader")
    private String reader;
    @SerializedName("familyName")
    private String familyName;

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getWave() {
        return wave;
    }

    public void setWave(String wave) {
        this.wave = wave;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
