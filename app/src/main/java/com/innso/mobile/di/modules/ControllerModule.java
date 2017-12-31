package com.innso.mobile.di.modules;


import com.innso.mobile.api.controllers.AppControllerApi;
import com.innso.mobile.api.controllers.CustomerController;
import com.innso.mobile.api.controllers.UserControllerApi;
import com.innso.mobile.api.services.ApplicationApi;
import com.innso.mobile.api.services.CustomerApi;
import com.innso.mobile.api.services.UserApi;
import com.innso.mobile.managers.preferences.PrefsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    @Provides
    @Singleton
    AppControllerApi applicationApiController(ApplicationApi applicationFirebaseAp) {
        return new AppControllerApi(applicationFirebaseAp);
    }

    @Provides
    @Singleton
    UserControllerApi userApi(UserApi userApi) {
        return new UserControllerApi(userApi);
    }

    @Provides
    @Singleton
    CustomerController customerController(CustomerApi customerApi, PrefsManager prefsManager) {
        return new CustomerController(customerApi, prefsManager);
    }

}
