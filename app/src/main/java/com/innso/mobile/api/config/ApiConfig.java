package com.innso.mobile.api.config;


import com.innso.mobile.managers.preferences.PrefsManager;

public class ApiConfig {

    public static final String BEARER = "Bearer ";

    public boolean DEBUG = true;

    private PrefsManager prefsManager;

    public ApiConfig(PrefsManager prefsManager) {
        this.prefsManager = prefsManager;
    }

    public String getFirebaseUrl() {
        return "https://innso-mobile.firebaseio.com";
    }
}
