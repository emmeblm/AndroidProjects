package com.example.lemme.arrayadapteritemsdinamicossqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = (ListView) this.findViewById(R.id.contactList);
        ArrayList<Contact> contacts = getContactsFromDatabase();
        addNewAdapterToContactList(contacts);
    }

    private void addNewAdapterToContactList(ArrayList<Contact> contacts) {
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, contacts);
        contactList.setAdapter(adapter);
    }

    private ArrayList<Contact> getContactsFromDatabase() {
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.addAll(Contact.listAll(Contact.class));
        return contacts;
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
