
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
    private static final String USGS ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    ReportAdapter report_adapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        report_adapter = new ReportAdapter(this,  new ArrayList<Report>());
        ListView list_view = findViewById(R.id.list_view);
        list_view.setAdapter(report_adapter);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);



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
        return new EarthquakeLoader(this);
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

    public static ArrayList<Report> extractFeatureFromJson(String reportJson){
        ArrayList<Report> array_list= new ArrayList<Report>();
        try {
            JSONObject root = new JSONObject(reportJson);
            JSONArray features= root.getJSONArray("features");
            for (int i=0;i<features.length();i++)
            {
                JSONObject first_object=features.getJSONObject(i);
                JSONObject param= first_object.getJSONObject("properties");
                Double magnitude =param.getDouble("mag");
                String place= param.getString("place");
                Long time=param.getLong("time");
                String loc= param.getString("url");
                Report report =new Report(magnitude,place,time,loc);
                array_list.add(report);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array_list;
    }
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }
        return url;
    }

   public static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream=urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);


            if(urlConnection.getResponseCode()==200)
            {
                Log.i("kiddaa","hogeya");
            }


        }catch (IOException e){

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }
public static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output= new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader= new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader= new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line !=null){
                output.append(line);
                line =reader.readLine();
            }
        }
        String h=output.toString();
        Log.i("hawaaa","hanji"+h);
        return output.toString();
    }
}

