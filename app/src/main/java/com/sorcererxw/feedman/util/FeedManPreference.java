package com.sorcererxw.feedman.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;

import com.socks.library.KLog;

import java.util.Date;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/12
 */
public class FeedManPreference<T> {
    private final T mDefaultValue;
    private String mKey;
    private SharedPreferences mPreferences;

    public FeedManPreference(SharedPreferences sharedPreferences, String key,
                             @NonNull T defaultValue) {
        mDefaultValue = defaultValue;
        mKey = key;
        mPreferences = sharedPreferences;
    }

    public T getValue() {
        if (mDefaultValue instanceof String) {
            return (T) mPreferences.getString(mKey, (String) mDefaultValue);
        } else if (mDefaultValue instanceof Integer) {
            return (T) Integer.valueOf(mPreferences.getInt(mKey, (Integer) mDefaultValue));
        } else if (mDefaultValue instanceof Float) {
            return (T) Float.valueOf(mPreferences.getFloat(mKey, (Float) mDefaultValue));
        } else if (mDefaultValue instanceof Boolean) {
            return (T) Boolean.valueOf(mPreferences.getBoolean(mKey, (Boolean) mDefaultValue));
        } else if (mDefaultValue instanceof Long) {
            return (T) Long.valueOf(mPreferences.getLong(mKey, (Long) mDefaultValue));
        } else if (mDefaultValue instanceof Date) {
            Long time = mPreferences.getLong(mKey, ((Date) mDefaultValue).getTime());
            return (T) new Date(time);
        } else {
            throw new IllegalArgumentException(
                    "Preference type not implemented " + mDefaultValue.getClass());
        }
    }

    public void setValue(T value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        if (value instanceof String) {
            editor.putString(mKey, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(mKey, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(mKey, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(mKey, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(mKey, (Long) value);
        } else if (value instanceof Date) {
            editor.putLong(mKey, ((Date) value).getTime());
        } else {
            throw new IllegalArgumentException(
                    "Preference type not implemented " + value.getClass());
        }
        editor.apply();
    }

    public boolean isContain() {
        return mPreferences.contains(mKey);
    }

    public void remove() {
        mPreferences.edit().remove(mKey).apply();
    }
}
