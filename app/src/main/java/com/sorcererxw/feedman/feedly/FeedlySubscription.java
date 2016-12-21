package com.sorcererxw.feedman.feedly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/21
 */

public class FeedlySubscription {

    /**
     * updated : 1367539068016
     * id : feed/http://feeds.feedburner.com/design-milk
     * title : Design Milk
     * website : http://design-milk.com
     * categories : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/design","label":"design"},{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/global.must","label":"must reads"}]
     * sortid : 26152F8F
     * visualUrl : http://pbs.twimg.com/profile_images/1765276661/DMLogoTM-carton-icon-facebook-twitter_bigger.jpg
     * added : 1367539068016
     */

    @SerializedName("updated")
    private long updated;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("website")
    private String website;
    @SerializedName("sortid")
    private String sortid;
    @SerializedName("visualUrl")
    private String visualUrl;
    @SerializedName("added")
    private long added;
    @SerializedName("categories")
    private List<CategoriesBean> categories;

    public long getUpdated() {
        return updated;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public String getSortid() {
        return sortid;
    }

    public String getVisualUrl() {
        return visualUrl == null ? "" : visualUrl;
    }

    public long getAdded() {
        return added;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public static class CategoriesBean {
        /**
         * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/design
         * label : design
         */

        @SerializedName("id")
        private String id;
        @SerializedName("label")
        private String label;

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }
    }
}
