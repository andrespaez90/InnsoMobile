package com.innso.mobile.ui.viewModels;


import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.util.Pair;

import com.innso.mobile.InnsoApplication;
import com.innso.mobile.di.components.DaggerViewModelComponent;
import com.innso.mobile.di.components.ViewModelComponent;
import com.innso.mobile.ui.factories.SnackBarFactory;
import com.innso.mobile.ui.models.events.SnackBarEvent;
import com.innso.mobile.utils.ErrorUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class BaseViewModel {

    private BehaviorSubject<Boolean> showLoading = BehaviorSubject.create();

    private PublishSubject<Boolean> showKeyboard = PublishSubject.create();

    private PublishSubject<SnackBarEvent> snackBarSubject = PublishSubject.create();

    private BehaviorSubject<Pair<Boolean, Integer>> showProgressDialog = BehaviorSubject.createDefault(new Pair<>(false, 0));

    PublishSubject<Class> startActivityEvent = PublishSubject.create();

    ArrayList<Disposable> disposables = new ArrayList<>();

    protected ViewModelComponent getComponent() {
        return DaggerViewModelComponent.builder()
                .appComponent(getApplication().getAppComponent())
                .build();
    }

    protected InnsoApplication getApplication() {
        return InnsoApplication.get();
    }
    public ArrayList<Disposable> getDisposablesArray() {
        return disposables;
    }

    public Disposable[] getDisposables() {
        int size = disposables.size();
        Disposable[] vectorDisposable = new Disposable[size];
        for (int i = 0; i < size; i++) {
            vectorDisposable[i] = disposables.get(i);
        }
        return vectorDisposable;
    }


    public void clearDisposables() {

        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }

    protected void showKeyboard() {
        showKeyboard.onNext(true);
    }

    protected void hideKeyBoard() {
        showKeyboard.onNext(false);
    }

    protected void eventKeyboard(boolean event) {
        showKeyboard.onNext(event);
    }

    public void showLoading() {
        showLoading.onNext(true);
    }

    public void hideLoading() {
        showLoading.onNext(false);
    }

    protected void eventLoading(Boolean event) {
        showLoading.onNext(event);
    }

    protected void showProgressDialog(@StringRes int string) {
        showProgressDialog.onNext(new Pair<>(true, string));
    }

    protected void dismissProgressDialog() {
        showProgressDialog.onNext(new Pair<>(false, 0));
    }

    protected void hideProgressDialog() {
        showProgressDialog.onNext(new Pair<>(false, 0));
    }

    protected void eventProgressDialog(Pair<Boolean, Integer> event) {
        showProgressDialog.onNext(event);
    }

    protected void showServiceError(Throwable throwable) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, ErrorUtils.getMessageError(throwable), Snackbar.LENGTH_LONG);
        hideLoading();
        hideProgressDialog();
    }

    protected void showSnackBarError(String message) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, message, Snackbar.LENGTH_LONG);
    }

    protected void showSnackBarError(int messageId) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, messageId, Snackbar.LENGTH_LONG);
    }

    protected void eventSnackBar(SnackBarEvent event) {
        snackBarSubject.onNext(event);
    }

    protected void showSnackBarMessage(@SnackBarFactory.SnackBarType String typeSnackBar, int stringResId, int duration) {
        String message = getApplication().get().getResources().getString(stringResId);
        showSnackBarMessage(typeSnackBar, message, duration);
    }

    protected void showSnackBarMessage(@SnackBarFactory.SnackBarType String typeSnackBar, String message, int duration) {
        snackBarSubject.onNext(new SnackBarEvent(typeSnackBar, message, duration));
    }

    public Observable<Boolean> observableShowLoading() {
        return showLoading.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Pair<Boolean, Integer>> observableShowProgress() {
        return showProgressDialog.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> observableShowKeyboard() {
        return showKeyboard.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SnackBarEvent> observableSnackBar() {
        return snackBarSubject.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Class> observableStartActivity() {
        return startActivityEvent.observeOn(AndroidSchedulers.mainThread());
    }
}