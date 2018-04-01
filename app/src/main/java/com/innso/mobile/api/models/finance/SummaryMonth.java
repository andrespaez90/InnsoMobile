package com.innso.mobile.api.models.finance;

import com.google.gson.annotations.SerializedName;

public class SummaryMonth {

    @SerializedName("revenue")
    double revenue;

    @SerializedName("expenses")
    double expenses;

    @SerializedName("paid_taxes")
    double paidTaxes;

    @SerializedName("received_taxes")
    double receivedTaxes;

    public double getRevenue() {
        return revenue;
    }

    public double getExpenses() {
        return expenses;
    }
}
