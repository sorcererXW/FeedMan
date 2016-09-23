package com.sorcererxw.feedman.network.api.feedly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedlyEntry {

    /**
     * engagement : 1476
     * thumbnail : [{"url":"https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png"}]
     * published : 1452614967000
     * recrawled : 1452618026719
     * crawled : 1452614994867
     * fingerprint : 17c5dd0d
     * canonical : [{"href":"http://techcrunch.com/2016/01/12/microsoft-today-ends-support-for-windows-8-old-versions-of-internet-explorer/?ncid=rss","type":"text/html"}]
     * title : Microsoft Today Ends Support For Windows 8, Old Versions Of Internet Explorer
     * author : Sarah Perez
     * id : Xne8uW/IUiZhV1EuO2ZMzIrc2Ak6NlhGjboZ+Yk0rJ8=_1523699cbb3:2aa0463:e47a7aef
     * enclosure : [{"href":"https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png","type":"image/png"},{"href":"http://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png?w=150"},{"href":"http://2.gravatar.com/avatar/5225bb627e112543aa03bf3b2958be3f?s=96&d=identicon&r=G"},{"href":"https://tctechcrunch2011.files.wordpress.com/2016/01/edge_phase2_banner_cortana_1400px.jpg?w=680"}]
     * summary : {"direction":"ltr","content":"<img height=\"382\" alt=\"Microsoft-Internet-Explorer-10\" width=\"680\" class=\"wp-post-image\" src=\"https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png?w=680\"> Microsoft\u2019s push towards Windows 10 continues. Today, Microsoft is ending support for Windows 8, as well as older versions of its Internet Explorer web browser, IE 8, IE 9, and IE 10. For end users, that doesn\u2019t mean the software instantly becomes non-functional, but that it will longer be updated with bug fixes or other security patches."}
     * originId : http://techcrunch.com/?p=1261251
     * updated : 1452614967000
     * unread : false
     * engagementRate : 7.88
     * tags : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/global.saved"},{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/Microsoft","label":"Microsoft"}]
     * categories : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/Tech","label":"Tech"},{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/global.must","label":"Must Read"}]
     * visual : {"contentType":"image/png","height":1687,"url":"https://tctechcrunch2011.files.wordpress.com/%2F2016%2F01%2Fmicrosoft-internet-explorer-10.png","width":3000}
     * origin : {"title":"TechCrunch","htmlUrl":"http://techcrunch.com","streamId":"feed/http://feeds.feedburner.com/Techcrunch"}
     * alternate : [{"href":"http://feedproxy.google.com/~r/Techcrunch/~3/iEm1aA_M_dw/","type":"text/html"}]
     * keywords : ["TC","Microsoft","Internet-Explorer","Windows 10","Microsoft Edge","PCs"]
     */

    @SerializedName("engagement")
    private int mEngagement;
    @SerializedName("published")
    private long mPublished;
    @SerializedName("recrawled")
    private long mRecrawled;
    @SerializedName("crawled")
    private long mCrawled;
    @SerializedName("fingerprint")
    private String mFingerprint;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("id")
    private String mId;
    /**
     * direction : ltr
     * content : <img height="382" alt="Microsoft-Internet-Explorer-10" width="680" class="wp-post-image" src="https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png?w=680"> Microsoft’s push towards Windows 10 continues. Today, Microsoft is ending support for Windows 8, as well as older versions of its Internet Explorer web browser, IE 8, IE 9, and IE 10. For end users, that doesn’t mean the software instantly becomes non-functional, but that it will longer be updated with bug fixes or other security patches.
     */

    @SerializedName("summary")
    private SummaryBean mSummary;
    @SerializedName("originId")
    private String mOriginId;
    @SerializedName("updated")
    private long mUpdated;
    @SerializedName("unread")
    private boolean mUnread;
    @SerializedName("engagementRate")
    private double mEngagementRate;
    /**
     * contentType : image/png
     * height : 1687
     * url : https://tctechcrunch2011.files.wordpress.com/%2F2016%2F01%2Fmicrosoft-internet-explorer-10.png
     * width : 3000
     */

    @SerializedName("visual")
    private VisualBean mVisual;
    /**
     * title : TechCrunch
     * htmlUrl : http://techcrunch.com
     * streamId : feed/http://feeds.feedburner.com/Techcrunch
     */

    @SerializedName("origin")
    private OriginBean mOrigin;
    /**
     * url : https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png
     */

    @SerializedName("thumbnail")
    private List<ThumbnailBean> mThumbnail;
    /**
     * href : http://techcrunch.com/2016/01/12/microsoft-today-ends-support-for-windows-8-old-versions-of-internet-explorer/?ncid=rss
     * type : text/html
     */

    @SerializedName("canonical")
    private List<CanonicalBean> mCanonical;
    /**
     * href : https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png
     * type : image/png
     */

    @SerializedName("enclosure")
    private List<EnclosureBean> mEnclosure;
    /**
     * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/global.saved
     */

    @SerializedName("tags")
    private List<TagsBean> mTags;
    /**
     * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/Tech
     * label : Tech
     */

    @SerializedName("categories")
    private List<FeedlyCategory> mCategories;
    /**
     * href : http://feedproxy.google.com/~r/Techcrunch/~3/iEm1aA_M_dw/
     * type : text/html
     */

    @SerializedName("alternate")
    private List<AlternateBean> mAlternate;
    @SerializedName("keywords")
    private List<String> mKeywords;

    public int getEngagement() {
        return mEngagement;
    }

    public long getPublished() {
        return mPublished;
    }

    public long getRecrawled() {
        return mRecrawled;
    }

    public long getCrawled() {
        return mCrawled;
    }

    public String getFingerprint() {
        return mFingerprint;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getId() {
        return mId;
    }

    public SummaryBean getSummary() {
        return mSummary;
    }

    public String getOriginId() {
        return mOriginId;
    }

    public long getUpdated() {
        return mUpdated;
    }

    public boolean isUnread() {
        return mUnread;
    }

    public double getEngagementRate() {
        return mEngagementRate;
    }

    public VisualBean getVisual() {
        return mVisual;
    }

    public OriginBean getOrigin() {
        return mOrigin;
    }

    public List<ThumbnailBean> getThumbnail() {
        return mThumbnail;
    }

    public List<CanonicalBean> getCanonical() {
        return mCanonical;
    }

    public List<EnclosureBean> getEnclosure() {
        return mEnclosure;
    }

    public List<TagsBean> getTags() {
        return mTags;
    }

    public List<FeedlyCategory> getCategories() {
        return mCategories;
    }

    public List<AlternateBean> getAlternate() {
        return mAlternate;
    }

    public List<String> getKeywords() {
        return mKeywords;
    }

    public String getUrl() {
        if (mAlternate == null || mAlternate.size() <= 0) {
            return null;
        }
        return mAlternate.get(0).getHref();
    }

    public String getSubscriptionId() {
        if (mOrigin != null) {
            return mOrigin.getStreamId();
        }
        return null;
    }

    public String getContent() {
        if (mSummary != null) {
            return mSummary.getContent();
        }
        return null;
    }

    public String getThumbnailUrl() {
        if (mVisual != null
                && mVisual.getContentType() != null
                && mVisual.getContentType().startsWith("image/")) {
            return mVisual.getUrl();
        }
        if (mThumbnail == null || mThumbnail.isEmpty()) {
            return null;
        }
        return mThumbnail.get(0).getUrl();
    }

    public static class SummaryBean {
        @SerializedName("direction")
        private String mDirection;
        @SerializedName("content")
        private String mContent;

        public String getDirection() {
            return mDirection;
        }

        public void setDirection(String direction) {
            mDirection = direction;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }
    }

    public static class VisualBean {
        @SerializedName("contentType")
        private String mContentType;
        @SerializedName("height")
        private int mHeight;
        @SerializedName("url")
        private String mUrl;
        @SerializedName("width")
        private int mWidth;

        public String getContentType() {
            return mContentType;
        }

        public void setContentType(String contentType) {
            mContentType = contentType;
        }

        public int getHeight() {
            return mHeight;
        }

        public void setHeight(int height) {
            mHeight = height;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            mUrl = url;
        }

        public int getWidth() {
            return mWidth;
        }

        public void setWidth(int width) {
            mWidth = width;
        }
    }

    public static class OriginBean {
        @SerializedName("title")
        private String mTitle;
        @SerializedName("htmlUrl")
        private String mHtmlUrl;
        @SerializedName("streamId")
        private String mStreamId;

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getHtmlUrl() {
            return mHtmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            mHtmlUrl = htmlUrl;
        }

        public String getStreamId() {
            return mStreamId;
        }

        public void setStreamId(String streamId) {
            mStreamId = streamId;
        }
    }

    public static class ThumbnailBean {
        @SerializedName("url")
        private String mUrl;

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            mUrl = url;
        }
    }

    public static class CanonicalBean {
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

    public static class EnclosureBean {
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

    public static class TagsBean {
        @SerializedName("id")
        private String mId;

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
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
}

