package com.example.lemme.contactlistsharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ContactListActivity extends Activity {

    TextView usernameIdentifier;
    ListView contactList;
    ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contactList = (ListView) this.findViewById(R.id.contactList);
        usernameIdentifier = (TextView) this.findViewById(R.id.usernameIdentifier);

        setUsernameIdentifierLabel();
        contacts = new ArrayList<>();
        contacts.addAll(Contact.listAll(Contact.class));
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, contacts);
        contactList.setAdapter(adapter);

        setItemListenerToContactList();
    }

    private void setUsernameIdentifierLabel() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String welcomeMessage = getString(R.string.welcome) + sharedPreferences.getString("Username", getString(R.string.default_value));
        usernameIdentifier.setText(welcomeMessage);
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
