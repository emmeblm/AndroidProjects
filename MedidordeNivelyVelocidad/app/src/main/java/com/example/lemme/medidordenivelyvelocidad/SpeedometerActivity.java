package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class SpeedometerActivity extends Activity {

    Chart speedChart;
    private ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedometer);
        initializeChart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createDataStreamToTalkToTheServer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(connectedThread != null)
            connectedThread.terminate();
    }

    private void createDataStreamToTalkToTheServer() {
        connectedThread = new ConnectedThread(Utilities.bluetoothSocket, speedChart);
        connectedThread.start();
        Log.d(Utilities.TAG, "Listening ... ");
    }

    private void initializeChart() {
        HashMap<String, Integer> serieColors = new HashMap<>();
        serieColors.put("Line", new Integer(Color.rgb(0, 200, 0)));
        serieColors.put("Point", new Integer(Color.rgb(0, 100, 0)));
        serieColors.put("Fill", new Integer(Color.rgb(150, 190, 150)));

        speedChart = new Chart(getString(R.string.speed_serie_name), serieColors, findViewById(R.id.chartSpeed));
    }
}
