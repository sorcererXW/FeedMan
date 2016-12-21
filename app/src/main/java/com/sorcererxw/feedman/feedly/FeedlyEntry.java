package com.sorcererxw.feedman.feedly;

import android.text.Html;

import com.google.gson.annotations.SerializedName;
import com.sorcererxw.feedman.util.TextUtil;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class FeedlyEntry {

    /**
     * enclosure : [{"href":"https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png","type":"image/png"},{"href":"http://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png?w=150"},{"href":"http://2.gravatar.com/avatar/5225bb627e112543aa03bf3b2958be3f?s=96&d=identicon&r=G"},{"href":"https://tctechcrunch2011.files.wordpress.com/2016/01/edge_phase2_banner_cortana_1400px.jpg?w=680"}]
     * updated : 1452614967000
     * engagementRate : 7.88
     * canonical : [{"href":"http://techcrunch.com/2016/01/12/microsoft-today-ends-support-for-windows-8-old-versions-of-internet-explorer/?ncid=rss","type":"text/html"}]
     * summary : {"content":"<img height=\"382\" alt=\"Microsoft-Internet-Explorer-10\" width=\"680\" class=\"wp-post-image\" src=\"https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png?w=680\"> Microsoft\u2019s push towards Windows 10 continues. Today, Microsoft is ending support for Windows 8, as well as older versions of its Internet Explorer web browser, IE 8, IE 9, and IE 10. For end users, that doesn\u2019t mean the software instantly becomes non-functional, but that it will longer be updated with bug fixes or other security patches.","direction":"ltr"}
     * fingerprint : 17c5dd0d
     * id : Xne8uW/IUiZhV1EuO2ZMzIrc2Ak6NlhGjboZ+Yk0rJ8=_1523699cbb3:2aa0463:e47a7aef
     * unread : false
     * title : Microsoft Today Ends Support For Windows 8, Old Versions Of Internet Explorer
     * categories : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/Tech","label":"Tech"},{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/global.must","label":"Must Read"}]
     * origin : {"htmlUrl":"http://techcrunch.com","title":"TechCrunch","streamId":"feed/http://feeds.feedburner.com/Techcrunch"}
     * author : Sarah Perez
     * originId : http://techcrunch.com/?p=1261251
     * visual : {"height":1687,"url":"https://tctechcrunch2011.files.wordpress.com/%2F2016%2F01%2Fmicrosoft-internet-explorer-10.png","width":3000,"contentType":"image/png"}
     * crawled : 1452614994867
     * recrawled : 1452618026719
     * engagement : 1476
     * tags : [{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/global.saved"},{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/Microsoft","label":"Microsoft"}]
     * alternate : [{"href":"http://feedproxy.google.com/~r/Techcrunch/~3/iEm1aA_M_dw/","type":"text/html"}]
     * thumbnail : [{"url":"https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png"}]
     * keywords : ["TC","Microsoft","Internet-Explorer","Windows 10","Microsoft Edge","PCs"]
     * published : 1452614967000
     */

    @SerializedName("updated")
    private long updated;
    @SerializedName("summary")
    private SummaryBean summary;
    @SerializedName("id")
    private String id;
    @SerializedName("unread")
    private boolean unread;
    @SerializedName("title")
    private String title;
    @SerializedName("origin")
    private OriginBean origin;
    @SerializedName("author")
    private String author;
    @SerializedName("originId")
    private String originId;
    @SerializedName("visual")
    private VisualBean visual;
    @SerializedName("published")
    private long published;
    @SerializedName("enclosure")
    private List<EnclosureBean> enclosure;
    @SerializedName("canonical")
    private List<CanonicalBean> canonical;
    @SerializedName("categories")
    private List<CategoriesBean> categories;
    @SerializedName("tags")
    private List<TagsBean> tags;
    @SerializedName("alternate")
    private List<AlternateBean> alternate;
    @SerializedName("thumbnail")
    private List<ThumbnailBean> thumbnail;
    @SerializedName("keywords")
    private List<String> keywords;
    @SerializedName("content")
    private ContentBean content;

    public static class ContentBean {
        @SerializedName("content")
        private String content;
    }

    public static class SummaryBean {
        /**
         * content : <img height="382" alt="Microsoft-Internet-Explorer-10" width="680" class="wp-post-image" src="https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png?w=680"> Microsoft’s push towards Windows 10 continues. Today, Microsoft is ending support for Windows 8, as well as older versions of its Internet Explorer web browser, IE 8, IE 9, and IE 10. For end users, that doesn’t mean the software instantly becomes non-functional, but that it will longer be updated with bug fixes or other security patches.
         * direction : ltr
         */

        @SerializedName("content")
        private String content;
        @SerializedName("direction")
        private String direction;

        @Override
        public String toString() {
            return "SummaryBean{" +
                    "\ncontent='" + content + '\'' +
                    "\n, direction='" + direction + '\'' +
                    "\n}";
        }
    }

    public static class OriginBean {
        /**
         * htmlUrl : http://techcrunch.com
         * title : TechCrunch
         * streamId : feed/http://feeds.feedburner.com/Techcrunch
         */

        @SerializedName("htmlUrl")
        private String htmlUrl;
        @SerializedName("title")
        private String title;
        @SerializedName("streamId")
        private String streamId;

        @Override
        public String toString() {
            return "OriginBean{" +
                    "\nhtmlUrl='" + htmlUrl + '\'' +
                    "\n, title='" + title + '\'' +
                    "\n, streamId='" + streamId + '\'' +
                    "\n}";
        }
    }

    public static class VisualBean {
        /**
         * height : 1687
         * url : https://tctechcrunch2011.files.wordpress.com/%2F2016%2F01%2Fmicrosoft-internet-explorer-10.png
         * width : 3000
         * contentType : image/png
         */

        @SerializedName("height")
        private int height;
        @SerializedName("url")
        private String url;
        @SerializedName("width")
        private int width;
        @SerializedName("contentType")
        private String contentType;

        @Override
        public String toString() {
            return "VisualBean{" +
                    "\nheight=" + height +
                    "\n, url='" + url + '\'' +
                    "\n, width=" + width +
                    "\n, contentType='" + contentType + '\'' +
                    "\n}";
        }
    }

    public static class EnclosureBean {
        /**
         * href : https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png
         * type : image/png
         */

        @SerializedName("href")
        private String href;
        @SerializedName("type")
        private String type;

        @Override
        public String toString() {
            return "EnclosureBean{" +
                    "\nhref='" + href + '\'' +
                    "\n, type='" + type + '\'' +
                    "\n}";
        }
    }

    public static class CanonicalBean {
        /**
         * href : http://techcrunch.com/2016/01/12/microsoft-today-ends-support-for-windows-8-old-versions-of-internet-explorer/?ncid=rss
         * type : text/html
         */

        @SerializedName("href")
        private String href;
        @SerializedName("type")
        private String type;

        @Override
        public String toString() {
            return "CanonicalBean{" +
                    "\nhref='" + href + '\'' +
                    "\n, type='" + type + '\'' +
                    "\n}";
        }
    }

    public static class CategoriesBean {
        /**
         * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/Tech
         * label : Tech
         */

        @SerializedName("id")
        private String id;
        @SerializedName("label")
        private String label;

        @Override
        public String toString() {
            return "CategoriesBean{" +
                    "\nid='" + id + '\'' +
                    "\n, label='" + label + '\'' +
                    "\n}";
        }
    }

    public static class TagsBean {
        /**
         * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/global.saved
         * label : Microsoft
         */

        @SerializedName("id")
        private String id;
        @SerializedName("label")
        private String label;

        @Override
        public String toString() {
            return "TagsBean{" +
                    "\nid='" + id + '\'' +
                    "\n, label='" + label + '\'' +
                    "\n}";
        }
    }

    public static class AlternateBean {
        /**
         * href : http://feedproxy.google.com/~r/Techcrunch/~3/iEm1aA_M_dw/
         * type : text/html
         */

        @SerializedName("href")
        private String href;
        @SerializedName("type")
        private String type;

        @Override
        public String toString() {
            return "AlternateBean{" +
                    "\nhref='" + href + '\'' +
                    "\n, type='" + type + '\'' +
                    "\n}";
        }
    }

    public static class ThumbnailBean {
        /**
         * url : https://tctechcrunch2011.files.wordpress.com/2016/01/microsoft-internet-explorer-10.png
         */

        @SerializedName("url")
        private String url;

        @Override
        public String toString() {
            return "ThumbnailBean{" +
                    "\nurl='" + url + '\'' +
                    "\n}";
        }
    }

    @Override
    public String toString() {
        return "FeedlyEntry{" +
                "\nupdated=" + updated +
                "\n, summary=" + summary +
                "\n, content=" + content +
                "\n, id='" + id + '\'' +
                "\n, unread=" + unread +
                "\n, title='" + title + '\'' +
                "\n, origin=" + origin +
                "\n, author='" + author + '\'' +
                "\n, originId='" + originId + '\'' +
                "\n, visual=" + visual +
                "\n, published=" + published +
                "\n, enclosure=" + enclosure +
                "\n, canonical=" + canonical +
                "\n, categories=" + categories +
                "\n, tags=" + tags +
                "\n, alternate=" + alternate +
                "\n, thumbnail=" + thumbnail +
                "\n, keywords=" + keywords +
                "\n}";
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPublished() {
        if (published == 0) {
            if (updated == 0) {
                return 0;
            }
            return updated;
        }
        return published;
    }

    public String getContent() {
        if (content != null && !TextUtil.isEmpty(content.content)) {
            return content.content;
        } else if (summary != null && !TextUtil.isEmpty(summary.content)) {
            return summary.content;
        }
        return "";
    }

    public String getSummary() {
        return Html.fromHtml(Html.fromHtml(getContent()).toString()).toString()
                .replaceAll("￼", " ");
    }

    public String getSubscriptionId() {
        if (origin != null) {
            return origin.streamId;
        }
        return null;
    }

    public String getUrl() {
        if (alternate == null || alternate.size() <= 0) {
            return null;
        }
        return alternate.get(0).href;
    }

    public String getSubscriptionTitle() {
        if (origin != null) {
            return origin.title;
        }
        return "";
    }

    public boolean isUnread() {
        return unread;
    }
}
