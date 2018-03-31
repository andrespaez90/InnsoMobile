package com.innso.mobile.api.services;

import com.innso.mobile.api.models.cutomers.Customer;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CustomerApi {

    @POST("customers.json")
    Completable addCustomer(@Body Customer customer);

    @GET("customers.json")
    Single<Map<String, Customer>> getCustomers();

}
