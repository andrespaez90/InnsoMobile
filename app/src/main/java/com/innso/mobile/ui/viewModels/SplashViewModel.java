package com.innso.mobile.ui.viewModels;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.innso.mobile.BuildConfig;
import com.innso.mobile.api.controllers.AppControllerApi;
import com.innso.mobile.api.models.app.GeneralInformation;
import com.innso.mobile.ui.activities.LoginActivity;
import com.innso.mobile.ui.activities.MainActivity;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class SplashViewModel extends BaseViewModel {

    private PublishSubject<Boolean> updateVersion = PublishSubject.create();

    @Inject
    AppControllerApi appControllerApi;

    @Inject
    FirebaseAuth firebaseAuth;

    public SplashViewModel() {
        getComponent().inject(this);
    }


    public void validateAuthentication() {
        appControllerApi.checkVersion()
                .subscribe(this::validateVersion, this::showServiceError);
    }

    private void validateVersion(GeneralInformation generalInformation) {
        if (BuildConfig.VERSION_CODE >= generalInformation.getBuildVersion()) {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null && !currentUser.isAnonymous()) {
                startActivityEvent.onNext(MainActivity.class);
            } else {
                startActivityEvent.onNext(LoginActivity.class);
            }
        } else {
            updateVersion.onNext(true);
        }
    }

    public Observable<Boolean> updateVersionEvent() {
        return updateVersion.observeOn(AndroidSchedulers.mainThread());
    }

}
