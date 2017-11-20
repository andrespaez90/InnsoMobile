package com.innso.mobile.api.config;


import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AuthenticatorService implements Authenticator {


    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        return null;
    }
}
