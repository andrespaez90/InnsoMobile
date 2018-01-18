package com.innso.mobile.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DecimalUtils {

    private static final String FORMAT_WITHOUT_POINTS = "#,##0";
    private static final String FORMAT_WITH_POINTS = "#,###.##";
    private static final String FORMAT_ALWAYS_WITH_POINTS = "#,##0.00";
    private static final String FORMAT_WITH_COMMA = "#.##0,00";

    public static String withCommaDecimal(double numericDouble) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        return decimalPatternFormat(numericDouble, "#.##0,00", symbols);
    }

    public static String withDecimals(double numericDouble) {
        return withFormat(numericDouble, "#,###.##");
    }

    public static String withInputDecimals(double numericDouble) {
        return withFormat(numericDouble, "#,##0.00");
    }

    public static String withFormat(double numericDouble, String format) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        return decimalFormat(numericDouble, format, symbols);
    }

    public static String withDecimals(String numeric) {
        return decimalFormat(Double.parseDouble(numeric), "#,###.##");
    }

    public static String withoutDecimals(double numericDouble) {
        return decimalFormat(numericDouble, "#,##0");
    }

    public static String withoutDecimals(String value) {
        return decimalFormat(Double.parseDouble(value), "#,##0");
    }

    public static String decimalFormat(double numericDouble, String format) {
        return (new DecimalFormat(format)).format(numericDouble);
    }

    public static String decimalFormat(double numericDouble, String format, DecimalFormatSymbols decimalFormatSymbols) {
        return (new DecimalFormat(format, decimalFormatSymbols)).format(numericDouble);
    }

    public static String decimalPatternFormat(double numericDouble, String localizedFormat, DecimalFormatSymbols decimalFormatSymbols) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##", decimalFormatSymbols);
        decimalFormat.applyLocalizedPattern(localizedFormat);
        return decimalFormat.format(numericDouble);
    }

}
