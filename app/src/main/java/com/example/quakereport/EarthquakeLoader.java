package com.example.quakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Report>> {
    private static final String USGS ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public EarthquakeLoader(@NonNull Context context) {
        super(context);
    }
    protected void onStartLoading() {
        forceLoad();
    }
    @Nullable
    @Override
    public ArrayList<Report> loadInBackground() {
        URL url = EarthquakeActivity.createUrl(USGS);
        String jsonResponse = "";
        try {
            jsonResponse = EarthquakeActivity.makeHttpRequest(url);
        } catch (IOException e) {

        }
        ArrayList<Report> report= EarthquakeActivity.extractFeatureFromJson(jsonResponse);
        return report;
    }
}
