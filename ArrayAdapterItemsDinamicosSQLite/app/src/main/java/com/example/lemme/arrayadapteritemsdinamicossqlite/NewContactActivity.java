package com.example.lemme.arrayadapteritemsdinamicossqlite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class NewContactActivity extends Activity {

    private ImageView contactPhoto;
    private EditText contactName;
    private EditText contactPhone;
    private EditText contactEmail;
    private int photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        contactPhoto = (ImageView) this.findViewById(R.id.photo);
        contactName = (EditText) this.findViewById(R.id.name);
        contactPhone = (EditText) this.findViewById(R.id.phone);
        contactEmail = (EditText) this.findViewById(R.id.email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_contact, menu);
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

    public void onClickChangePhoto(View view) {
        switch (view.getId()) {
            case R.id.photoOption1:
                photo = R.drawable.contact1;
                break;
            case R.id.photoOption2:
                photo = R.drawable.contact2;
                break;
            case R.id.photoOption3:
                photo = R.drawable.contact3;
                break;
            default:
                break;
        }
        contactPhoto.setImageResource(photo);
    }

    public void onClickSaveContact(View view) {
        String name = contactName.getText().toString();
        String phone = contactPhone.getText().toString();
        String email = contactEmail.getText().toString();

        saveContact(name, phone, email);
    }

    private void saveContact(String name, String phone, String email) {
        if (validateContactDataBeforeSaving(name, phone, email)) {
            Contact contact = new Contact(photo, name, phone, email);
            contact.save();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, getString(R.string.fields_validation_error_message), Toast.LENGTH_SHORT);
        }
    }

    private boolean validateContactDataBeforeSaving(String name, String phone, String email) {
        if(name.length() > 0 && phone.length() > 0 && email.length() > 0 && photo != 0)
            return  true;
        return false;
    }
}
