package com.innso.mobile.ui.viewModels;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.models.finance.FinanceYearSummary;
import com.innso.mobile.api.models.finance.SummaryMonth;
import com.innso.mobile.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class FinanceViewModel extends BaseViewModel {

    private PublishSubject<List<Double>> revenue = PublishSubject.create();

    private PublishSubject<Double> totalRevenue = PublishSubject.create();

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

    private void updateRevenue(FinanceYearSummary yearSummary) throws ParseException {
        hideLoading();
        SummaryMonth[] summaryMonth = sortFinanceSummary(yearSummary.getMonthSummary());
        revenue.onNext(getRevenuesValues(summaryMonth));
        totalRevenue.onNext(yearSummary.getTotalRevenue());
    }

    @NonNull
    private ArrayList<Double> getRevenuesValues(SummaryMonth[] summaryMonth) {
        ArrayList<Double> revenueValues = new ArrayList<>(12);
        for (SummaryMonth aSummaryMonth : summaryMonth) {
            if (aSummaryMonth != null) {
                revenueValues.add(aSummaryMonth.getRevenue());
            } else {
                revenueValues.add(0d);
            }
        }
        return revenueValues;
    }

    private SummaryMonth[] sortFinanceSummary(Map<String, SummaryMonth> response) throws ParseException {
        SummaryMonth[] summaryMonths = new SummaryMonth[12];
        for (Map.Entry<String, SummaryMonth> e : response.entrySet()) {
            summaryMonths[DateUtils.getMonth(e.getKey())] = e.getValue();
        }
        return summaryMonths;
    }

    public Observable<List<Double>> onRevenueUpdated() {
        return revenue.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Double> onTotalRevenueUpdated() {
        return totalRevenue.observeOn(AndroidSchedulers.mainThread());
    }


}
