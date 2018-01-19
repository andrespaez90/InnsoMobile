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

    @SerializedName("imageUrl")
    String image;


    public BillModel(String code, String date, String value, String taxes, String imageUrl) {
        this.code = code;
        this.date = date;
        this.value = Double.valueOf(value);
        this.taxes = Double.valueOf(taxes);
        this.image = imageUrl;
        this.total = this.value + this.taxes;
    }
}
