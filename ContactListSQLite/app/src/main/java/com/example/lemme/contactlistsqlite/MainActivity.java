package com.example.lemme.contactlistsqlite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ListView contactList;
    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = (ListView) this.findViewById(R.id.contactList);

        contacts = new ArrayList<>();
        contacts.addAll(Contact.listAll(Contact.class));
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, contacts);
        contactList.setAdapter(adapter);

        setItemListenerToContactList();
    }

    private void setItemListenerToContactList() {
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contacts.get(position);
                Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                intentProfile.putExtra("Contact", contact);
                startActivity(intentProfile);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClickAddContact(View view) {
        startActivity(new Intent(this, NewContactActivity.class));
        finish();
    }
}
