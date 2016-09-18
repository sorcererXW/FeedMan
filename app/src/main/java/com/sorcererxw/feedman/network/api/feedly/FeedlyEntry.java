package com.sorcererxw.feedman.network.api.feedly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedlyEntry {

    /**
     * engagement : 15
     * published : 1367539068016
     * crawled : 1367539068016
     * title : NBC's reviled sci-fi drama 'Heroes' may get a second lease on life as Xbox Live exclusive
     * author : Nathan Ingraham
     * id : gRtwnDeqCDpZ42bXE9Sp7dNhm4R6NsipqFVbXn2XpDA=_13fb9d6f274:2ac9c5:f5718180
     * content : {"direction":"ltr","content":"..."}
     * updated : 1367539068016
     * unread : true
     * tags : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/inspiration","label":"inspiration"}]
     * origin : {"title":"The Verge -  All Posts","htmlUrl":"http://www.theverge.com/","streamId":"feed/http://www.theverge.com/rss/full.xml"}
     * alternate : [{"href":"http://www.theverge.com/2013/4/17/4236096/nbc-heroes-may-get-a-second-lease-on-life-on-xbox-live","type":"text/html"}]
     * categories : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/tech","label":"tech"}]
     */

    @SerializedName("engagement")
    private int mEngagement;
    @SerializedName("published")
    private long mPublished;
    @SerializedName("crawled")
    private long mCrawled;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("id")
    private String mId;
    /**
     * direction : ltr
     * content : ...
     */

    @SerializedName("content")
    private FeedlyEntryContent mContent;
    @SerializedName("updated")
    private long mUpdated;
    @SerializedName("unread")
    private boolean mUnread;
    /**
     * title : The Verge -  All Posts
     * htmlUrl : http://www.theverge.com/
     * streamId : feed/http://www.theverge.com/rss/full.xml
     */

    @SerializedName("origin")
    private FeedlyEntryOrigin mOrigin;
    /**
     * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/inspiration
     * label : inspiration
     */

    @SerializedName("tags")
    private List<TagsBean> mTags;
    /**
     * href : http://www.theverge.com/2013/4/17/4236096/nbc-heroes-may-get-a-second-lease-on-life-on-xbox-live
     * type : text/html
     */

    @SerializedName("alternate")
    private List<AlternateBean> mAlternate;
    /**
     * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/tech
     * label : tech
     */

    @SerializedName("categories")
    private List<CategoriesBean> mCategories;

    public int getEngagement() {
        return mEngagement;
    }

    public void setEngagement(int engagement) {
        mEngagement = engagement;
    }

    public long getPublished() {
        return mPublished;
    }

    public void setPublished(long published) {
        mPublished = published;
    }

    public long getCrawled() {
        return mCrawled;
    }

    public void setCrawled(long crawled) {
        mCrawled = crawled;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public FeedlyEntryContent getContent() {
        return mContent;
    }

    public void setContent(FeedlyEntryContent content) {
        mContent = content;
    }

    public long getUpdated() {
        return mUpdated;
    }

    public void setUpdated(long updated) {
        mUpdated = updated;
    }

    public boolean isUnread() {
        return mUnread;
    }

    public void setUnread(boolean unread) {
        mUnread = unread;
    }

    public FeedlyEntryOrigin getOrigin() {
        return mOrigin;
    }

    public void setOrigin(FeedlyEntryOrigin origin) {
        mOrigin = origin;
    }

    public List<TagsBean> getTags() {
        return mTags;
    }

    public void setTags(List<TagsBean> tags) {
        mTags = tags;
    }

    public List<AlternateBean> getAlternate() {
        return mAlternate;
    }

    public void setAlternate(List<AlternateBean> alternate) {
        mAlternate = alternate;
    }

    public List<CategoriesBean> getCategories() {
        return mCategories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        mCategories = categories;
    }

    public static class TagsBean {
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

    public static class AlternateBean {
        @SerializedName("href")
        private String mHref;
        @SerializedName("type")
        private String mType;

        public String getHref() {
            return mHref;
        }

        public void setHref(String href) {
            mHref = href;
        }

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }
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


    public FeedlyEntry() {
    }


    @Override
    public String toString() {
        return "FeedlyEntry{" +
                "\nmEngagement=" + mEngagement +
                "\n, mPublished=" + mPublished +
                "\n, mCrawled=" + mCrawled +
                "\n, mTitle='" + mTitle + '\'' +
                "\n, mAuthor='" + mAuthor + '\'' +
                "\n, mId='" + mId + '\'' +
                "\n, mContent=" + mContent +
                "\n, mUpdated=" + mUpdated +
                "\n, mUnread=" + mUnread +
                "\n, mOrigin=" + mOrigin +
                "\n, mTags=" + mTags +
                "\n, mAlternate=" + mAlternate +
                "\n, mCategories=" + mCategories +
                "\n}";
    }
}

