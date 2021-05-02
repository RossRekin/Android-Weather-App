package pu.fmi.rainytime.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pu.fmi.rainytime.R;

public class WeatherReportListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<WeatherReport> reports;
    private final int [] images;

    public WeatherReportListAdapter(Context context, ArrayList<WeatherReport> reports, int[] images) {
        this.context = context;
        this.reports = reports;
        this.images = images;
    }

    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_list_item, parent, false);
            viewHolder.degreesTV = (TextView) convertView.findViewById(R.id.degreesTextView);
            viewHolder.weatherTV = (TextView) convertView.findViewById(R.id.weatherTextView);
            viewHolder.dateTV = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.weatherIconIV = (ImageView) convertView.findViewById(R.id.weatherIconImageView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.degreesTV.setText(Double.toString(reports.get(position).getTemp())+ " °C");
        viewHolder.weatherTV.setText(reports.get(position).getWeather());
        viewHolder.dateTV.setText(reports.get(position).getTimestamp());
        viewHolder.weatherIconIV.setImageResource(images[0]);

        return convertView;
    }

    private static class ViewHolder {

        TextView degreesTV;
        TextView weatherTV;
        TextView dateTV;
        ImageView weatherIconIV;

    }
}
