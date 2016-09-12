package com.sorcererxw.feedman.api.feedly;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/4
 */
public class FeedlyEntryBean implements Parcelable {

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
    private List<CategoriesBean> mCategories;
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

    public void setEngagement(int engagement) {
        mEngagement = engagement;
    }

    public long getPublished() {
        return mPublished;
    }

    public void setPublished(long published) {
        mPublished = published;
    }

    public long getRecrawled() {
        return mRecrawled;
    }

    public void setRecrawled(long recrawled) {
        mRecrawled = recrawled;
    }

    public long getCrawled() {
        return mCrawled;
    }

    public void setCrawled(long crawled) {
        mCrawled = crawled;
    }

    public String getFingerprint() {
        return mFingerprint;
    }

    public void setFingerprint(String fingerprint) {
        mFingerprint = fingerprint;
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

    public SummaryBean getSummary() {
        return mSummary;
    }

    public void setSummary(SummaryBean summary) {
        mSummary = summary;
    }

    public String getOriginId() {
        return mOriginId;
    }

    public void setOriginId(String originId) {
        mOriginId = originId;
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

    public double getEngagementRate() {
        return mEngagementRate;
    }

    public void setEngagementRate(double engagementRate) {
        mEngagementRate = engagementRate;
    }

    public VisualBean getVisual() {
        return mVisual;
    }

    public void setVisual(VisualBean visual) {
        mVisual = visual;
    }

    public OriginBean getOrigin() {
        return mOrigin;
    }

    public void setOrigin(OriginBean origin) {
        mOrigin = origin;
    }

    public List<ThumbnailBean> getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(List<ThumbnailBean> thumbnail) {
        mThumbnail = thumbnail;
    }

    public List<CanonicalBean> getCanonical() {
        return mCanonical;
    }

    public void setCanonical(List<CanonicalBean> canonical) {
        mCanonical = canonical;
    }

    public List<EnclosureBean> getEnclosure() {
        return mEnclosure;
    }

    public void setEnclosure(List<EnclosureBean> enclosure) {
        mEnclosure = enclosure;
    }

    public List<TagsBean> getTags() {
        return mTags;
    }

    public void setTags(List<TagsBean> tags) {
        mTags = tags;
    }

    public List<CategoriesBean> getCategories() {
        return mCategories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        mCategories = categories;
    }

    public List<AlternateBean> getAlternate() {
        return mAlternate;
    }

    public void setAlternate(List<AlternateBean> alternate) {
        mAlternate = alternate;
    }

    public List<String> getKeywords() {
        return mKeywords;
    }

    public void setKeywords(List<String> keywords) {
        mKeywords = keywords;
    }

    public static class SummaryBean implements Parcelable {
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

        @Override
        public String toString() {
            return "SummaryBean{" +
                    "mDirection='" + mDirection + '\'' +
                    ", mContent='" + mContent + '\'' +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mDirection);
            dest.writeString(this.mContent);
        }

        public SummaryBean() {
        }

        protected SummaryBean(Parcel in) {
            this.mDirection = in.readString();
            this.mContent = in.readString();
        }

        public static final Creator<SummaryBean> CREATOR = new Creator<SummaryBean>() {
            @Override
            public SummaryBean createFromParcel(Parcel source) {
                return new SummaryBean(source);
            }

            @Override
            public SummaryBean[] newArray(int size) {
                return new SummaryBean[size];
            }
        };
    }

    public static class VisualBean implements Parcelable {
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

        @Override
        public String toString() {
            return "VisualBean{" +
                    "mContentType='" + mContentType + '\'' +
                    ", mHeight=" + mHeight +
                    ", mUrl='" + mUrl + '\'' +
                    ", mWidth=" + mWidth +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mContentType);
            dest.writeInt(this.mHeight);
            dest.writeString(this.mUrl);
            dest.writeInt(this.mWidth);
        }

        public VisualBean() {
        }

        protected VisualBean(Parcel in) {
            this.mContentType = in.readString();
            this.mHeight = in.readInt();
            this.mUrl = in.readString();
            this.mWidth = in.readInt();
        }

        public static final Creator<VisualBean> CREATOR = new Creator<VisualBean>() {
            @Override
            public VisualBean createFromParcel(Parcel source) {
                return new VisualBean(source);
            }

            @Override
            public VisualBean[] newArray(int size) {
                return new VisualBean[size];
            }
        };
    }

    public static class OriginBean implements Parcelable {
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

        @Override
        public String toString() {
            return "OriginBean{" +
                    "mTitle='" + mTitle + '\'' +
                    ", mHtmlUrl='" + mHtmlUrl + '\'' +
                    ", mStreamId='" + mStreamId + '\'' +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mTitle);
            dest.writeString(this.mHtmlUrl);
            dest.writeString(this.mStreamId);
        }

        public OriginBean() {
        }

        protected OriginBean(Parcel in) {
            this.mTitle = in.readString();
            this.mHtmlUrl = in.readString();
            this.mStreamId = in.readString();
        }

