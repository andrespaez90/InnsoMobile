package com.innso.mobile.ui.viewModels;


import android.text.TextUtils;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.innso.mobile.R;
import com.innso.mobile.api.controllers.CustomerController;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class NewCustomerViewModel extends BaseViewModel {

    public ObservableField<String> id = new ObservableField<>("");

    public ObservableField<String> name = new ObservableField<>("");

    public ObservableField<String> address = new ObservableField<>("");

    public ObservableField<String> phone = new ObservableField<>("");

    private PublishSubject<String> customerCreated = PublishSubject.create();

    @Inject
    CustomerController customerController;

    @Inject
    FirebaseAuth firebaseAuth;

    public NewCustomerViewModel() {
        getComponent().inject(this);
    }

    public void onFinalizeClick(View view) {

        if (!TextUtils.isEmpty(name.get()) && !TextUtils.isEmpty(name.get()) && !TextUtils.isEmpty(address.get())) {

            customerController.addCustomer(id.get(), name.get(), address.get(), phone.get())
                    .subscribe(() -> customerCreated.onNext(name.get()), this::showServiceError);

        } else {
            showSnackBarError(R.string.error_empty_fields);
        }
    }

    public Observable<String> onCustomerCreated() {
        return customerCreated.observeOn(AndroidSchedulers.mainThread());
    }

}
