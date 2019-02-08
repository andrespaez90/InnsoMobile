package com.innso.mobile.di.modules;


import com.innso.mobile.api.controllers.CustomerController;
import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.controllers.UserControllerApi;
import com.innso.mobile.api.services.CustomerApi;
import com.innso.mobile.api.services.FinanceApi;
import com.innso.mobile.api.services.UserApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    @Provides
    @Singleton
    UserControllerApi userApi(UserApi userApi) {
        return new UserControllerApi(userApi);
    }

    @Provides
    @Singleton
    CustomerController customerController(CustomerApi customerApi) {
        return new CustomerController(customerApi);
    }

    @Provides
    @Singleton
    FinanceController financeController(FinanceApi financeApi) {
        return new FinanceController(financeApi);
    }

}
