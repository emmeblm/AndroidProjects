package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.os.Bundle;

import com.androidplot.xy.XYPlot;

public class MedidorVelocidad extends Activity {

    private XYPlot chartSpeedSensorXY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidor_velocidad);
        initializeChart();
    }

    private void initializeChart() {

    }

}
