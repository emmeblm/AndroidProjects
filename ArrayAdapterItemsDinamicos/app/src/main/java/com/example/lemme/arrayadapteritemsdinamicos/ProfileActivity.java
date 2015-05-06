package com.example.lemme.arrayadapteritemsdinamicos;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileActivity extends Activity {
    private Contact contact;
    private ImageView contactPhoto;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactAddress;
    private TextView contactCode;

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
        contactAddress = (TextView) this.findViewById(R.id.address);
        contactCode = (TextView) this.findViewById(R.id.code);
    }

    private void setContactInfoInTextViews() {
        contactPhoto.setImageResource(contact.getPhoto());
        contactName.setText(contact.getName());
        contactPhone.setText(contact.getPhone());
        contactAddress.setText(contact.getAddress());
        contactCode.setText(String.valueOf(contact.getCode()));
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
