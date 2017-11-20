package com.innso.mobile.api.models.app;


import com.google.gson.annotations.SerializedName;

public class GeneralInformation {

    @SerializedName("app_version")
    int buildVersion;

    public int getBuildVersion() {
        return buildVersion;
    }

}
