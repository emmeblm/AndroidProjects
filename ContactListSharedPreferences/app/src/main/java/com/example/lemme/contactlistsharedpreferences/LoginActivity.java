package com.example.lemme.contactlistsharedpreferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(userInfoSharedPreferencesFileExists())
            goToMainActivity();
        else {
            username = (EditText) this.findViewById(R.id.username);
            password = (EditText) this.findViewById(R.id.password);
        }
    }

    private boolean userInfoSharedPreferencesFileExists() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("Username", getString(R.string.default_value)) == getString(R.string.default_value))
            return false;
        return true;
    }

    private void goToMainActivity() {
        startActivity(new Intent(getApplicationContext(), ContactListActivity.class));
        finish();
    }

    public void onClickLogin(View view) {
        if(validateUsernameAndPasswordFields()){
            saveUserDataInSharedPreferences();
            goToMainActivity();
        } else
            Toast.makeText(this, getString(R.string.fields_validation_error_message), Toast.LENGTH_SHORT);
    }

    private boolean validateUsernameAndPasswordFields() {
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        if(username.length() > 0 && password.length() > 0)
            return true;
        return false;
    }

    private void saveUserDataInSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username.getText().toString());
        editor.putString("Password", password.getText().toString());
        editor.commit();
    }
}
