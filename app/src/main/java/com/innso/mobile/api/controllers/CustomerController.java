package com.innso.mobile.api.controllers;


import com.innso.mobile.api.models.cutomers.Customer;
import com.innso.mobile.api.services.CustomerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CustomerController {

    private CustomerApi customerApi;


    public CustomerController(CustomerApi customerApi) {
        this.customerApi = customerApi;
    }

    public Completable addCustomer(String id, String name, String address, String phone) {

        Customer customer = new Customer().setId(id).setName(name).setAddress(address).setPhone(phone);

        return customerApi.addCustomer(customer).subscribeOn(Schedulers.io());
    }

    public Single<ArrayList<Customer>> getCustomers() {
        return customerApi.getCustomers()
                .map(it -> new ArrayList<>(it.values()))
                .subscribeOn(Schedulers.io());
    }

}
