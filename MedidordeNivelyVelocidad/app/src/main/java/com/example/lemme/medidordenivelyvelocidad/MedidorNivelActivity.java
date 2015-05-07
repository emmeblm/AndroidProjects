package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.os.Bundle;


public class MedidorNivelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidor_nivel);
        initializeChart();
    }

    private void initializeChart() {
        //chartLevelMeasurement = new SensorReadingChartXYPlotImpl(findViewById(R.id.chartLevel));
    }
}
