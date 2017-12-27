package com.innso.mobile.api.models.users;

import com.google.gson.annotations.SerializedName;

public class UserRequest {

    private final transient String DEFAULT_PASSWORD = "123456";

    @SerializedName("email")
    String email;

    @SerializedName("displayName")
    String name;

    @SerializedName("password")
    String password;

    @SerializedName("phoneNumber")
    String phone;

    public UserRequest(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = DEFAULT_PASSWORD;
    }
}
