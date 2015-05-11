package com.example.lemme.contactlistsqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
