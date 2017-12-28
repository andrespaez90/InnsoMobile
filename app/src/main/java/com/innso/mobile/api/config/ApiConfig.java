package com.innso.mobile.api.config;


import com.innso.mobile.managers.preferences.PrefsManager;

public class ApiConfig {

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "authorization";

    public boolean DEBUG = true;

    private PrefsManager prefsManager;

    public ApiConfig(PrefsManager prefsManager) {
        this.prefsManager = prefsManager;
    }

    public String getFirebaseUrl() {
        return "https://innso-mobile.firebaseio.com";
    }

    public String getFuntionsUrlBase() {
        return "https://us-central1-innso-mobile.cloudfunctions.net";
    }
}
