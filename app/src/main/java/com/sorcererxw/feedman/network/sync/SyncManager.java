package com.sorcererxw.feedman.network.sync;

import android.content.Context;

import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.database.DB;
import com.sorcererxw.feedman.models.Account;
import com.sorcererxw.feedman.util.FeedManPreference;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/16
 */
public abstract class SyncManager {
    private Account mAccount;
    private Context mContext;
    private DB mDB;
    private FeedManPreference<Date> mLastSyncPreference;

    public abstract Func1<List<String>, Observable<String[]>> markAsRead();

    public abstract Func1<String, Observable<String>> markAsStarred();

    public abstract Func1<List<String>, Observable<String[]>> markAsUnread();

    public abstract Func1<String, Observable<String>> markAsUnstarred();

    public abstract void refreshToken();

    public abstract Observable<Void> subscribe(String str);

    public abstract void sync();

    public abstract Func1<Subscription, Observable<Subscription>> unsubscribe();

    protected Account getAccount() {
        return mAccount;
    }

    protected Context getContext() {
        return mContext;
    }

    protected DB getDB() {
        return mDB;
    }

    protected FeedManPreference<Date> getLastSyncPreference() {
        return mLastSyncPreference;
    }

    public static SyncManager from(Context context, String accountId) {
        context = context.getApplicationContext();
        Account account = FeedManApp.getDB(context).getAccounts().getAccount(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        return from(context, account);
    }

    public static SyncManager from(Context context, Account account) {
        if (account == null) {
            throw new NullPointerException("Account cannot be null.");
        }
        context = context.getApplicationContext();
        // TODO: 2016/9/16 multi account type switch
        return new FeedlySyncManager(context, account);
    }

    protected SyncManager(Context context, Account account) {
        mContext = context;
        mAccount = account;
        mDB = FeedManApp.getDB(context);
        mLastSyncPreference = FeedManApp.getPrefs(context).getLastSync(account.id());
    }


}
