package com.example.lemme.arrayadapteritemsdinamicossqlite;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by lemme on 5/5/15.
 */
public class Contact extends SugarRecord<Contact> implements Parcelable {
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

    public Contact(Parcel parcel) {
        readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        int[] intTemp = new int[2];
        String[] stringTemp = new String[3];

        parcel.readIntArray(intTemp);
        parcel.readStringArray(stringTemp);

        photo = intTemp[0];
        code = intTemp[1];
        name = stringTemp[0];
        phone = stringTemp[1];
        address = stringTemp[2];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] {photo, code});
        parcel.writeStringArray(new String[] {name, phone, address});
    }

    public static final Parcelable.Creator<Contact> CREATOR
            = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
