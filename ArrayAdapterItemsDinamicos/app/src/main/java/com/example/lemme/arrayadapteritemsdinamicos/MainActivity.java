package com.example.lemme.arrayadapteritemsdinamicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ListView contactList;
    private ArrayList<Contact> contactArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeContactList();
        setItemListenerToContactList();
    }

    private void initializeContactList() {
        contactList = (ListView) findViewById(R.id.contactList);
        contactArrayList = createListOfContacts();
        CustomArrayAdapter customAdapter = new CustomArrayAdapter(this, contactArrayList);
        contactList.setAdapter(customAdapter);
    }

    private ArrayList<Contact> createListOfContacts() {
        final int contact1Code = 708932;
        final int contact2Code = 129835;
        final int contact3Code = 500027;

        ArrayList<Contact> contactArrayList = new ArrayList<>() ;
        contactArrayList.add(new Contact(R.drawable.contact1,
                getString(R.string.contact_1_name),
                getString(R.string.contact_1_phone),
                getString(R.string.contact_1_address),
                contact1Code));
        contactArrayList.add(new Contact(R.drawable.contact2,
                getString(R.string.contact_2_name),
                getString(R.string.contact_2_phone),
                getString(R.string.contact_2_address),
                contact2Code));
        contactArrayList.add(new Contact(R.drawable.contact3,
                getString(R.string.contact_3_name),
                getString(R.string.contact_3_phone),
                getString(R.string.contact_3_address),
                contact3Code));

        return contactArrayList;
    }

    private void setItemListenerToContactList() {
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactArrayList.get(position);
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
}
