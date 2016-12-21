package com.sorcererxw.feedman.feedly;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class FeedlyStream {
    @SerializedName("id")
    private String id;
    @SerializedName("continuation")
    private String continuation;
    @SerializedName("items")
    private List<FeedlyEntry> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContinuation() {
        return continuation;
    }

    public List<FeedlyEntry> getItems() {
        return items;
    }

}
