package com.innso.mobile.api.services;

import com.innso.mobile.api.models.finance.BillModel;

import io.reactivex.Completable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FinanceApi {

    @POST("/bills/{date}.json")
    Completable addBill(@Path("date") String date, @Body BillModel customer);

}
