package com.innso.mobile.viewModels.onBoarding

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.innso.mobile.R
import com.innso.mobile.extensions.biLet
import com.innso.mobile.managers.preferences.InnsoPreferences
import com.innso.mobile.managers.preferences.PrefsManager
import com.innso.mobile.ui.activities.MainActivity
import com.innso.mobile.utils.StringUtils
import com.innso.mobile.viewModels.AndroidViewModel
import com.innso.mobile.viewModels.models.StartActivityModel
import javax.inject.Inject

class LoginViewModel : AndroidViewModel() {

    var email = MutableLiveData("")

    var password = MutableLiveData("")

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var prefsManager: PrefsManager

    init {
        getComponent().inject(this)
    }

    fun onLogInClick(view: View) {
        if (!TextUtils.isEmpty(password.value) && StringUtils.isValidEmail(email.value)) {
            Pair(email.value, password.value).biLet { email, pass ->
                showLoading()
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { this.validateLogin(it) }
            }
        } else {
            showSnackBarError(R.string.error_empty_fields)
        }
    }

    private fun validateLogin(authResultTask: Task<AuthResult>) {
        hideLoading()
        if (authResultTask.isSuccessful) {
            firebaseAuth.currentUser!!.getToken(true)
                    .addOnCompleteListener { this.validateToken(it) }

        } else {
            val exception = authResultTask.exception
            exception?.message?.let { showSnackBarError(it) }
        }
    }

    private fun validateToken(tokenResultTask: Task<GetTokenResult>) {
        if (tokenResultTask.isSuccessful) {
            prefsManager.set(InnsoPreferences.ACCESS_TOKEN, tokenResultTask.result.token)
            startActivity.postValue(StartActivityModel(MainActivity::class.java))
        }
    }

    fun onRecoverPasswordClick(view: View) {
        if (StringUtils.isValidEmail(email.value)) {
            firebaseAuth.verifyPasswordResetCode(email.value!!)
        } else {
            showSnackBarError("Dirección de correo invalido")
        }
    }
}
