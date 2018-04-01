package com.innso.mobile.api.models.finance;

import com.google.gson.annotations.SerializedName;
import com.innso.mobile.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpenseModel {

    @SerializedName("value")
    double value;

    @SerializedName("taxes")
    double taxes;

    @SerializedName("total")
    double total;

    @SerializedName("date")
    String date;

    @SerializedName("concept")
    String concept;

    @SerializedName("imageUrl")
    String image;

    public ExpenseModel(String date, String concept, String value, String taxes, String imageUrl) {
        this.concept = concept;
        this.date = date;
        this.value = Double.valueOf(value);
        this.taxes = Double.valueOf(taxes);
        this.image = imageUrl;
        this.total = this.value + this.taxes;
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

    public String getConcept() {
        return concept;
    }

    public String getImage() {
        return image;
    }

    public String getMonth() {
        Calendar calendar = DateUtils.getCalendarFromString(date, "dd/MM/yyyy");
        return new SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.getTime()).toLowerCase();
    }
}
