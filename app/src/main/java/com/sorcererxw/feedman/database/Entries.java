package com.sorcererxw.feedman.database;

import com.sorcererxw.feedman.database.tables.EntryTable;
import com.sorcererxw.feedman.network.api.feedly.FeedlyEntry;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/18
 */

public class Entries {
    private BriteDatabase mDatabase;

    public Entries(BriteDatabase database) {
        mDatabase = database;
    }

    public void addEntries(List<FeedlyEntry> entries) {
        BriteDatabase.Transaction transaction = mDatabase.newTransaction();
        for (FeedlyEntry entry:entries) {
//            mDatabase.insert(EntryTable.TABLE_NAME, entry.)
        }
    }
}
