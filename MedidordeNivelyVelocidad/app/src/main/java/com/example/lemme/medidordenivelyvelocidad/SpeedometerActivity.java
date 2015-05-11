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
        Log.d(Utilities.TAG, "... Listening ... ");
    }

    private void initializeChart() {
        HashMap<String, Integer> serieOptions = new HashMap<>();
        serieOptions.put("Line Color", new Integer(Color.rgb(0, 200, 0)));
        serieOptions.put("Point Color", new Integer(Color.rgb(0, 100, 0)));
        serieOptions.put("Fill Color", new Integer(Color.rgb(150, 190, 150)));
        serieOptions.put("Sampling Step", Utilities.SENSOR_SAMPLING_STEP);
        serieOptions.put("Min Y-Axis Value", Utilities.MIN_Y_AXIS_VALUE_SPEEDOMETER);
        serieOptions.put("Max Y-Axis Value", Utilities.MAX_Y_AXIS_VALUE_SPEEDOMETER);

        speedChart = new Chart(getString(R.string.speed_serie_name), serieOptions, findViewById(R.id.chartSpeed));
    }
}
