package com.innso.mobile.di.modules;


import com.innso.mobile.api.controllers.CustomerController;
import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.controllers.UserControllerApi;
import com.innso.mobile.api.services.CustomerApi;
import com.innso.mobile.api.services.FinanceServices;
import com.innso.mobile.api.services.UserServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    @Provides
    @Singleton
    UserControllerApi userApi(UserServices userApi) {
        return new UserControllerApi(userApi);
    }

    @Provides
    @Singleton
    CustomerController customerController(CustomerApi customerApi) {
        return new CustomerController(customerApi);
    }

    @Provides
    @Singleton
    FinanceController financeController(FinanceServices financeApi) {
        return new FinanceController(financeApi);
    }

}
