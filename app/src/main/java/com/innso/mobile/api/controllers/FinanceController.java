package com.innso.mobile.api.controllers;

import com.innso.mobile.api.models.finance.BillModel;
import com.innso.mobile.api.services.FinanceApi;
import com.innso.mobile.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class FinanceController {

    private FinanceApi financeApi;

    public FinanceController(FinanceApi financeApi) {
        this.financeApi = financeApi;
    }

    public Completable addBill(String code, String date, String value, String taxes, String urlImage) {
        BillModel billModel = new BillModel(code, date, value, taxes, urlImage);
        return financeApi.addBill(datePath(date), billModel).subscribeOn(Schedulers.io());
    }

    private String datePath(String date) {
        Calendar calendar = DateUtils.getCalendarFromString(date, "dd/MM/yyyy");
        String month = new SimpleDateFormat("MMM", Locale.ENGLISH).format(calendar.getTime()).toLowerCase();
        return calendar.get(Calendar.YEAR) + "/" + month;
    }

}
