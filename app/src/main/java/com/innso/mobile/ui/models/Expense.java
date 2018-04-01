package com.innso.mobile.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.innso.mobile.api.models.finance.BillModel;
import com.innso.mobile.api.models.finance.ExpenseModel;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.MoneyUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Expense implements Parcelable {

    public String id;

    public String customer;

    public String date;

    public String value;

    public String taxes;

    public String total;

    public String imageUrl;


    protected Expense(Parcel in) {
        this.id = in.readString();
        this.date = in.readString();
        this.value = in.readString();
        this.taxes = in.readString();
        this.total = in.readString();
        this.imageUrl = in.readString();
    }

    public Expense(ExpenseModel expenseModel) {
        this.date = expenseModel.getDate();
        this.customer = expenseModel.getConcept();
        this.value = MoneyUtil.getBasicCurrencyPrice("CO", expenseModel.getValue());
        this.taxes = MoneyUtil.getBasicCurrencyPrice("CO", expenseModel.getTaxes());
        this.total = MoneyUtil.getBasicCurrencyPrice("CO", expenseModel.getTotal());
        this.imageUrl = expenseModel.getImage();

    }

    public String getMonth() {
        Calendar calendar = DateUtils.getCalendarFromString(date, "dd/MM/yyyy");
        return new SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.getTime()).toLowerCase();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.date);
        dest.writeString(this.value);
        dest.writeString(this.taxes);
        dest.writeString(this.total);
        dest.writeString(this.imageUrl);
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel source) {
            return new Expense(source);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };
}
