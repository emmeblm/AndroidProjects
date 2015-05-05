package com.example.lemme.arrayadapteritemsdinamicos;

/**
 * Created by lemme on 5/5/15.
 */
public class Contact {
    private int photo;

    private String name;
    private String phone;
    private String address;
    private int code;

    public Contact(int photo, String name, String phone, String address, int code) {
        this.photo = photo;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.code = code;
    }

    public int getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getCode() {
        return code;
    }
}
