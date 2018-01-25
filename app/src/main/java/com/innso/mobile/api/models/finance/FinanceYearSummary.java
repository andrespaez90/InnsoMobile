package com.innso.mobile.api.models.finance;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class FinanceYearSummary {

    @SerializedName("total_revenue")
    double totalRevenue;

    @SerializedName("total_expenditure")
    double totalExpediture;

    @SerializedName("summary")
    Map<String, SummaryMonth> monthSummary;

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getTotalExpediture() {
        return totalExpediture;
    }

    public Map<String, SummaryMonth> getMonthSummary() {
        return monthSummary != null ? monthSummary : new HashMap<>();
    }
}
