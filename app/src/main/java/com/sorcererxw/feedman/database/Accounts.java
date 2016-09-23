package com.sorcererxw.feedman.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteQuery;

import com.sorcererxw.feedman.database.tables.AccountTable;
import com.sorcererxw.feedman.models.Account;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

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

    public void updateAccount(Account account) {
        mDatabase.update(AccountTable.TABLE_NAME,
                account.toContentValues(),
                "id = ?",
                account.id());
    }

    public Observable<List<Account>> getAccounts() {
        return mDatabase.createQuery(AccountTable.TABLE_NAME,
                "SELECT a.* ,(SELECT COUNT(id) FROM subscription s WHERE s.account_id = a.id) AS subscription_count , (SELECT COUNT(id) FROM entry e WHERE e.account_id = a.id AND e.unread = 1) AS unread_count FROM account AS a",
                new String[0])
                .flatMap(new Func1<SqlBrite.Query, Observable<List<Account>>>() {
                    @Override public Observable<List<Account>> call(SqlBrite.Query query) {
                        List<Account> list = new ArrayList<>();
                        Cursor cursor = query.run();
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                list.add(Account.from(cursor));
                            }
                            cursor.close();
                        }
                        return Observable.just(list);
                    }
                });
    }

    public List<Account> getAccountsImmediate() {
        Cursor cursor = mDatabase.query("SELECT * FROM account");
        List<Account> list = new ArrayList<>();
        if(cursor!=null){
            while (cursor.moveToNext()){
                list.add(Account.from(cursor));
            }
            cursor.close();
        }
        return list;
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
