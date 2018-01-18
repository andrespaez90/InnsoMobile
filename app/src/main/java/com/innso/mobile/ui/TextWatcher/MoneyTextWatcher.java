package com.innso.mobile.ui.TextWatcher;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.innso.mobile.utils.MoneyUtil;

public class MoneyTextWatcher implements TextWatcher {
    private boolean mEditing;
    private String countryCode;
    private EditText editText;

    public MoneyTextWatcher(EditText editText, String countryCode) {
        this.editText = editText;
        this.countryCode = countryCode;
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        if (!this.mEditing) {
            this.mEditing = true;
            String digits = s.toString().replaceAll("\\D", "");
            if (TextUtils.isEmpty(digits)) {
                digits = "0";
            }

            String price = MoneyUtil.getInputCurrencyPrice(this.countryCode, this.getDigits(digits));
            s.replace(0, s.length(), price);
            this.editText.setText(price);
            this.editText.setSelection(price.length());
            this.mEditing = false;
        }

    }

    private double getDigits(String digitsText) {
        double value = Double.parseDouble(digitsText);
        if (!this.countryCode.toLowerCase().equals("co")) {
            value /= 100.0D;
        }

        return value;
    }

    @NonNull
    public static String getMoneyRegexForParseToDouble(@NonNull String miCityCode) {
        String regex = "[^\\d.]";
        if (miCityCode.toLowerCase().equals("br")) {
            regex = "[^\\d,]";
        }

        return regex;
    }
}