package com.innso.mobile.ui.viewModels;

import android.view.View;

import com.innso.mobile.api.controllers.UserControllerApi;
import com.innso.mobile.api.models.users.UserRequest;
import com.innso.mobile.ui.activities.CreateUserActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public class UsersListViewModel extends BaseViewModel {

    @Inject
    UserControllerApi userController;

    private BehaviorSubject<List<UserRequest>> users = BehaviorSubject.create();

    public UsersListViewModel() {
        getComponent().inject(this);
        updateUsers();
    }

    public void updateUsers() {
        userController.getUsers().subscribe(users::onNext, this::showServiceError);
    }

    public void onNewUserClick(View view) {
        startActivityEvent.onNext(CreateUserActivity.class);
    }

    public Observable<List<UserRequest>> allUsers() {
        return users.observeOn(AndroidSchedulers.mainThread());
    }
}
