package com.innso.mobile.api.services

import com.innso.mobile.api.models.news.TopLinesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {

    @GET("top-headlines")
    fun getTopLines(@Query("country") countryCode: String, @Query("page") page: Int? = null): Single<TopLinesResponse>
}