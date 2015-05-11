package com.example.lemme.contactlistsqlite;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by lemme on 5/11/15.
 */
public class Contact extends SugarRecord<Contact> implements Parcelable {
    private int photo;
    private String name;
    private String phone;
    private String email;

    public Contact(int photo, String name, String phone, String email) {
        this.photo = photo;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Contact(Parcel parcel) {
        readFromParcel(parcel);
    }

    public Contact() {
        super();
    }

    private void readFromParcel(Parcel parcel) {
        int[] intTemp = new int[2];
        String[] stringTemp = new String[3];

        parcel.readIntArray(intTemp);
        parcel.readStringArray(stringTemp);

        photo = intTemp[0];
        name = stringTemp[0];
        phone = stringTemp[1];
        email = stringTemp[2];
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

    public String getEmail() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeIntArray(new int[] {photo});
        parcel.writeStringArray(new String[] {name, phone, email});
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
