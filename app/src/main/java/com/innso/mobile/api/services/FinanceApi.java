package com.innso.mobile.api.services;

import com.innso.mobile.api.models.finance.BillModel;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FinanceApi {

    @POST("/bills/{date}.json")
    Completable addBill(@Path("date") String date, @Body BillModel customer);

    @GET("/bills/{year}.json")
    Single<Response<Map<String, Map<String, BillModel>>>> getBills(@Path("year") String year);

}
