package com.sorcererxw.feedman.network.api.feedly;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/15
 */
public class FeedlyEntryOrigin {
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


    public FeedlyEntryOrigin() {
    }

    @Override
    public String toString() {
        return "FeedlyEntryOrigin{" +
                "\nmTitle='" + mTitle + '\'' +
                "\n, mHtmlUrl='" + mHtmlUrl + '\'' +
                "\n, mStreamId='" + mStreamId + '\'' +
                "\n}";
    }
}
