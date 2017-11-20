package com.innso.mobile.utils.models;

import com.google.gson.annotations.SerializedName;

public class DefaultError {

    @SerializedName("error")
    private String errorMessage;

    public String getError() {
        return errorMessage;
    }


}
