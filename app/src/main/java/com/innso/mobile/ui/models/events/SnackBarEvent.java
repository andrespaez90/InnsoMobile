package com.innso.mobile.ui.models.events;


import com.innso.mobile.ui.factories.SnackBarFactory;

public class SnackBarEvent {

    private @SnackBarFactory.SnackBarType String typeSnackBar;
    private String message;
    private int duration;

    public SnackBarEvent(String typeSnackBar, String message, int duration) {
        this.typeSnackBar = typeSnackBar;
        this.message = message;
        this.duration = duration;
    }

    public @SnackBarFactory.SnackBarType String getTypeSnackBar() {
        return typeSnackBar;
    }

    public String getMessage() {
        return message;
    }

    public int getDuration() {
        return duration;
    }
}
