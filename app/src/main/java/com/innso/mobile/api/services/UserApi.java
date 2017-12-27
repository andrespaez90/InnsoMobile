package com.innso.mobile.api.services;

import com.innso.mobile.api.models.users.UserRequest;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserApi {

    @GET("/users")
    Single<List<UserRequest>> getUsers();

    @PUT("/users")
    Single<UserRequest> updateUser(@Body UserRequest customer);

    @POST("/users")
    Completable createUser(@Body UserRequest customer);

}
