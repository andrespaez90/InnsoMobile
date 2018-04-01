package com.innso.mobile.api.controllers;

import com.innso.mobile.api.models.finance.BillModel;
import com.innso.mobile.api.models.finance.ExpenseModel;
import com.innso.mobile.api.models.finance.FinanceYearSummary;
import com.innso.mobile.api.services.FinanceApi;
import com.innso.mobile.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FinanceController {

    private FinanceApi financeApi;

    public FinanceController(FinanceApi financeApi) {
        this.financeApi = financeApi;
    }

    public Single<FinanceYearSummary> getSummary(String year) {
        return financeApi.getAccountSummary(year)
                .map(response -> response.body() == null ? new FinanceYearSummary() : response.body())
                .subscribeOn(Schedulers.io());
    }

    public Completable addBill(String code, String date, String customerName, String value, String taxes, String urlImage) {
        BillModel billModel = new BillModel(code, date, customerName, value, taxes, urlImage);
        return financeApi.addBill(datePath(date), billModel).subscribeOn(Schedulers.io());
    }

    public Completable addExpene(String date, String concept, String value, String taxes, String urlImage) {
        ExpenseModel expenseModel = new ExpenseModel(date, concept, value, taxes, urlImage);
        return financeApi.addExpense(datePath(date), expenseModel).subscribeOn(Schedulers.io());
    }

    public Single<List<BillModel>> getBills() {
        return getBills(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }

    public Single<List<BillModel>> getBills(String year) {
        return financeApi.getBills(year)
                .map(response -> response.body() == null ? new HashMap<String, Map<String, BillModel>>() : response.body())
                .map(this::concatBills)
                .subscribeOn(Schedulers.io());
    }

    private List<BillModel> concatBills(Map<String, Map<String, BillModel>> response) {
        List<BillModel> bills = new ArrayList<>();
        List<Map<String, BillModel>> serverBillsModel = new ArrayList<>(response.values());
        for (int i = 0, size = serverBillsModel.size(); i < size; i++) {
            bills.addAll(serverBillsModel.get(i).values());
        }
        return bills;
    }

    private String datePath(String date) {
        Calendar calendar = DateUtils.getCalendarFromString(date, "dd/MM/yyyy");
        String month = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime()).toLowerCase();
        return calendar.get(Calendar.YEAR) + "/" + month;
    }

}
