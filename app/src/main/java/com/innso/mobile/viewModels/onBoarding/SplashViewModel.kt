package com.innso.mobile.viewModels.onBoarding

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.innso.mobile.BuildConfig
import com.innso.mobile.api.controllers.AppControllerApi
import com.innso.mobile.api.models.app.GeneralInformation
import com.innso.mobile.ui.activities.HomeActivity
import com.innso.mobile.ui.activities.MainActivity
import com.innso.mobile.viewModels.AndroidViewModel
import com.innso.mobile.viewModels.models.FinishActivityModel
import com.innso.mobile.viewModels.models.StartActivityModel
import javax.inject.Inject

class SplashViewModel : AndroidViewModel() {

    private val updateVersion = MutableLiveData<Boolean>()

    @Inject
    lateinit var appControllerApi: AppControllerApi

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    init {
        getComponent().inject(this)
    }

    fun validateAuthentication() {
        disposables.add(appControllerApi.checkVersion()
                .doOnSubscribe { showLoading() }
                .doFinally { hideLoading() }
                .subscribe({ this.validateVersion(it) }, { this.showServiceError(it) }))
    }

    private fun validateVersion(generalInformation: GeneralInformation) {
        if (BuildConfig.VERSION_CODE >= generalInformation.buildVersion) {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null && !currentUser.isAnonymous) {
                startActivity.postValue(StartActivityModel(MainActivity::class.java))
            } else {
                startActivity.postValue(StartActivityModel(HomeActivity::class.java))
            }
            closeView.postValue(FinishActivityModel(Activity.RESULT_OK))
        } else {
            updateVersion.postValue(true)
        }
    }

    fun updateVersionEvent(): LiveData<Boolean> = updateVersion

}
