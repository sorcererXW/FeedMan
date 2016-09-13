package com.sorcererxw.feedman.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteQuery;

import com.sorcererxw.feedman.database.tables.AccountTable;
import com.sorcererxw.feedman.models.Account;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/13
 */
public class Accounts {
    private static final String GET_ACCOUNT_QUERY =
            String.format("SELECT * FROM %s WHERE %s=?", AccountTable.TABLE_NAME, AccountTable.ID);

    private BriteDatabase mDatabase;

    Accounts(BriteDatabase database) {
        mDatabase = database;
    }

    public void addAccount(Account account) {
        mDatabase.insert(AccountTable.TABLE_NAME, account.toContentValues());
    }

    public Account getAccount(String accountId) {
        Cursor c = mDatabase.query(GET_ACCOUNT_QUERY, accountId);
        Account account = null;
        if (c != null) {
            if (c.moveToFirst()) {
                account = Account.from(c);
            }
            c.close();
        }
        return account;
    }

    public void removeAccount(String accountId) {
        mDatabase.delete(AccountTable.TABLE_NAME, "id = ?", accountId);
    }

}
