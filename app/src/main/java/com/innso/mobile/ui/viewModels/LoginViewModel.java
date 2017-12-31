package com.innso.mobile.ui.viewModels;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.innso.mobile.R;
import com.innso.mobile.managers.preferences.InnsoPreferences;
import com.innso.mobile.managers.preferences.PreferencesKey;
import com.innso.mobile.managers.preferences.PrefsManager;
import com.innso.mobile.ui.activities.MainActivity;
import com.innso.mobile.utils.StringUtils;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {

    public ObservableField<String> email = new ObservableField<>("");

    public ObservableField<String> password = new ObservableField<>("");

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    PrefsManager prefsManager;

    public LoginViewModel() {
        getComponent().inject(this);
    }

    public void onLogInClick(View view) {
        if (!TextUtils.isEmpty(password.get()) && StringUtils.isValidEmail(email.get())) {
            firebaseAuth.signInWithEmailAndPassword(email.get(), password.get())
                    .addOnCompleteListener(this::validateLogin);
        } else {
            showSnackBarError(R.string.error_empty_fields);
        }
    }

    private void validateLogin(Task<AuthResult> authResultTask) {
        if (authResultTask.isSuccessful()) {
            firebaseAuth.getCurrentUser().getToken(true)
                    .addOnCompleteListener(this::validateToken);

        } else {
            Exception exception = authResultTask.getException();
            if (exception != null) {
                showSnackBarError(exception.getMessage());
            }
        }
    }

    private void validateToken(Task<GetTokenResult> tokenResultTask) {
        if (tokenResultTask.isSuccessful()) {
            prefsManager.set(InnsoPreferences.ACCESS_TOKEN, tokenResultTask.getResult().getToken());
            startActivityEvent.onNext(MainActivity.class);
        }
    }

    public void onRecoverPasswordClick(View view) {
        if (StringUtils.isValidEmail(email.get())) {
            firebaseAuth.verifyPasswordResetCode(email.get());
        } else {
            showSnackBarError("Dirección de correo invalido");
        }
    }
}
