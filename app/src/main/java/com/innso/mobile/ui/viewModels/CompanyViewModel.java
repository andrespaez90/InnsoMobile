package com.innso.mobile.ui.viewModels;


import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.innso.mobile.ui.activities.CreateUserActivity;
import com.innso.mobile.ui.activities.UsersActivity;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class CompanyViewModel extends BaseViewModel {


    private PublishSubject<Boolean> closeSession = PublishSubject.create();

    @Inject
    FirebaseAuth firebaseAuth;

    public CompanyViewModel() {
        getComponent().inject(this);
    }

    public void addUser(View view) {
        startActivityEvent.onNext(UsersActivity.class);
    }

    public void addCustomer(View view) {
        startActivityEvent.onNext(CreateUserActivity.class);
    }

    public void closeSession(View view) {
        firebaseAuth.signOut();
        closeSession.onNext(true);
    }

    public Observable<Boolean> closeSession() {
        return closeSession.observeOn(AndroidSchedulers.mainThread());
    }

}
