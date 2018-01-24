package com.innso.mobile.ui.viewModels;

import android.databinding.ObservableField;
import android.view.View;

import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.models.finance.SummaryMonth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class FinanceViewModel extends BaseViewModel {

    private PublishSubject<List<Double>> revenue = PublishSubject.create();

    public ObservableField<String> currentYear = new ObservableField<>("");

    @Inject
    FinanceController financeController;

    public FinanceViewModel() {
        getComponent().inject(this);
        currentYear.set(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }

    public void getSummary() {
        showLoading();
        financeController.getSummary(currentYear.get())
                .subscribe(this::updateRevenue, this::showServiceError);
    }

    public void onNextYear(View view) {
        currentYear.set(String.valueOf(Integer.parseInt(currentYear.get()) + 1));
        getSummary();
    }

    public void onLastYear(View view) {
        currentYear.set(String.valueOf(Integer.parseInt(currentYear.get()) - 1));
        getSummary();
    }

    private void updateRevenue(SummaryMonth[] summaryMonth) {
        hideLoading();
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
