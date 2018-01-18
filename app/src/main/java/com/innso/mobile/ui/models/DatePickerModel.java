package com.innso.mobile.ui.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.innso.mobile.utils.DateUtils;

import java.util.Calendar;

public class DatePickerModel implements Parcelable {

    private Calendar startCalendar;

    private Calendar maxCalendar;

    private Calendar minCalendar;

    public DatePickerModel(Calendar startCalendar, Calendar maxCalendar) {
        this.startCalendar = startCalendar;
        this.maxCalendar = maxCalendar;
    }

    public DatePickerModel(Calendar startCalendar, Calendar maxCalendar, Calendar minCalendar) {
        this.startCalendar = startCalendar;
        this.maxCalendar = maxCalendar;
        this.minCalendar = minCalendar;
    }

    public DatePickerModel() {

    }

    public Calendar getStartCalendar() {
        return startCalendar;
    }

    public Calendar getMaxCalendar() {
        return maxCalendar;
    }

    public Calendar getMinCalendar() {
        return minCalendar;
    }

    public boolean isEmpty() {
        return startCalendar == null && maxCalendar == null && minCalendar == null;
    }

    private DatePickerModel(Parcel in) {
        String startCalendar = in.readString();
        String maxCalendar = in.readString();
        String minCalendar = in.readString();
        if (!TextUtils.isEmpty(startCalendar)) {
            this.startCalendar = DateUtils.getCalendarFromString(startCalendar);
        }
        if (!TextUtils.isEmpty(maxCalendar)) {
            this.maxCalendar = DateUtils.getCalendarFromString(maxCalendar);
        }
        if (!TextUtils.isEmpty(minCalendar)) {
            this.minCalendar = DateUtils.getCalendarFromString(minCalendar);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startCalendar != null ? startCalendar.toString() : "");
        dest.writeString(maxCalendar != null ? maxCalendar.toString() : "");
        dest.writeString(minCalendar != null ? minCalendar.toString() : "");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<DatePickerModel> CREATOR = new Parcelable.Creator<DatePickerModel>() {
        @Override
        public DatePickerModel createFromParcel(Parcel in) {
            return new DatePickerModel(in);
        }

        @Override
        public DatePickerModel[] newArray(int size) {
            return new DatePickerModel[size];
        }
    };


}
