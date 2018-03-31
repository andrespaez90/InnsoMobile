package com.innso.mobile.ui.viewModels;


import android.view.View;

import com.innso.mobile.ui.activities.NewCustomerActivity;
import com.innso.mobile.ui.activities.UsersActivity;
import com.innso.mobile.ui.activities.bills.BillsActivity;

public class CompanyViewModel extends BaseViewModel {


    public CompanyViewModel() {
        getComponent().inject(this);
    }

    public void addUser(View view) {
        startActivityEvent.onNext(UsersActivity.class);
    }

    public void addCustomer(View view) {
        startActivityEvent.onNext(NewCustomerActivity.class);
    }

    public void addBill(View view) {
        startActivityEvent.onNext(BillsActivity.class);
    }

}
