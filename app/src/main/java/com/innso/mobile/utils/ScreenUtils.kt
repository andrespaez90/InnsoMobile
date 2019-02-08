package com.innso.mobile.utils

import android.content.Context
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.WindowManager

fun getScreenSize(context: Context): IntArray {

    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val metrics = DisplayMetrics()

    windowManager.defaultDisplay.getMetrics(metrics)

    val x = metrics.widthPixels
    val y = metrics.heightPixels

    val screenSize = IntArray(2)

    screenSize[0] = Math.min(x, y)
    screenSize[1] = Math.max(x, y)

    return screenSize
}
