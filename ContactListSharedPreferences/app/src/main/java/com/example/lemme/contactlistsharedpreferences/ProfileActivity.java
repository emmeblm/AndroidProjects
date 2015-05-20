package com.example.lemme.contactlistsharedpreferences;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lemme.contactlistsharedpreferences.Contact;
import com.example.lemme.contactlistsharedpreferences.R;

import java.io.File;


public class ProfileActivity extends Activity {

    private Contact contact;
    private ImageView contactPhoto;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactEmail;
    private Bitmap imageCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        contact = this.getIntent().getParcelableExtra("Contact");

        getContactInfoViews();
        setContactInfoInTextViews();
    }

    private void getContactInfoViews() {
        contactPhoto = (ImageView) this.findViewById(R.id.photo);
        contactName = (TextView) this.findViewById(R.id.name);
        contactPhone = (TextView) this.findViewById(R.id.phone);
        contactEmail = (TextView) this.findViewById(R.id.email);
    }

    private void setContactInfoInTextViews() {
        setPhoto(contact);
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactEmail.setText(contact.getEmail());
    }

    public void setPhoto(Contact contact) {
        try {
            String imagePath = contact.getImagePath();
            if (imagePath == null || imagePath.length() == 0)
                contactPhoto.setImageResource(contact.getPhoto());
            else {
                if(imageCache != null)
                    imageCache.recycle();
                imageCache = BitmapFactory.decodeFile(imagePath);
                contactPhoto.setImageBitmap(imageCache);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
