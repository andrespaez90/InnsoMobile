package com.innso.mobile.utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    public static int getMonth(String month) throws ParseException {
        Date date = new SimpleDateFormat("MMM", Locale.US).parse(month);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static String getCurrentDate(String format, Locale locale) {
        return (new SimpleDateFormat(format, locale)).format(new Date());
    }

    public static String parseDateToStringWithFormat(Date date, String format) {
        return parseDateToStringWithFormat(date, format, Locale.getDefault());
    }

    public static String parseDateToStringWithFormat(Date date, String format, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(date);
    }

    public static Calendar getCalendarFromString(String dateString) {
        return getCalendarFromString(dateString, "yyyy-MM-dd");
    }

    public static Calendar getCalendarFromString(String dateString, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromString(dateString, format));
        return calendar;
    }

    public static Date getDateFromString(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            return dateString != null && dateString.length() != 0 ? sdf.parse(dateString) : new Date();
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
