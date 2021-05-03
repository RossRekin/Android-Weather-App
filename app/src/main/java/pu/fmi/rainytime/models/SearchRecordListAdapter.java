package pu.fmi.rainytime.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pu.fmi.rainytime.R;
import pu.fmi.rainytime.SearchHistoryActivity;
import pu.fmi.rainytime.database.DbHelper;

public class SearchRecordListAdapter extends BaseAdapter {

    Context context;
    private final ArrayList<Search> searchRecords;
    DbHelper dbHelper;

    public SearchRecordListAdapter(Context context, ArrayList<Search> searchRecords) {
        this.context = context;
        this.searchRecords = searchRecords;
        dbHelper = new DbHelper(context);
    }

    @Override
    public int getCount() {
        return searchRecords.size();
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


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.single_search_record, parent, false);
            viewHolder.locationTV = (TextView) convertView.findViewById(R.id.searchRecord_locationTextView);
            viewHolder.timestampTV = (TextView) convertView.findViewById(R.id.searchRecord_timestampTextView);
            viewHolder.deleteRecordB = (Button) convertView.findViewById(R.id.deleteRecordButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.locationTV.setText(searchRecords.get(position).getLocation());
        viewHolder.timestampTV.setText(searchRecords.get(position).getTimestamp());
        viewHolder.deleteRecordB.setOnClickListener(v -> {
            dbHelper.deleteSearch(searchRecords.get(position));
            searchRecords.remove(position);
            notifyDataSetChanged();
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView locationTV;
        TextView timestampTV;
        Button deleteRecordB;
    }
}
