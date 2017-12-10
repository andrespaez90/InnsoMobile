package com.innso.mobile.ui.viewModels;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.innso.mobile.BuildConfig;
import com.innso.mobile.api.controllers.AppControllerApi;
import com.innso.mobile.api.models.app.GeneralInformation;
import com.innso.mobile.ui.activities.CreateUserActivity;
import com.innso.mobile.ui.activities.LoginActivity;
import com.innso.mobile.ui.activities.MainActivity;

import javax.inject.Inject;

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
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener(this::anonymosSigIn);
        } else {
            checkAppVersion(currentUser);
        }
    }

    private void checkAppVersion(@NonNull FirebaseUser currentUser) {
        currentUser.getToken(true)
                .addOnCompleteListener(this::validateSession);
    }

    private void validateSession(Task<GetTokenResult> result){
        if(result.isSuccessful()){
            checkVersion(result.getResult().getToken());
        } else {
            showSnackBarError("Verifica tu conexion a internet");
        }
    }

    private void anonymosSigIn(@NonNull Task<AuthResult> resultTask) {
        if (resultTask.isSuccessful()) {
            checkAppVersion(resultTask.getResult().getUser());
        } else {
            showSnackBarError("Verifica tu conexion a internet");
        }
    }

    private void checkVersion(String token) {
        appControllerApi.checkVersion(token)
                .subscribe(this::validateVersion, this::showServiceError);
    }

    private void validateVersion(GeneralInformation generalInformation) {
        if (BuildConfig.VERSION_CODE >= generalInformation.getBuildVersion()) {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null && currentUser.isAnonymous()) {
                startActivityEvent.onNext(LoginActivity.class);
            } else {
                startActivityEvent.onNext(MainActivity.class);
            }
        } else {
            updateVersion.onNext(true);
        }
    }

}
