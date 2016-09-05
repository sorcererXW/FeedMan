package com.sorcererxw.feedman.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/4
 */
public class FeedlyStreamBean {

    /**
     * ids : ["gRtwnDeqCDpZ42bXE9Sp7dNhm4R6NsipqFVbXn2XpDA=_13fb9d6f274:2ac9c5:f5718180","9bVktswTBLT3zSr0Oy09Gz8mJYLymYp71eEVeQryp2U=_13fb9d1263d:2a8ef5:db3da1a7"]
     * continuation : 13fb9d1263d:2a8ef5:db3da1a7
     */

    @SerializedName("continuation")
    private String mContinuation;
    @SerializedName("ids")
    private List<String> mIds;

    public String getContinuation() {
        return mContinuation;
    }

    public void setContinuation(String continuation) {
        mContinuation = continuation;
    }

    public List<String> getIds() {
        return mIds;
    }

    public void setIds(List<String> ids) {
        mIds = ids;
    }
}
