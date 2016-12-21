package com.sorcererxw.feedman.feedly;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/18
 */

public class FeedlyAccount {
    private FeedlyToken mFeedlyToken;
    private String  mId;


    public FeedlyToken getFeedlyToken() {
        return mFeedlyToken;
    }

    public void setFeedlyToken(FeedlyToken feedlyToken) {
        mFeedlyToken = feedlyToken;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
