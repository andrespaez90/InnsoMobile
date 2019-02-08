package com.innso.mobile.ui.models;


public class NewUser {

    private String name;

    private String email;

    private String password;

    private String phone;

    public NewUser(String name, String email, String password, String phone) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
