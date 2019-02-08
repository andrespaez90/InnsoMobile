package com.innso.mobile.ui.viewModels;

import android.view.View;

import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.models.finance.BillModel;
import com.innso.mobile.ui.activities.bills.AddBillActivity;
import com.innso.mobile.viewModels.models.StartActivityModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public class BillListViewModel extends BaseViewModel {

    private BehaviorSubject<List<BillModel>> bills = BehaviorSubject.createDefault(new ArrayList<>());

    @Inject
    FinanceController financeController;

    public BillListViewModel() {
        getComponent().inject(this);
        financeController.getBills().subscribe(bills::onNext, this::showServiceError);
    }

    public void onClick(View view) {
        startActivityEvent.onNext(new StartActivityModel(AddBillActivity.class));
    }

    public void update(String yearSelected) {
        showLoading();
        financeController.getBills(yearSelected)
                .doOnSuccess(list -> hideLoading())
                .subscribe(bills::onNext, this::showServiceError);
    }

    public Observable<List<BillModel>> onBillsChange() {
        return bills.observeOn(AndroidSchedulers.mainThread());
    }

}
