package com.example.lemme.contactlistsqlite;

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

    private ImageView imageViewPhoto;
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private int photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        imageViewPhoto = (ImageView) this.findViewById(R.id.photo);
        editTextName = (EditText) this.findViewById(R.id.name);
        editTextPhone = (EditText) this.findViewById(R.id.phone);
        editTextEmail = (EditText) this.findViewById(R.id.email);
        photo = R.mipmap.ic_launcher;
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
        imageViewPhoto.setImageResource(photo);
    }

    public void onClickSaveContact(View view) {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

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
        if(name.length() > 0 && phone.length() > 0 && email.length() > 0)
            return  true;
        return false;
    }
}
