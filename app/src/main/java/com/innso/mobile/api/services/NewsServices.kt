package com.innso.mobile.api.services

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {

    @GET("top-headlines")
    fun getTopLines(@Query("county") countryCode: String )
}