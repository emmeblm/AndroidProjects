package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStartActivitySpeedometer(View view) {
        System.out.println("Click!");
        Intent intentProfile = new Intent(this, MedidorVelocidad.class);
        startActivity(intentProfile);
    }
}
