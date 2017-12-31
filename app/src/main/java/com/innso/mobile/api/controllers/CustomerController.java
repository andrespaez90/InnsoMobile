package com.innso.mobile.api.controllers;


import com.innso.mobile.api.models.cutomers.Customer;
import com.innso.mobile.api.services.CustomerApi;
import com.innso.mobile.managers.preferences.InnsoPreferences;
import com.innso.mobile.managers.preferences.PrefsManager;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class CustomerController {

    private CustomerApi customerApi;

    private PrefsManager prefsManager;

    public CustomerController(CustomerApi customerApi, PrefsManager prefsManager) {
        this.customerApi = customerApi;
        this.prefsManager = prefsManager;
    }

    public Completable addCustomer(String id, String name, String address, String phone) {

        Customer customer = new Customer().setId(id).setName(name).setAddress(address).setPhone(phone);

        return customerApi.addCustomer(customer).subscribeOn(Schedulers.io());
    }

}
