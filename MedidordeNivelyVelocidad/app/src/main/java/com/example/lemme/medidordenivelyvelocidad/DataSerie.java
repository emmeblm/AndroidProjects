package com.example.lemme.medidordenivelyvelocidad;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lemme on 4/27/15.
 */
public class DataSerie {
    private String name;
    private ArrayList<Float> serie;
    private ArrayList<Float> serieHistory;
    private SimpleXYSeries.ArrayFormat format;
    private LineAndPointFormatter lineAndPointFormatter;

    public DataSerie(SimpleXYSeries.ArrayFormat format, LineAndPointFormatter lineAndPointFormatter, String name) {
        this.format = format;
        this.lineAndPointFormatter = lineAndPointFormatter;
        this.name = name;
        serie = new ArrayList<>();
        serieHistory = new ArrayList<>();
    }

    public void addSensorLecture(float sensorLecture) {
        serie.add(new Float(sensorLecture));
        updateSerieHistory();
        if(serie.size() > Utilities.MAXIMUM_LENGHT_DATA_SERIE_DISPLAYED) {
            serie.remove(0);
        }
    }

    private void updateSerieHistory() {
        serieHistory.add(serie.get(serie.size() - 1));
    }

    public void initializeSerieWithRandomData() {
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            serie.add(new Float(random.nextFloat()));
        }
        serieHistory.addAll(serie);
    }

    public SimpleXYSeries.ArrayFormat getFormat() {
        return format;
    }

    public LineAndPointFormatter getLineAndPointFormatter() {
        return lineAndPointFormatter;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Float> getSerie() {
        return serie;
    }
}
