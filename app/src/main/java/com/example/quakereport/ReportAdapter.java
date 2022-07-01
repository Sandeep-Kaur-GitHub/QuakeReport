package com.example.quakereport;
import android.app.Activity;

import android.databinding.tool.util.L;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportAdapter extends ArrayAdapter<Report> {
    String first="";
    String second;

    public ReportAdapter(EarthquakeActivity context, ArrayList<Report> report) {
        super(context, 0, report);

    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        Report currentReport = getItem(position);
        TextView mag_textView = (TextView) listItemView.findViewById(R.id.textview_magnitude);
        String dMag=formatMagnitude(currentReport.getMagnitude());
        GradientDrawable magnitudeCircle = (GradientDrawable) mag_textView.getBackground();
        int magnitudeColor = getMagnitudeColor((int) currentReport.getMagnitude());
        mag_textView.setText(dMag);
        magnitudeCircle.setColor(magnitudeColor);
        TextView firstloc_textView = (TextView) listItemView.findViewById(R.id.textview_locationFirst);
        TextView secondloc_textView = (TextView) listItemView.findViewById(R.id.textview_locationSecond);
        String div=currentReport.getLocation();
        if(div.contains("of")){
            String[] separated=div.split("of");
             first= separated[0]+"of";
             second= separated[1];
        }else
        {
             first="Near by";
            second= div;
        }

        firstloc_textView.setText(first);
        secondloc_textView.setText(second);

        TextView date_textView = (TextView) listItemView.findViewById(R.id.textview_date);
        TextView time_textView = (TextView) listItemView.findViewById(R.id.textview_time);
        Date dateObject = new Date(currentReport.getTime());
        String formattedDate = formatDate(dateObject);
        date_textView.setText(formattedDate);
        String formattedTime = formatTime(dateObject);
        time_textView.setText(formattedTime);
        return listItemView;
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}