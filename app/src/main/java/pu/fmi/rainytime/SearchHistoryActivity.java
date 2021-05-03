package pu.fmi.rainytime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pu.fmi.rainytime.database.DbHelper;
import pu.fmi.rainytime.models.Search;
import pu.fmi.rainytime.models.SearchRecordListAdapter;

public class SearchHistoryActivity extends AppCompatActivity {

    ListView searchRecordsLV;
    SearchRecordListAdapter listAdapter;
    Button clearB;
    DbHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        dbHelper = new DbHelper(this);
        searchRecordsLV = findViewById(R.id.searchRecordsListView);
        clearB = findViewById(R.id.clearHistoryButton);
        ArrayList<Search> searchRecords = dbHelper.getSearchRecords();

        if (searchRecords.size()>0){
            listAdapter = new SearchRecordListAdapter(SearchHistoryActivity.this,searchRecords);
            searchRecordsLV.setAdapter(listAdapter);

        }else{
            Toast.makeText(this,"There aren't any search records", Toast.LENGTH_LONG).show();
        }

        clearB.setOnClickListener(v -> {
            dbHelper.deleteAllSearchRecords();
            finish();
            startActivity(getIntent());
        });

    }
}