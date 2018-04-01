package com.innso.mobile.ui.viewModels;

import android.view.View;

import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.models.finance.ExpenseModel;
import com.innso.mobile.ui.activities.bills.AddBillActivity;
import com.innso.mobile.ui.activities.expenses.AddExpenseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public class ExpensesViewModel extends BaseViewModel {

    private BehaviorSubject<List<ExpenseModel>> expenses = BehaviorSubject.createDefault(new ArrayList<>());

    @Inject
    FinanceController financeController;

    public ExpensesViewModel() {
        getComponent().inject(this);
        financeController.getExpenses().subscribe(expenses::onNext, this::showServiceError);
    }

    public void onClick(View view) {
        startActivityEvent.onNext(AddExpenseActivity.class);
    }

    public void update(String yearSelected) {
        showLoading();
        financeController.getExpenses(yearSelected)
                .doOnSuccess(list -> hideLoading())
                .subscribe(expenses::onNext, this::showServiceError);
    }

    public Observable<List<ExpenseModel>> onBillsChange() {
        return expenses.observeOn(AndroidSchedulers.mainThread());
    }

}
