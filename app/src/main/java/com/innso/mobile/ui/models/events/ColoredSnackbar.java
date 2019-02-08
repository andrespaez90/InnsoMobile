package com.innso.mobile.ui.models.events;


import androidx.annotation.ColorInt;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

public class ColoredSnackbar {

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, @ColorInt int colorId) {

        View snackBarView = getSnackBarLayout(snackbar);

        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
        }

        return snackbar;
    }

    public static Snackbar draw(Snackbar snackbar, int color) {
        return colorSnackBar(snackbar, color);
    }

}
