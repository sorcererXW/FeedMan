package com.sorcererxw.feedman.network.api.feedly;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/15
 */
public class FeedlyEntryContent {
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
        return "FeedlyEntryContent{" +
                "\nmDirection='" + mDirection + '\'' +
                "\n, mContent='" + mContent + '\'' +
                "\n}";
    }
}
