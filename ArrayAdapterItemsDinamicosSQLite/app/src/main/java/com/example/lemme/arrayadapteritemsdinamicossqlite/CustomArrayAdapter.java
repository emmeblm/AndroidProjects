package com.example.lemme.arrayadapteritemsdinamicossqlite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lemme on 5/5/15.
 */
public class CustomArrayAdapter extends ArrayAdapter<Contact> {

    private final Activity context;
    private ArrayList<Contact> contactList;

    CustomArrayAdapter(Activity context, ArrayList<Contact> contactList) {
        super(context, R.layout.rowlist, contactList);
        this.context = context;
        this.contactList = contactList;
    }
}
