package com.example.quakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Report>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();
    private static final String USGS = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private ReportAdapter report_adapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        report_adapter = new ReportAdapter(this, new ArrayList<Report>());
        ListView list_view = findViewById(R.id.list_view);
        list_view.setAdapter(report_adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Report currentReport = report_adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentReport.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
                Toast.makeText(getApplicationContext(),
                                "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });


    }

    @NonNull
    @Override
    public Loader<ArrayList<Report>> onCreateLoader(int id, @Nullable Bundle args) {
       EarthquakeLoader el = new EarthquakeLoader(this);
        return el;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Report>> loader, ArrayList<Report> report) {
        report_adapter.clear();
        if (report != null && !report.isEmpty()) {
            report_adapter.addAll(report);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Report>> loader) {
        report_adapter.clear();
    }

}