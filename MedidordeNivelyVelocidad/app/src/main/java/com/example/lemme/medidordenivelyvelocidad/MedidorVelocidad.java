package com.example.lemme.medidordenivelyvelocidad;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import java.util.Random;

public class MedidorVelocidad extends Activity {
    private XYPlot speedChartView;
    private DataSerie speedSerie;
    private SimpleXYSeries speedChartSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medidor_velocidad);
        initializeChart();
    }

    private void initializeChart() {
        speedSerie = initializeSpeedSerie();
        speedSerie.initializeSerie();

        updateChart();
    }

    private DataSerie initializeSpeedSerie() {
        String serieName = "Velocidad";
        int lineColor = Color.rgb(0, 200, 0);
        int pointColor = Color.rgb(0, 100, 0);
        int fillColor = Color.rgb(150, 190, 150);
        LineAndPointFormatter serieFormat = new LineAndPointFormatter(lineColor, pointColor, fillColor , new PointLabelFormatter(0));
        return new DataSerie(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, serieFormat, serieName);
    }

    private void updateChart() {
        speedChartView = (XYPlot) findViewById(R.id.chartSpeed);
        speedChartSerie = new SimpleXYSeries(speedSerie.getSerie(), speedSerie.getFormat(), speedSerie.getName());
        speedChartView.getSeriesSet().clear();
        speedChartView.addSeries(speedChartSerie, speedSerie.getLineAndPointFormatter());
        speedChartView.redraw();
    }

    public void onClickAddSensorLecture(View view) {
        Random random = new Random();
        speedSerie.addSensorLecture(random.nextFloat());
        updateChart();
    }
}
