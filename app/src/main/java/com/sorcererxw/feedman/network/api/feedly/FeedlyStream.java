package com.sorcererxw.feedman.network.api.feedly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedlyStream {

    /**
     * direction : ltr
     * title : The Verge -  All Posts
     * id : feed/http => //www.theverge.com/rss/full.xml
     * continuation : gRtwnDeqCDpZ42bXE9Sp7dNhm4R6NsipqFVbXn2XpDA=_13fb9d6f274:2ac9c5:f5718180
     * updated : 1367539068016
     * items : [{"engagement":15,"published":1367539068016,"crawled":1367539068016,"title":"NBC's reviled sci-fi drama 'Heroes' may get a second lease on life as Xbox Live exclusive","author":"Nathan Ingraham","id":"gRtwnDeqCDpZ42bXE9Sp7dNhm4R6NsipqFVbXn2XpDA=_13fb9d6f274:2ac9c5:f5718180","content":{"direction":"ltr","content":"..."},"updated":1367539068016,"unread":true,"tags":[{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/inspiration","label":"inspiration"}],"origin":{"title":"The Verge -  All Posts","htmlUrl":"http://www.theverge.com/","streamId":"feed/http://www.theverge.com/rss/full.xml"},"alternate":[{"href":"http://www.theverge.com/2013/4/17/4236096/nbc-heroes-may-get-a-second-lease-on-life-on-xbox-live","type":"text/html"}],"categories":[{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/tech","label":"tech"}]},{"engagement":39,"published":1367539068016,"crawled":1367539068016,"title":"Senate rejects bipartisan gun control measure for background checks despite broad public support","author":"T.C. Sottek","id":"gRtwnDeqCDpZ42bXE9Sp7dNhm4R6NsipqFVbXn2XpDA=_13fb9d6f274:2ac9c5:f5718182","content":{"direction":"ltr","content":"...html content..."},"updated":1367539068016,"unread":true,"tags":[{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/tag/inspiration","label":"inspiration"}],"origin":{"title":"The Verge -  All Posts","htmlUrl":"http://www.theverge.com/","streamId":"feed/http://www.theverge.com/rss/full.xml"},"alternate":[{"href":"http://www.theverge.com/2013/4/17/4236136/senate-rejects-gun-control-amendment","type":"text/html"}],"categories":[{"id":"user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/tech","label":"tech"}]}]
     */

    @SerializedName("direction")
    private String mDirection;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("id")
    private String mId;
    @SerializedName("continuation")
    private String mContinuation;
    @SerializedName("updated")
    private long mUpdated;
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

    @SerializedName("items")
    private List<FeedlyEntry> mItems;

    public String getDirection() {
        return mDirection;
    }

    public void setDirection(String direction) {
        mDirection = direction;
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

    public String getContinuation() {
        return mContinuation;
    }

    public void setContinuation(String continuation) {
        mContinuation = continuation;
    }

    public long getUpdated() {
        return mUpdated;
    }

    public void setUpdated(long updated) {
        mUpdated = updated;
    }

    public List<FeedlyEntry> getItems() {
        return mItems;
    }

    public void setItems(List<FeedlyEntry> items) {
        mItems = items;
    }

}
