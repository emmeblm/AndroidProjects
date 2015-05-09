package com.example.lemme.arrayadapteritemsdinamicossqlite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.lemme.arrayadapteritemsdinamicossqlite.Contact;
import com.example.lemme.arrayadapteritemsdinamicossqlite.R;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if(rowView == null)
            rowView = createNewRowView();

        Contact contact = contactList.get(position);
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        viewHolder.setContactInfoOnEachView(contact);

        return rowView;
    }

    private View createNewRowView() {
        LayoutInflater inflater = context.getLayoutInflater();
        View newRowView = inflater.inflate(R.layout.rowlist, null);
        final ViewHolder viewHolder = createContactViewHolder(newRowView);
        newRowView.setTag(viewHolder);

        return newRowView;
    }

    private ViewHolder createContactViewHolder(View rowView) {
        HashMap<String, View> contactInfoViews = new HashMap<>();
        contactInfoViews.put("Photo", rowView.findViewById(R.id.contactPhoto));
        contactInfoViews.put("Name", rowView.findViewById(R.id.contactName));
        contactInfoViews.put("Phone", rowView.findViewById(R.id.contactPhone));
        contactInfoViews.put("Address", rowView.findViewById(R.id.contactAddress));
        contactInfoViews.put("Code", rowView.findViewById(R.id.contactCode));

        ViewHolder viewHolder = new ViewHolder(contactInfoViews);

        return viewHolder;
    }
}
