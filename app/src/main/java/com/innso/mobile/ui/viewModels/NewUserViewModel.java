package com.innso.mobile.ui.viewModels;


import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.innso.mobile.R;
import com.innso.mobile.utils.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class NewUserViewModel extends BaseViewModel {

    private final String DEFAULT_PASSWORD = "123456";

    public ObservableField<String> name = new ObservableField<>("");

    public ObservableField<String> email = new ObservableField<>("");

    public ObservableField<String> phone = new ObservableField<>("");

    private PublishSubject<String> userCreated = PublishSubject.create();

    @Inject
    @Named("firebaseUserAdmin")
    FirebaseAuth firebaseUserAdmin;

    public NewUserViewModel() {
        getComponent().inject(this);
    }

    public void onFinaliceClick(View view) {

        if (!TextUtils.isEmpty(name.get()) && !TextUtils.isEmpty(email.get()) && !TextUtils.isEmpty(phone.get())) {

            if (StringUtils.isValidEmail(email.get()) && TextUtils.isDigitsOnly(phone.get())) {

                firebaseUserAdmin.createUserWithEmailAndPassword(email.get(), DEFAULT_PASSWORD)
                        .addOnCompleteListener(this::validateNewUser);

            } else {
                showSnackBarError(R.string.error_incorrect_fields);
            }
        } else {
            showSnackBarError(R.string.error_empty_fields);
        }
    }


    private void validateNewUser(Task<AuthResult> resultTask) {
        if (resultTask.isSuccessful()) {

            UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.get());

            resultTask.getResult().getUser()
                    .updateProfile(builder.build())
                    .addOnCompleteListener(this::userUpdated);
        } else {
            Exception exception = resultTask.getException();
            if(exception != null){
                showSnackBarError(exception.getMessage());
            }
        }
    }

    private void userUpdated(Task<Void> authResultTask){
        userCreated.onNext(name.get());
    }

    public Observable<String> userCreated() {
        return userCreated.observeOn(AndroidSchedulers.mainThread());
    }



}
