package com.innso.mobile.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.innso.mobile.api.models.finance.BillModel;
import com.innso.mobile.utils.DateUtils;
import com.innso.mobile.utils.MoneyUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Bill implements Parcelable {

    public String id;

    public String customer;

    public String date;

    public String value;

    public String taxes;

    public String total;

    public String imageUrl;

    public Bill(BillModel billModel) {
        this.id = billModel.getCode();
        this.date = billModel.getDate();
        this.customer = billModel.getCustomer();
        this.value = MoneyUtil.getBasicCurrencyPrice("CO", billModel.getValue());
        this.taxes = MoneyUtil.getBasicCurrencyPrice("CO", billModel.getTaxes());
        this.total = MoneyUtil.getBasicCurrencyPrice("CO", billModel.getTotal());
        this.imageUrl = billModel.getImage();
    }

    protected Bill(Parcel in) {
        this.id = in.readString();
        this.date = in.readString();
        this.value = in.readString();
        this.taxes = in.readString();
        this.total = in.readString();
        this.customer = in.readString();
        this.imageUrl = in.readString();
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
        dest.writeString(this.customer);
        dest.writeString(this.imageUrl);
    }

    public static final Parcelable.Creator<Bill> CREATOR = new Parcelable.Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel source) {
            return new Bill(source);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };
}
