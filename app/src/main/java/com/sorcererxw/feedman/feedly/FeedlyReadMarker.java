package com.sorcererxw.feedman.feedly;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/19
 */

public class FeedlyReadMarker {
    private static final String ACTION_READ = "markAsRead";
    private static final String ACTION_STARRED = "markAsSaved";
    private static final String ACTION_UNREAD = "keepUnread";
    private static final String ACTION_UNSTARRED = "markAsUnsaved";
    private final String action;
    private final String[] entryIds;
    private final String type = "entries";

    private FeedlyReadMarker(String action, String[] entryIds) {
        this.action = action;
        this.entryIds = entryIds;
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
        return action;
    }

    public String getType() {
        return type;
    }

    public String[] getEntryIds() {
        return entryIds;
    }
}
