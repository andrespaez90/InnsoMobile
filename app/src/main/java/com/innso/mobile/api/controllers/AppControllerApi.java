package com.innso.mobile.api.controllers;


import com.innso.mobile.api.models.app.GeneralInformation;
import com.innso.mobile.api.services.ApplicationApi;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppControllerApi {

    private ApplicationApi applicationApi;

    public AppControllerApi(ApplicationApi applicationFirebaseApi) {
        this.applicationApi = applicationFirebaseApi;
    }

    public Single<GeneralInformation> checkVersion(){
        return applicationApi.getAppVersion().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
