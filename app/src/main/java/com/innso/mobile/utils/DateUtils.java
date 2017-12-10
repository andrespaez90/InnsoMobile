package com.innso.mobile.utils;

import java.text.DateFormatSymbols;

public class DateUtils {

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
