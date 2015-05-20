package com.example.lemme.contactlistsharedpreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

/**
 * Created by lemme on 5/11/15.
 */
public class ViewHolder {
    ImageView photo;
    TextView name;
    TextView phone;
    TextView email;
    private Bitmap imageCache;

    public ViewHolder(HashMap<String, View> contactData) {
        photo = (ImageView) contactData.get("Photo");
        name = (TextView) contactData.get("Name");
        phone = (TextView) contactData.get("Phone");
        email = (TextView) contactData.get("Email");
    }

    public void setContactInfoOnEachView(Contact contact) {
        setPhoto(contact);
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
    }

    public void setPhoto(Contact contact) {
        try {
            String imagePath = contact.getImagePath();
            if (imagePath == null || imagePath.length() == 0)
                photo.setImageResource(contact.getPhoto());
            else {
                if(imageCache != null)
                    imageCache.recycle();
                imageCache = BitmapFactory.decodeFile(imagePath);
                photo.setImageBitmap(imageCache);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
