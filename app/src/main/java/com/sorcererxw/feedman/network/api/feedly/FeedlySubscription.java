package com.sorcererxw.feedman.network.api.feedly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedlySubscription {

    /**
     * visualUrl : http://pbs.twimg.com/profile_images/1765276661/DMLogoTM-carton-icon-facebook-twitter_bigger.jpg
     * title : Design Milk
     * id : feed/http://feeds.feedburner.com/design-milk
     * added : 1367539068016
     * updated : 1367539068016
     * website : http://design-milk.com
     * sortid : 26152F8F
     * categories : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/design","label":"design"},{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/global.must","label":"must reads"}]
     */

    @SerializedName("visualUrl")
    private String mVisualUrl;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("id")
    private String mId;
    @SerializedName("added")
    private long mAdded;
    @SerializedName("updated")
    private long mUpdated;
    @SerializedName("website")
    private String mWebsite;
    @SerializedName("sortid")
    private String mSortid;
    /**
     * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/design
     * label : design
     */

    @SerializedName("categories")
    private List<CategoriesBean> mCategories;

    public String getVisualUrl() {
        return mVisualUrl;
    }

    public void setVisualUrl(String visualUrl) {
        mVisualUrl = visualUrl;
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

    public long getAdded() {
        return mAdded;
    }

    public void setAdded(long added) {
        mAdded = added;
    }

    public long getUpdated() {
        return mUpdated;
    }

    public void setUpdated(long updated) {
        mUpdated = updated;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getSortid() {
        return mSortid;
    }

    public void setSortid(String sortid) {
        mSortid = sortid;
    }

    public List<CategoriesBean> getCategories() {
        return mCategories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        mCategories = categories;
    }

    public static class CategoriesBean {
        @SerializedName("id")
        private String mId;
        @SerializedName("label")
        private String mLabel;

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
    }
}
