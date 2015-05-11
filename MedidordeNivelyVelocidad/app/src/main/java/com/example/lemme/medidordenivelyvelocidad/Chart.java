package com.example.lemme.medidordenivelyvelocidad;

import android.content.Intent;
import android.view.View;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import java.util.HashMap;

/**
 * Created by lemme on 5/8/15.
 */
public class Chart {

    private HashMap<String, Integer> serieOptions;
    private XYPlot chartView;
    private DataSerie serie;
    private String serieName;
    private SimpleXYSeries chartSerie;

    public Chart(String serieName, HashMap<String, Integer> serieOptions, View view) {
        this.serieName = serieName;
        this.serieOptions = serieOptions;
        serie = initializeSpeedSerie();
        chartView = (XYPlot) view;
        updateChart();
    }

    private DataSerie initializeSpeedSerie() {
        int lineColor = serieOptions.get("Line Color");
        int pointColor = serieOptions.get("Point Color");
        int fillColor = serieOptions.get("Fill Color");
        LineAndPointFormatter serieFormat = new LineAndPointFormatter(lineColor, pointColor, fillColor , new PointLabelFormatter(0));
        return new DataSerie(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, serieFormat, serieName);
    }

    public void updateChart() {
        chartView.removeSeries(chartSerie);
        chartSerie = new SimpleXYSeries(serie.getSerie(), serie.getFormat(), serie.getName());
        chartView.addSeries(chartSerie, serie.getLineAndPointFormatter());
        chartView.setDomainStepValue(serieOptions.get("Sampling Step"));
        chartView.setRangeBoundaries(serieOptions.get("Min Y-Axis Value"), serieOptions.get("Max Y-Axis Value"), BoundaryMode.FIXED);

        chartView.redraw();
    }

    public DataSerie getSerie() {
        return serie;
    }

    public String getSerieName() {
        return serieName;
    }
}
