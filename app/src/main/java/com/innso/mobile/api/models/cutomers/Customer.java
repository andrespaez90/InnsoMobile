package com.innso.mobile.api.models.cutomers;


import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("phone")
    String phone;

    @SerializedName("url_photo")
    String photo;

    public Customer setId(String id) {
        this.id = id;
        return this;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public Customer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Customer setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
