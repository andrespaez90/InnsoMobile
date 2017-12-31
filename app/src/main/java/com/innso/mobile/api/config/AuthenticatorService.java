package com.innso.mobile.api.config;


import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.innso.mobile.managers.preferences.PrefsManager;
import com.innso.mobile.ui.activities.LoginActivity;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AuthenticatorService implements Authenticator {

    private Context context;

    private PrefsManager prefsManager;

    private FirebaseAuth firebaseAuth;

    public AuthenticatorService(Context context, PrefsManager prefsManager, FirebaseAuth firebaseAuth) {
        this.context = context;
        this.prefsManager = prefsManager;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        prefsManager.resetPreferences();

        firebaseAuth.signOut();

        Intent intent = new Intent(context, LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);

        return null;
    }
}
