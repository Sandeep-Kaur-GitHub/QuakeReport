package com.example.quakereport;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
public class ReportAdapter extends ArrayAdapter<Report> {
    Context context;
    public ReportAdapter(Activity context, ArrayList<Report> report) {
        super(context, 0, report);
        this.context = context;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false);
        }
        Report currentReport = getItem(position);
        TextView mag_textView = (TextView) listItemView.findViewById(R.id.textview_magnitude);
        mag_textView.setText(String.valueOf(currentReport.getMagnitude()));
        TextView loc_textView = (TextView) listItemView.findViewById(R.id.textview_location);
        loc_textView.setText(currentReport.getLocation());
        TextView time_textView = (TextView) listItemView.findViewById(R.id.textview_time);
        time_textView.setText(currentReport.getTime());
        return listItemView;
    }
}