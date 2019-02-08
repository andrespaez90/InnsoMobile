package com.innso.mobile.viewModels

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.innso.mobile.InnsoApplication
import com.innso.mobile.di.components.DaggerViewModelComponent
import com.innso.mobile.di.components.ViewModelComponent
import com.innso.mobile.ui.dialogs.BasicDialog
import com.innso.mobile.ui.factories.SnackBarFactory
import com.innso.mobile.ui.models.events.SnackBarEvent
import com.innso.mobile.ui.viewModels.BaseViewModel
import com.innso.mobile.viewModels.lifecycle.ActiveMutableLiveData
import com.innso.mobile.viewModels.models.DialogParams
import com.innso.mobile.viewModels.models.FinishActivityModel
import com.innso.mobile.viewModels.models.StartActionModel
import com.innso.mobile.viewModels.models.StartActivityModel
import io.reactivex.disposables.CompositeDisposable

open class AndroidViewModel : ViewModel() {

    internal val disposables = CompositeDisposable()

    internal val loader = MutableLiveData<Boolean>()

    internal val closeView = MutableLiveData<FinishActivityModel>()

    internal val snackBar = MutableLiveData<SnackBarEvent>()

    internal val alertDialog = ActiveMutableLiveData<DialogParams>()

    internal val dialog = ActiveMutableLiveData<BasicDialog<*>>()

    internal val startActivity = ActiveMutableLiveData<StartActivityModel>()

    protected val startAction = ActiveMutableLiveData<StartActionModel>()

    private val children = ArrayList<BaseViewModel>()

    protected fun getComponent(): ViewModelComponent {
        return DaggerViewModelComponent.builder()
                .appComponent(getApplication().getAppComponent())
                .build()
    }

    private fun getApplication(): InnsoApplication {
        return InnsoApplication.get()
    }

    internal fun showSnackBarError(message: String) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, message, Snackbar.LENGTH_LONG)
    }

    internal fun showSnackBarError(message: Int) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, getApplication().getString(message), Snackbar.LENGTH_LONG)
    }

    internal fun showSnackBarMessage(@SnackBarFactory.SnackBarType typeSnackBar: String, message: String, duration: Int) {
        snackBar.postValue(SnackBarEvent(typeSnackBar, message, duration))
    }

    internal fun showSnackBarMessage(@SnackBarFactory.SnackBarType typeSnackBar: String, message: Int, duration: Int) {
        snackBar.postValue(SnackBarEvent(typeSnackBar, getApplication().getString(message), duration))
    }

    open fun showServiceError(throwable: Throwable) {
        //showSnackBarMessage(SnackBarFactory.TYPE_ERROR, ErrorUtil.getMessageError(throwable), Snackbar.LENGTH_LONG)
        loader.postValue(false)
    }

    override fun onCleared() {
        disposables.clear()
        children.forEach { it.clearDisposables() }
        super.onCleared()
    }

    fun hideLoading() {
        loader.postValue(false)
    }

    fun showLoading() {
        loader.postValue(true)
    }

    fun onCloseView() {
        closeView.postValue(FinishActivityModel(Activity.RESULT_OK))
    }

    internal fun addChild(childViewModel: BaseViewModel) {
        children.add(childViewModel)
        subscribeChild(childViewModel)
    }

    @Deprecated("BaseViewModel deprecated")
    fun subscribeChild(childViewModel: BaseViewModel) {
        disposables.addAll(childViewModel.observableShowLoading().subscribe { loader.value = it },
                childViewModel.startActivityEvent().subscribe { startActivity.setValue(it) },
                childViewModel.observableSnackBar().subscribe { snackBar.value = it },
                childViewModel.showDialogEvent().subscribe { dialog.setValue(it) })
    }

    open fun onSaveInstanceState(): Bundle {
        return Bundle()
    }

    open fun onRestoreInstanceState(savedInstanceState: Bundle) {}

    /**
     * LiveData
     */

    fun loaderState(): LiveData<Boolean> = loader

    fun snackBarMessage(): LiveData<SnackBarEvent> = snackBar

    fun showDialog(): LiveData<BasicDialog<*>> = dialog

    fun showAlertDialog(): LiveData<DialogParams> = alertDialog

    fun closeView(): LiveData<FinishActivityModel> = closeView

    fun startActivity(): LiveData<StartActivityModel> = startActivity

    fun startAction(): LiveData<StartActionModel> = startAction
}