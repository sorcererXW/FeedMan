package com.sorcererxw.feedman.network.api.feedly.model;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */
public class FeedlyReadMarker {
    private static final String ACTION_READ = "markAsRead";
    private static final String ACTION_STARRED = "markAsSaved";
    private static final String ACTION_UNREAD = "keepUnread";
    private static final String ACTION_UNSTARRED = "markAsUnsaved";

    private final String mAction;
    private final String[] mEntryIds;
    private final String mType = "entries";

    private FeedlyReadMarker(String action, String[] entryIds) {
        mAction = action;
        mEntryIds = entryIds;
    }

    public static FeedlyReadMarker read(String[] entryIds) {
        return new FeedlyReadMarker(ACTION_READ, entryIds);
    }

    public static FeedlyReadMarker unread(String[] entryIds) {
        return new FeedlyReadMarker(ACTION_UNREAD, entryIds);
    }

    public static FeedlyReadMarker starred(String entryId) {
        return new FeedlyReadMarker(ACTION_STARRED, new String[]{entryId});
    }

    public static FeedlyReadMarker unstarred(String entryId) {
        return new FeedlyReadMarker(ACTION_UNSTARRED, new String[]{entryId});
    }

    public String getAction() {
        return mAction;
    }

    public String[] getEntryIds() {
        return mEntryIds;
    }
}
