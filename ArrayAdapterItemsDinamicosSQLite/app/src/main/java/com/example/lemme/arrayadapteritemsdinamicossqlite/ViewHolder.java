package com.example.lemme.arrayadapteritemsdinamicossqlite;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by lemme on 5/5/15.
 */
public class ViewHolder {
    ImageView photo;
    TextView name;
    TextView phone;
    TextView address;
    TextView code;

    public ViewHolder(HashMap<String, View> contactData) {
        photo = (ImageView) contactData.get("Photo");
        name = (TextView) contactData.get("Name");
        phone = (TextView) contactData.get("Phone");
        address = (TextView) contactData.get("Address");
        code = (TextView) contactData.get("Code");
    }

    public void setContactInfoOnEachView(Contact contact) {
        photo.setImageResource(contact.getPhoto());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        address.setText(contact.getAddress());
        code.setText(String.valueOf(contact.getCode()));
    }
}
