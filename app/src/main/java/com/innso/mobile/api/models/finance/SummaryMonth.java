package com.innso.mobile.api.models.finance;

import com.google.gson.annotations.SerializedName;

public class SummaryMonth {

    @SerializedName("revenue")
    double revenue;

    @SerializedName("taxes")
    double taxes;

    public double getRevenue() {
        return revenue;
    }
}
