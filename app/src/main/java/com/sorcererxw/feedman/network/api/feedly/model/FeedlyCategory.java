package com.sorcererxw.feedman.network.api.feedly.model;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */
public class FeedlyCategory {

    /**
     * description : Best tech websites
     * label : tech
     * id : user/c805fcbf-3acf-4302-a97e-d82f9d7c897f/category/tech
     */

    @SerializedName("description") private String mDescription;
    @SerializedName("label") private String mLabel;
    @SerializedName("id") private String mId;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
