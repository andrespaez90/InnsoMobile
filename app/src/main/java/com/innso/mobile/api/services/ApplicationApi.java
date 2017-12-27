package com.innso.mobile.api.services;


import com.innso.mobile.api.models.app.GeneralInformation;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApplicationApi {

    @GET("general_information.json")
    Single<GeneralInformation> getAppVersion();

}
