package com.innso.mobile.ui.viewModels;


import android.text.TextUtils;
import android.view.View;

import com.innso.mobile.R;
import com.innso.mobile.api.controllers.UserControllerApi;
import com.innso.mobile.utils.StringUtils;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class NewUserViewModel extends BaseViewModel {

    public ObservableField<String> name = new ObservableField<>("");

    public ObservableField<String> email = new ObservableField<>("");

    public ObservableField<String> phone = new ObservableField<>("");

    private PublishSubject<String> userCreated = PublishSubject.create();

    @Inject
    UserControllerApi userControllerApi;

    public NewUserViewModel() {
        getComponent().inject(this);
    }

    public void onFinalizeClick(View view) {

        if (!TextUtils.isEmpty(name.get()) && !TextUtils.isEmpty(email.get()) && !TextUtils.isEmpty(phone.get())) {

            if (StringUtils.isValidEmail(email.get())) {

                userControllerApi.createUser(name.get(), email.get(), phone.get())
                        .subscribe(() -> userCreated.onNext(name.get()), this::showServiceError);

            } else {
                showSnackBarError(R.string.error_incorrect_fields);
            }
        } else {
            showSnackBarError(R.string.error_empty_fields);
        }
    }

    public Observable<String> userCreated() {
        return userCreated.observeOn(AndroidSchedulers.mainThread());
    }


}
