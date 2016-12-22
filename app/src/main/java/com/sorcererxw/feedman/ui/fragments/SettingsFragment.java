package com.sorcererxw.feedman.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.sorcererxw.feedman.FeedManApp;
import com.sorcererxw.feedman.R;
import com.sorcererxw.feedman.database.Db;
import com.sorcererxw.feedman.util.Prefs;


/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/20
 */

public class SettingsFragment extends PreferenceFragment {

    private Prefs mPrefs;

    private Db mDb;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        mPrefs = FeedManApp.prefs(mContext);
        mDb = FeedManApp.getDb(mContext);
        setupReading();
        setupSynchronization();
        setupAccount();
    }

    private void setupReading() {
        SwitchPreference autoReadabilityPreference =
                (SwitchPreference) findPreference("KEY_READING_AUTO_READABILITY");
        autoReadabilityPreference.setChecked(mPrefs.autoReadability().getValue());
        autoReadabilityPreference
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        mPrefs.autoReadability().setValue((Boolean) newValue);
                        return true;
                    }
                });
    }

    private void setupSynchronization() {
        SwitchPreference autoSyncPreference =
                (SwitchPreference) findPreference("KEY_SYNCHRONIZATION_AUTO_DOWNLOAD");
        autoSyncPreference.setChecked(mPrefs.getAutoSyncEnable().getValue());
        autoSyncPreference.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        if ((Boolean) newValue) {
                            FeedManApp.getScheduleManager(mContext).startSyncIfNeed();
                        } else {
                            FeedManApp.getScheduleManager(mContext).shutdown();
                        }
                        mPrefs.getAutoSyncEnable().setValue((Boolean) newValue);
                        return true;
                    }
                });

        SwitchPreference autoSyncWifiPreference =
                (SwitchPreference) findPreference("KEY_SYNCHRONIZATION_ONLY_WIFI");
        autoSyncWifiPreference.setChecked(mPrefs.autoSyncOnlyWifi().getValue());
        autoSyncWifiPreference.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        mPrefs.autoSyncOnlyWifi().setValue((Boolean) newValue);
                        return true;
                    }
                });

    }

    private void setupAccount() {
        findPreference("KEY_ACCOUNT_LOGOUT").setOnPreferenceClickListener(
                new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        mPrefs.getFeedlyAccessToken().remove();
                        mPrefs.getFeedlyAccountId().remove();
                        mPrefs.getFeedlyRefreshToken().remove();
                        mDb.entries().clearEntries();
                        mDb.subscriptions().clearSubscriptions();
                        FeedManApp.restartApp(mContext);
                        return true;
                    }
                });
    }
}