        public static final Creator<OriginBean> CREATOR = new Creator<OriginBean>() {
            @Override
            public OriginBean createFromParcel(Parcel source) {
                return new OriginBean(source);
            }

            @Override
            public OriginBean[] newArray(int size) {
                return new OriginBean[size];
            }
        };
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

        @Override
        public String toString() {
            return "ThumbnailBean{" +
                    "mUrl='" + mUrl + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "CanonicalBean{" +
                    "mType='" + mType + '\'' +
                    ", mHref='" + mHref + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "EnclosureBean{" +
                    "mType='" + mType + '\'' +
                    ", mHref='" + mHref + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "TagsBean{" +
                    "mId='" + mId + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "CategoriesBean{" +
                    "mId='" + mId + '\'' +
                    ", mLabel='" + mLabel + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "AlternateBean{" +
                    "mType='" + mType + '\'' +
                    ", mHref='" + mHref + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FeedlyEntryBean{" +
                "mEngagement=" + mEngagement +
                ", mPublished=" + mPublished +
                ", mRecrawled=" + mRecrawled +
                ", mCrawled=" + mCrawled +
                ", mFingerprint='" + mFingerprint + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mId='" + mId + '\'' +
                ", mSummary=" + mSummary +
                ", mOriginId='" + mOriginId + '\'' +
                ", mUpdated=" + mUpdated +
                ", mUnread=" + mUnread +
                ", mEngagementRate=" + mEngagementRate +
                ", mVisual=" + mVisual +
                ", mOrigin=" + mOrigin +
                ", mThumbnail=" + mThumbnail +
                ", mCanonical=" + mCanonical +
                ", mEnclosure=" + mEnclosure +
                ", mTags=" + mTags +
                ", mCategories=" + mCategories +
                ", mAlternate=" + mAlternate +
                ", mKeywords=" + mKeywords +
                '}';
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mEngagement);
        dest.writeLong(this.mPublished);
        dest.writeLong(this.mRecrawled);
        dest.writeLong(this.mCrawled);
        dest.writeString(this.mFingerprint);
        dest.writeString(this.mTitle);
        dest.writeString(this.mAuthor);
        dest.writeString(this.mId);
        dest.writeParcelable(this.mSummary, flags);
        dest.writeString(this.mOriginId);
        dest.writeLong(this.mUpdated);
        dest.writeByte(this.mUnread ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.mEngagementRate);
        dest.writeParcelable(this.mVisual, flags);
        dest.writeParcelable(this.mOrigin, flags);
        dest.writeList(this.mThumbnail);
        dest.writeList(this.mCanonical);
        dest.writeList(this.mEnclosure);
        dest.writeList(this.mTags);
        dest.writeList(this.mCategories);
        dest.writeList(this.mAlternate);
        dest.writeStringList(this.mKeywords);
    }

    public FeedlyEntryBean() {
    }

    protected FeedlyEntryBean(Parcel in) {
        this.mEngagement = in.readInt();
        this.mPublished = in.readLong();
        this.mRecrawled = in.readLong();
        this.mCrawled = in.readLong();
        this.mFingerprint = in.readString();
        this.mTitle = in.readString();
        this.mAuthor = in.readString();
        this.mId = in.readString();
        this.mSummary = in.readParcelable(SummaryBean.class.getClassLoader());
        this.mOriginId = in.readString();
        this.mUpdated = in.readLong();
        this.mUnread = in.readByte() != 0;
        this.mEngagementRate = in.readDouble();
        this.mVisual = in.readParcelable(VisualBean.class.getClassLoader());
        this.mOrigin = in.readParcelable(OriginBean.class.getClassLoader());
        this.mThumbnail = new ArrayList<ThumbnailBean>();
        in.readList(this.mThumbnail, ThumbnailBean.class.getClassLoader());
        this.mCanonical = new ArrayList<CanonicalBean>();
        in.readList(this.mCanonical, CanonicalBean.class.getClassLoader());
        this.mEnclosure = new ArrayList<EnclosureBean>();
        in.readList(this.mEnclosure, EnclosureBean.class.getClassLoader());
        this.mTags = new ArrayList<TagsBean>();
        in.readList(this.mTags, TagsBean.class.getClassLoader());
        this.mCategories = new ArrayList<CategoriesBean>();
        in.readList(this.mCategories, CategoriesBean.class.getClassLoader());
        this.mAlternate = new ArrayList<AlternateBean>();
        in.readList(this.mAlternate, AlternateBean.class.getClassLoader());
        this.mKeywords = in.createStringArrayList();
    }

    public static final Parcelable.Creator<FeedlyEntryBean> CREATOR =
            new Parcelable.Creator<FeedlyEntryBean>() {
                @Override
                public FeedlyEntryBean createFromParcel(Parcel source) {
                    return new FeedlyEntryBean(source);
                }

                @Override
                public FeedlyEntryBean[] newArray(int size) {
                    return new FeedlyEntryBean[size];
                }
            };
}
