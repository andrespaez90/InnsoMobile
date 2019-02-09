package com.innso.mobile.api.services

import com.innso.mobile.api.models.finance.BillModel
import com.innso.mobile.api.models.finance.ExpenseModel
import com.innso.mobile.api.models.finance.FinanceYearSummary

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FinanceServices {

    @POST("/bills/{date}.json")
    fun addBill(@Path("date") date: String, @Body customer: BillModel): Completable

    @POST("/expenses/{date}.json")
    fun addExpense(@Path("date") date: String, @Body customer: ExpenseModel): Completable

    @GET("/bills/{year}.json")
    fun getBills(@Path("year") year: String): Single<Response<Map<String, Map<String, BillModel>>>>

    @GET("/expenses/{year}.json")
    fun getExpenses(@Path("year") year: String): Single<Response<Map<String, Map<String, ExpenseModel>>>>

    @GET("/finance/{year}.json")
    fun getAccountSummary(@Path("year") year: String): Single<Response<FinanceYearSummary>>
}
