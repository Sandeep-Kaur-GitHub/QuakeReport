package com.example.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        ArrayList<Report> array_list= new ArrayList<Report>();
        array_list.add(new Report(2.3,"USA",344));
        array_list.add(new Report(2.3,"USA",344));
        array_list.add(new Report(2.3,"USA",344));
        ReportAdapter report_adapter = new ReportAdapter(this, array_list);
        ListView list_view = findViewById(R.id.list_view);
        list_view.setAdapter(report_adapter);
    }
}