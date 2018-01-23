package com.innso.mobile.api.config;


import android.content.Context;

import com.innso.mobile.R;
import com.innso.mobile.managers.preferences.PrefsManager;

public class ApiConfig {

    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "authorization";
    public static final String PARAM_AUTHORIZATION = "auth";

    public boolean DEBUG = false;

    private PrefsManager prefsManager;
    private Context context;

    public ApiConfig(Context context, PrefsManager prefsManager) {
        this.prefsManager = prefsManager;
        this.context = context;
    }

    public String getFirebaseUrl() {
        return context.getString(R.string.firebase_database);
    }

    public String getFuntionsUrlBase() {
        return "https://us-central1-innso-mobile.cloudfunctions.net";
    }
}
