package com.innso.mobile.api.controllers;


import com.innso.mobile.api.models.users.UserRequest;
import com.innso.mobile.api.services.UserApi;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserControllerApi {

    private UserApi userApi;

    public UserControllerApi(UserApi userApi) {
        this.userApi = userApi;
    }

    public Single<List<UserRequest>> getUsers() {
        return userApi.getUsers()
                .subscribeOn(Schedulers.io());
    }

    public Completable createUser(String name, String email, String phone) {
        UserRequest newUser = new UserRequest(name, email, phone);
        return userApi.createUser(newUser)
                .subscribeOn(Schedulers.io());
    }


}
