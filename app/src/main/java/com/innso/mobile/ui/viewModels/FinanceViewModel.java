package com.innso.mobile.ui.viewModels;

import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.models.finance.SummaryMonth;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class FinanceViewModel extends BaseViewModel {

    private PublishSubject<List<Double>> revenue = PublishSubject.create();

    @Inject
    FinanceController financeController;

    public FinanceViewModel() {
        getComponent().inject(this);
    }

    public void getSummary() {
        financeController.getSummary()
                .subscribe(this::updateRevenue, this::showServiceError);
    }

    private void updateRevenue(SummaryMonth[] summaryMonth) {
        ArrayList<Double> revenueValues = new ArrayList<>(12);
        for (int i = 0; i < summaryMonth.length; i++) {
            if (summaryMonth[i] != null) {
                revenueValues.add(summaryMonth[i].getRevenue());
            } else {
                revenueValues.add(0d);
            }
        }
        revenue.onNext(revenueValues);
    }

    public Observable<List<Double>> onRevenueUpdated() {
        return revenue.observeOn(AndroidSchedulers.mainThread());
    }


}
