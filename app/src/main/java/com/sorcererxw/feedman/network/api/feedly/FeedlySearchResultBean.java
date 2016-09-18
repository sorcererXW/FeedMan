package com.sorcererxw.feedman.network.api.feedly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/3
 */
public class FeedlySearchResultBean {


    /**
     * results : [{"velocity":47.8,"curated":true,"title":"Daring Fireball","feedId":"feed/http://daringfireball.net/index.xml","featured":true,"subscribers":359471,"website":"http://daringfireball.net/"},{"velocity":36.4,"curated":true,"title":"MacRumors","feedId":"feed/http://www.macrumors.com/macrumors.xml","featured":true,"subscribers":322765,"website":"http://www.macrumors.com"},{"velocity":22.9,"curated":true,"title":"The Apple Blog","feedId":"feed/http://theappleblog.com/feed/","featured":true,"subscribers":118576,"website":"http://gigaom.com"}]
     * hint : apple
     * related : ["tech","technology","osx","macintosh","mac"]
     */

    @SerializedName("hint")
    private String mHint;
    /**
     * velocity : 47.8
     * curated : true
     * title : Daring Fireball
     * feedId : feed/http://daringfireball.net/index.xml
     * featured : true
     * subscribers : 359471
     * website : http://daringfireball.net/
     */

    @SerializedName("results")
    private List<ResultsBean> mResults;
    @SerializedName("related")
    private List<String> mRelated;

    public String getHint() {
        return mHint;
    }

    public void setHint(String hint) {
        mHint = hint;
    }

    public List<ResultsBean> getResults() {
        return mResults;
    }

    public void setResults(List<ResultsBean> results) {
        mResults = results;
    }

    public List<String> getRelated() {
        return mRelated;
    }

    public void setRelated(List<String> related) {
        mRelated = related;
    }

    public static class ResultsBean {
        @SerializedName("velocity")
        private double mVelocity;
        @SerializedName("curated")
        private boolean mCurated;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("feedId")
        private String mFeedId;
        @SerializedName("featured")
        private boolean mFeatured;
        @SerializedName("subscribers")
        private int mSubscribers;
        @SerializedName("website")
        private String mWebsite;

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

        public String getFeedId() {
            return mFeedId;
        }

        public void setFeedId(String feedId) {
            mFeedId = feedId;
        }

        public boolean isFeatured() {
            return mFeatured;
        }

        public void setFeatured(boolean featured) {
            mFeatured = featured;
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

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "mVelocity=" + mVelocity +
                    ", mCurated=" + mCurated +
                    ", mTitle='" + mTitle + '\'' +
                    ", mFeedId='" + mFeedId + '\'' +
                    ", mFeatured=" + mFeatured +
                    ", mSubscribers=" + mSubscribers +
                    ", mWebsite='" + mWebsite + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FeedlySearchResultBean{" +
                "mHint='" + mHint + '\'' +
                ", mResults=" + mResults +
                ", mRelated=" + mRelated +
                '}';
    }
}
