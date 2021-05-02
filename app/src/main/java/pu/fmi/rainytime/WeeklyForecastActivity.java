package pu.fmi.rainytime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pu.fmi.rainytime.models.WeatherReport;
import pu.fmi.rainytime.models.WeatherReportListAdapter;
import pu.fmi.rainytime.services.WeatherDataService;

public class WeeklyForecastActivity extends AppCompatActivity {
    ListView weeklyForecastLV;
    WeatherReportListAdapter listAdapter;
    int[] images = {R.drawable.weathericontest};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_forecast);
        weeklyForecastLV = findViewById(R.id.weeklyForecastListView);
        String cityName = getIntent().getStringExtra("cityName");
        final WeatherDataService weatherDataService = new WeatherDataService(WeeklyForecastActivity.this);

        weatherDataService.getWeeklyWeather(cityName, new WeatherDataService.WeeklyWeatherResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(WeeklyForecastActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(ArrayList<WeatherReport> reports) {
                //Toast.makeText(WeeklyForecastActivity.this, reports.get(1).getCity().getName()+" "+reports.get(1).getWeather(), Toast.LENGTH_SHORT).show();
                //ArrayAdapter arrayAdapter = new ArrayAdapter(WeeklyForecastActivity.this, android.R.layout.simple_list_item_1,reports);
                //weeklyForecastLV.setAdapter(arrayAdapter);
                weeklyForecastLV = (ListView)findViewById(R.id.weeklyForecastListView);
                listAdapter = new WeatherReportListAdapter(WeeklyForecastActivity.this,reports,images);
                weeklyForecastLV.setAdapter(listAdapter);
            }
        });
    }
}