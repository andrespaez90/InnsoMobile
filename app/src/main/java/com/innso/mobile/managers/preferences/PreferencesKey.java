package com.innso.mobile.managers.preferences;

import java.security.InvalidParameterException;

import androidx.annotation.NonNull;

public class PreferencesKey {

    private String groupName;
    private String keyValue;
    private Object defaultValue;

    public PreferencesKey(String key, @NonNull Object value) {
        this(FilePreference.DefaultPreference, key, value);
    }

    public PreferencesKey(String filePreference, String key, @NonNull Object value) {
        if (key == null) {
            throw new InvalidParameterException("key can't be null");
        }
        keyValue = key;
        defaultValue = value;
        groupName = filePreference;
        ManagerPreferenceKey.addPreferenceKey(this);
    }

    protected String getKey() {
        return keyValue;
    }

    protected Object getDefaultValue() {
        return defaultValue;
    }

    protected String getPreferenceGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return keyValue;
    }

}
