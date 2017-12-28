package com.innso.mobile.utils.models;

import com.google.gson.annotations.SerializedName;

public class DefaultError {

    @SerializedName("error")
    String error;

    @SerializedName("message")
    String message;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
