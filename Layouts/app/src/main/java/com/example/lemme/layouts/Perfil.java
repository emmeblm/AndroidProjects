package com.example.lemme.layouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class Perfil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Bundle bundle = getIntent().getExtras();
        loadNameInTextView(bundle.getString("Name"));
    }

    private void loadNameInTextView(String name) {
        TextView lblName = (TextView) findViewById(R.id.lblName);
        lblName.setText(name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
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

    public void onClickReturnToLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
