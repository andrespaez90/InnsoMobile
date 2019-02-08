package com.innso.mobile.viewModels.company


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.innso.mobile.viewModels.AndroidViewModel
import javax.inject.Inject

class ProfileViewModel : AndroidViewModel() {

    val userInformation: MutableLiveData<String> = MutableLiveData()

    private val closeSession: MutableLiveData<Unit> = MutableLiveData()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    init {
        getComponent().inject(this)
        initUserInfo()
    }

    fun initUserInfo() {
        firebaseAuth.currentUser?.let {
            userInformation.value = it.displayName + "\n" + it.email
        }
    }

    fun closeSession(view: View) {
        firebaseAuth.signOut()
        closeSession.postValue(Unit)
    }

    fun closeSession(): LiveData<Unit> = closeSession

}
