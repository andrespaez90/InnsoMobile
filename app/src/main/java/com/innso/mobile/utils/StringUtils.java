package com.innso.mobile.utils;


import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static Spannable setSpannablesFromRegex(CharSequence completeString, String regex, ParcelableSpan... spans) {
        Spannable spannable = new SpannableString("");
        if (!TextUtils.isEmpty(completeString)) {
            spannable = new SpannableString(completeString);
            Pattern pattern = Pattern.compile(regex, 2);
            Matcher match = pattern.matcher(completeString);

            while (match.find()) {
                ParcelableSpan[] var6 = spans;
                int var7 = spans.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    ParcelableSpan span = var6[var8];
                    spannable.setSpan(span, match.start(), match.end(), 33);
                }
            }
        }

        return spannable;
    }

}
