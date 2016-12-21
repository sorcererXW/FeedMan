package com.sorcererxw.feedman.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.sorcererxw.feedman.R;


/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/12/20
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
