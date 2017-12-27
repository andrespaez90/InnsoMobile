package com.innso.mobile.di.components;


import com.google.firebase.auth.FirebaseAuth;
import com.innso.mobile.api.config.ApiConfig;
import com.innso.mobile.api.config.AuthenticatorService;
import com.innso.mobile.api.controllers.AppControllerApi;
import com.innso.mobile.api.controllers.UserControllerApi;
import com.innso.mobile.di.modules.ApiModule;
import com.innso.mobile.di.modules.AppModule;
import com.innso.mobile.di.modules.ControllerModule;
import com.innso.mobile.di.modules.FirebaseModule;
import com.innso.mobile.managers.preferences.PrefsManager;
import com.innso.mobile.providers.ResourceProvider;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class, FirebaseModule.class, ControllerModule.class})
public interface AppComponent {

    PrefsManager preferenceManager();

    ResourceProvider resourceProvider();

    /**
     * Apis
     **/

    ApiConfig getApiConfig();

    Retrofit retrofit();

    AuthenticatorService autheticaorService();

    /**
     * Firebase
     */

    FirebaseAuth firebaseAuth();

    /**
     * Controllers
     */

    AppControllerApi appControllerApi();

    UserControllerApi userControllerApi();

}
