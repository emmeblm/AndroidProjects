package com.example.lemme.medidordenivelyvelocidad;

import android.view.View;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import java.util.HashMap;

/**
 * Created by lemme on 5/8/15.
 */
public class Chart {

    private XYPlot chartView;
    private DataSerie serie;
    private SimpleXYSeries chartSerie;

    public Chart(String serieName, HashMap<String, Integer> serieColors,  View view) {
        serie = initializeSpeedSerie(serieName, serieColors);
        chartView = (XYPlot) view;
        updateChart();
    }

    private DataSerie initializeSpeedSerie(String serieName, HashMap<String, Integer> serieColors) {
        int lineColor = serieColors.get("Line");
        int pointColor = serieColors.get("Point");
        int fillColor = serieColors.get("Fill");
        LineAndPointFormatter serieFormat = new LineAndPointFormatter(lineColor, pointColor, fillColor , new PointLabelFormatter(0));
        return new DataSerie(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, serieFormat, serieName);
    }

    public void updateChart() {
        chartView.removeSeries(chartSerie);
        chartSerie = new SimpleXYSeries(serie.getSerie(), serie.getFormat(), serie.getName());
        chartView.addSeries(chartSerie, serie.getLineAndPointFormatter());
        chartView.redraw();
    }

    public DataSerie getSerie() {
        return serie;
    }
}
