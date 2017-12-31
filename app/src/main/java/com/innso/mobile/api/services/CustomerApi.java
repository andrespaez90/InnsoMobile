package com.innso.mobile.api.services;

import com.innso.mobile.api.models.cutomers.Customer;

import io.reactivex.Completable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CustomerApi {

    @POST("customers.json")
    Completable addCustomer(@Body Customer customer);

}
