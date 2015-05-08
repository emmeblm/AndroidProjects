package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import java.util.Random;

public class MedidorVelocidadActivity extends Activity {
    private XYPlot speedChartView;
    private DataSerie speedSerie;
    private SimpleXYSeries speedChartSerie;
    private ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidor_velocidad);
        createDataStreamToTalkToTheServer();
        initializeChart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(connectedThread != null)
            connectedThread.terminate();
    }

    private void createDataStreamToTalkToTheServer() {
        connectedThread = new ConnectedThread(Utilities.bluetoothSocket);
        connectedThread.start();
        Log.d(Utilities.TAG, "Listening ... ");
    }

    private void initializeChart() {
        speedSerie = initializeSpeedSerie();
//        speedSerie.initializeSerieWithRandomData();

//        BluetoothSocket bs =

        onClickAddSensorLecture(speedChartView);
        updateChart();
    }

    private DataSerie initializeSpeedSerie() {
        String serieName = getString(R.string.speed_serie_name);
        int lineColor = Color.rgb(0, 200, 0);
        int pointColor = Color.rgb(0, 100, 0);
        int fillColor = Color.rgb(150, 190, 150);
        LineAndPointFormatter serieFormat = new LineAndPointFormatter(lineColor, pointColor, fillColor , new PointLabelFormatter(0));
        return new DataSerie(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, serieFormat, serieName);
    }

    private void updateChart() {
        speedChartView = (XYPlot) findViewById(R.id.chartSpeed);
        speedChartView.removeSeries(speedChartSerie);
        speedChartSerie = new SimpleXYSeries(speedSerie.getSerie(), speedSerie.getFormat(), speedSerie.getName());
        speedChartView.addSeries(speedChartSerie, speedSerie.getLineAndPointFormatter());
        speedChartView.redraw();
    }

    public void onClickAddSensorLecture(View view) {

        Random random = new Random();
        speedSerie.addSensorLecture(random.nextFloat());
        updateChart();
    }
}
