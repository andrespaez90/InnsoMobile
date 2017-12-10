package com.innso.mobile.utils;


import android.os.Build;

public class AndroidVersionUtil {

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean hasMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static boolean hasNougat() {
        return Build.VERSION.SDK_INT >= 24;
    }

}
