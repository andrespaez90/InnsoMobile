package com.innso.mobile.api.services;

import com.innso.mobile.api.models.app.GeneralInformation;
import com.innso.mobile.api.models.cutomers.Customer;

import io.reactivex.Single;
import retrofit2.http.POST;

public interface CustomerApi {

    @POST("customers.json")
    Single<GeneralInformation> addCustomer(Customer customer);

}
