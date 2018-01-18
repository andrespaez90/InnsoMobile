package com.innso.mobile.utils;


import android.content.Context;
import android.text.TextUtils;

import com.innso.mobile.R;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtil {

    private static final String DOLLAR_SYMBOL = "$";
    private static final String EURO_SYMBOL = "€";
    private static final String POUND_SYMBOL = "£";
    private static final String REAL_SYMBOL = "R$ ";
    public static final String CODE_COLOMBIA = "co";
    public static final String CODE_MEXICO = "mx";
    public static final String CODE_BRAZIL = "br";
    private static final String CURRENCY_BRAZIL_CODE = "BRL";
    private static final String CURRENCY_COLOMBIA_CODE = "COP";
    private static final String CURRENCY_MEXICO_CODE = "MXN";

    public static String parseToMoneyFormat(double value) {
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
        usdCostFormat.setMinimumFractionDigits(0);
        usdCostFormat.setMaximumFractionDigits(2);
        String newValue = usdCostFormat.format(value);
        newValue = newValue.replaceAll(",", ".");
        return newValue;
    }

    public static String getCurrencyPrice(Context context, String countryCode, double value) {
        return getCurrencyPrice(context, countryCode, String.valueOf(value), 0);
    }

    public static String getCurrencyPrice(Context context, String countryCode, double value, int formatResId) {
        return getCurrencyPrice(context, countryCode, String.valueOf(value), formatResId);
    }

    public static String getCurrencyPrice(Context context, String countryCode, String value) {
        return getCurrencyPrice(context, countryCode, value, 0);
    }

    public static String getCurrencySymbol(String countryCode) {
        String var1 = countryCode.toLowerCase();
        byte var2 = -1;
        switch (var1.hashCode()) {
            case 3152:
                if (var1.equals("br")) {
                    var2 = 1;
                }
                break;
            case 3180:
                if (var1.equals("co")) {
                    var2 = 0;
                }
                break;
            case 3499:
                if (var1.equals("mx")) {
                    var2 = 2;
                }
        }

        switch (var2) {
            case 0:
                return "COP";
            case 1:
                return "BRL";
            case 2:
                return "MXN";
            default:
                return "COP";
        }
    }

    public static String getCurrencyPrice(Context context, String countryCode, String value, int formatResId) {
        String currencyPrice = "";
        String var6 = countryCode.toLowerCase();
        byte var7 = -1;
        switch (var6.hashCode()) {
            case 3152:
                if (var6.equals("br")) {
                    var7 = 2;
                }
                break;
            case 3180:
                if (var6.equals("co")) {
                    var7 = 0;
                }
                break;
            case 3499:
                if (var6.equals("mx")) {
                    var7 = 1;
                }
        }

        int resId;
        switch (var7) {
            case 0:
                resId = formatResId != 0 ? formatResId : R.string.currency_sign;
                currencyPrice = !TextUtils.isEmpty(value) ?
                        String.format(context.getString(resId), DecimalUtils.withoutDecimals(Double.parseDouble(value))) :
                        String.format(context.getString(resId), DecimalUtils.withoutDecimals(0.0D));
                break;
            case 1:
                resId = formatResId != 0 ? formatResId : R.string.currency_sign_mxn;
                currencyPrice = !TextUtils.isEmpty(value) ?
                        String.format(context.getString(resId), DecimalUtils.withDecimals(Double.parseDouble(value))) :
                        String.format(context.getString(resId), DecimalUtils.withDecimals(0.0D));
                break;
            case 2:
                resId = formatResId != 0 ? formatResId : R.string.currency_sign_brl;
                currencyPrice = !TextUtils.isEmpty(value) ?
                        String.format(context.getString(resId), DecimalUtils.withCommaDecimal(Double.parseDouble(value))) :
                        String.format(context.getString(resId), DecimalUtils.withCommaDecimal(0.0D));
        }

        return currencyPrice;
    }

    public static String getInputCurrencyPrice(String countryCode, double value) {
        double valueToFormat = value < 0.0D ? value * -1.0D : value;
        String currencyPrice = "";
        if (countryCode.toLowerCase().equals("mx")) {
            currencyPrice = "$" + DecimalUtils.withInputDecimals(valueToFormat);
            return value < 0.0D ? "-" + currencyPrice : currencyPrice;
        } else {
            return getBasicCurrencyPrice(countryCode, value);
        }
    }

    public static String getBasicCurrencyPrice(String countryCode, double value) {
        double valueToFormat = value < 0.0D ? value * -1.0D : value;
        String currencyPrice = "";
        String var6 = countryCode.toLowerCase();
        byte var7 = -1;
        switch (var6.hashCode()) {
            case 3152:
                if (var6.equals("br")) {
                    var7 = 2;
                }
                break;
            case 3180:
                if (var6.equals("co")) {
                    var7 = 0;
                }
                break;
            case 3499:
                if (var6.equals("mx")) {
                    var7 = 1;
                }
        }

        switch (var7) {
            case 0:
            case 1:
                currencyPrice = "$" + DecimalUtils.withDecimals(valueToFormat);
                break;
            case 2:
                currencyPrice = "R$ " + DecimalUtils.withCommaDecimal(valueToFormat);
        }

        return value < 0.0D ? "-" + currencyPrice : currencyPrice;
    }

    public static int getIntFromMoney(String money) {
        String clearValue = money.replace("$", "").replaceAll(",", "").replaceAll("[.]", "");
        return Integer.parseInt(clearValue);
    }

    private static String getCleanValue(String value) {
        String valueWithoutSymbols = value.replace("$", "");
        valueWithoutSymbols = valueWithoutSymbols.replace("€", "");
        valueWithoutSymbols = valueWithoutSymbols.replace("£", "");
        valueWithoutSymbols = valueWithoutSymbols.replace("R$ ", "");
        valueWithoutSymbols = valueWithoutSymbols.replaceAll("\\s", "");
        valueWithoutSymbols = valueWithoutSymbols.replaceAll(",", "");
        valueWithoutSymbols = valueWithoutSymbols.replaceAll("\\.", "");
        if (valueWithoutSymbols.length() > 0 && valueWithoutSymbols.charAt(0) == 48) {
            valueWithoutSymbols = valueWithoutSymbols.substring(1);
        }

        return valueWithoutSymbols.trim();
    }

    public static String raw(double value) {
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance();
        usdCostFormat.setMinimumFractionDigits(0);
        usdCostFormat.setMaximumFractionDigits(0);
        return usdCostFormat.format(value).replace("$", "").replace("€", "");
    }

    private static String rawWithSymbol(double value) {
        return "$" + raw(value).replaceAll("\\s", "");
    }

    public static String parseToMoneyFormat(String value) {
        String valueWithoutSymbols = getCleanValue(value);
        int cost = 0;
        String text = "";
        if (valueWithoutSymbols.length() > 0) {
            cost = Integer.parseInt(valueWithoutSymbols);
        }

        if (cost > 0) {
            text = rawWithSymbol((double) cost).trim();
        }

        return text;
    }

}
