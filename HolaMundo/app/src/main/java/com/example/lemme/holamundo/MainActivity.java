package com.example.lemme.holamundo;

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

    public void onClickSegundoActivity(View vista) {
        System.out.println("Click botón cambio segundo activity");
        startActivity(new Intent(this, SegundoActivity.class));
    }

    public void onClickTercerActivity(View vista) {
        System.out.println("Click botón cambio tercer activity");
        startActivity(new Intent(this, TercerActivity.class));
    }

    public void onClickCuartoActivity(View vista) {
        System.out.println("Click botón cambio cuarto activity");
        startActivity(new Intent(this, CuartoActivity.class));
    }

}
