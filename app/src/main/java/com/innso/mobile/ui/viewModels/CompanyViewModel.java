package com.innso.mobile.ui.viewModels;


import android.view.View;

import com.innso.mobile.ui.activities.expenses.AddExpenseActivity;
import com.innso.mobile.ui.activities.NewCustomerActivity;
import com.innso.mobile.ui.activities.UsersActivity;
import com.innso.mobile.ui.activities.bills.BillsActivity;
import com.innso.mobile.ui.activities.expenses.ExpensesActivity;

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

    public void showBills(View view) {
        startActivityEvent.onNext(BillsActivity.class);
    }

    public void addExpense(View view) {
        startActivityEvent.onNext(ExpensesActivity.class);
    }

}
