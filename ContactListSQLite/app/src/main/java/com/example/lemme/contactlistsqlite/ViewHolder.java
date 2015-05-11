package com.example.lemme.contactlistsqlite;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by lemme on 5/11/15.
 */
public class ViewHolder {
    ImageView photo;
    TextView name;
    TextView phone;
    TextView email;

    public ViewHolder(HashMap<String, View> contactData) {
        photo = (ImageView) contactData.get("Photo");
        name = (TextView) contactData.get("Name");
        phone = (TextView) contactData.get("Phone");
        email = (TextView) contactData.get("Email");
    }

    public void setContactInfoOnEachView(Contact contact) {
        photo.setImageResource(contact.getPhoto());
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
    }
}
