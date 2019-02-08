package com.innso.mobile.api.controllers


import com.innso.mobile.api.models.app.GeneralInformation
import com.innso.mobile.api.services.ApplicationApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppControllerApi @Inject constructor(private val applicationApi: ApplicationApi) {

    fun checkVersion(): Single<GeneralInformation> =
            applicationApi.appVersion.subscribeOn(Schedulers.io())


}
