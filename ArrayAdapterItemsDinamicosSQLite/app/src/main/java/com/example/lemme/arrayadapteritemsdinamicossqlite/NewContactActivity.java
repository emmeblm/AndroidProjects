package com.example.lemme.arrayadapteritemsdinamicossqlite;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class NewContactActivity extends Activity {

    ImageView contactPhoto;
    EditText contactName;
    EditText contactPhone;
    EditText contactEmail;
    int photo;

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

    }
}
