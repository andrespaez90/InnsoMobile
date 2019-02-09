package com.innso.mobile.api.services

import com.innso.mobile.api.models.users.UserRequest

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserServices {

    @GET("/users")
    fun getUsers(): Single<List<UserRequest>>

    @PUT("/users")
    fun updateUser(@Body customer: UserRequest): Single<UserRequest>

    @POST("/users")
    fun createUser(@Body customer: UserRequest): Completable

}
