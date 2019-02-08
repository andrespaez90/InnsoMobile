package com.innso.mobile.ui.viewModels;

import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;
import android.view.View;

import com.innso.mobile.api.controllers.FinanceController;
import com.innso.mobile.api.models.finance.FinanceYearSummary;
import com.innso.mobile.api.models.finance.SummaryMonth;
import com.innso.mobile.ui.activities.bills.BillsActivity;
import com.innso.mobile.ui.activities.expenses.ExpensesActivity;
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

    private PublishSubject<List<Double>> expenses = PublishSubject.create();

    private PublishSubject<List<Double>> totalMonth = PublishSubject.create();

    private PublishSubject<Double> totalRevenue = PublishSubject.create();

    private PublishSubject<Double> totalExpenditure = PublishSubject.create();

    private PublishSubject<Double> totalBanks = PublishSubject.create();

    private PublishSubject<Double> totalCash = PublishSubject.create();

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
        SummaryMonth[] summaryMonth = sortFinanceSummary(yearSummary.getMonthSummary());
        ArrayList<Double> revenuePerMonth = new ArrayList<>(12);
        ArrayList<Double> expensesPerMonth = new ArrayList<>(12);
        totalMonth.onNext(getRevenueExpensesValues(summaryMonth, revenuePerMonth, expensesPerMonth));
        revenue.onNext(revenuePerMonth);
        expenses.onNext(expensesPerMonth);
        totalRevenue.onNext(yearSummary.getTotalRevenue());
        totalExpenditure.onNext(yearSummary.getTotalExpediture());
        totalBanks.onNext(yearSummary.getTotalBanks());
        totalCash.onNext(yearSummary.getTotalCash());
        hideLoading();
    }

    @NonNull
    private ArrayList<Double> getRevenueExpensesValues(SummaryMonth[] summaryMonth, ArrayList<Double> revenueVales, ArrayList<Double> expenseValues) {
        ArrayList<Double> totalMonth = new ArrayList<>(12);
        for (SummaryMonth month : summaryMonth) {
            if (month != null) {
                revenueVales.add(month.getRevenue());
                expenseValues.add(month.getExpenses());
                totalMonth.add(month.getRevenue() - month.getExpenses());
            } else {
                revenueVales.add(0d);
                expenseValues.add(0d);
                totalMonth.add(0d);
            }
        }
        return totalMonth;
    }

    private SummaryMonth[] sortFinanceSummary(Map<String, SummaryMonth> response) throws ParseException {
        SummaryMonth[] summaryMonths = new SummaryMonth[12];
        for (Map.Entry<String, SummaryMonth> e : response.entrySet()) {
            summaryMonths[DateUtils.getMonth(e.getKey())] = e.getValue();
        }
        return summaryMonths;
    }

    public void onExpensesClick(View view) {
        startActivityEvent.onNext(ExpensesActivity.class);
    }

    public void onRevenueClick(View view) {
        startActivityEvent.onNext(BillsActivity.class);
    }

    public Observable<List<Double>> onRevenueUpdated() {
        return revenue.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Double>> onExpensesUpdated() {
        return expenses.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Double>> onTotalMonthUpdated() {
        return totalMonth.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Double> onTotalRevenueUpdated() {
        return totalRevenue.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Double> onTotalExpenditureUpdated() {
        return totalExpenditure.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Double> onTotalBanksUpdated() {
        return totalBanks.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Double> onTotalCashUpdated() {
        return totalCash.observeOn(AndroidSchedulers.mainThread());
    }


}
