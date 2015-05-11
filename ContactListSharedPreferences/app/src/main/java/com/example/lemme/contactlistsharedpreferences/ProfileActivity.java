package com.example.lemme.contactlistsharedpreferences;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lemme.contactlistsharedpreferences.Contact;
import com.example.lemme.contactlistsharedpreferences.R;


public class ProfileActivity extends Activity {

    private Contact contact;
    private ImageView contactPhoto;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactEmail;

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
        contactPhoto.setImageResource(contact.getPhoto());
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactEmail.setText(contact.getEmail());
    }


}
