package com.innso.mobile.viewModels.lifecycle

import androidx.lifecycle.MutableLiveData

class ActiveMutableLiveData<T> : MutableLiveData<T>() {

    override fun postValue(value: T) {
        if (hasActiveObservers()) super.postValue(value)
    }

    override fun setValue(value: T) {
        if (hasActiveObservers()) super.setValue(value)
    }
}