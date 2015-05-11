package com.example.lemme.contactlistsharedpreferences;

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
        photo = parcel.readInt();
        name = parcel.readString();
        phone = parcel.readString();
        email = parcel.readString();
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
        parcel.writeInt(photo);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(email);
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
