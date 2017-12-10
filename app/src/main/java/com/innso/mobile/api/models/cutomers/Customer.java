package com.innso.mobile.api.models.cutomers;


import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("nit")
    String NIT;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("phone")
    String phone;

}
