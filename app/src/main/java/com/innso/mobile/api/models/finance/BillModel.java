package com.innso.mobile.api.models.finance;

import com.google.gson.annotations.SerializedName;

public class BillModel {

    @SerializedName("code")
    String code;

    @SerializedName("value")
    double value;

    @SerializedName("taxes")
    double taxes;

    @SerializedName("total")
    double total;

    @SerializedName("date")
    String date;

    @SerializedName("customer")
    String customer;

    @SerializedName("imageUrl")
    String image;

    public BillModel(String code, String date, String customerName, String value, String taxes, String imageUrl) {
        this.customer = customerName;
        this.code = code;
        this.date = date;
        this.value = Double.valueOf(value);
        this.taxes = Double.valueOf(taxes);
        this.image = imageUrl;
        this.total = this.value + this.taxes;
    }

    public String getCode() {
        return code;
    }

    public double getValue() {
        return value;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getTotal() {
        return total;
    }

    public String getDate() {
        return date;
    }

    public String getCustomer() {
        return customer;
    }

    public String getImage() {
        return image;
    }
}
