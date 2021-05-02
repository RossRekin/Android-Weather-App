package pu.fmi.rainytime.models;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pu.fmi.rainytime.R;
import pu.fmi.rainytime.WeeklyForecastActivity;
import pu.fmi.rainytime.services.WeatherDataService;

public class HourlyForecastActivity extends AppCompatActivity {

    ListView hourlyForecastLV;
    WeatherReportListAdapter listAdapter;
    int[] images = {R.drawable.weathericontest};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        hourlyForecastLV = findViewById(R.id.hourlyForecastListView);
        String cityName = getIntent().getStringExtra("cityName");
        final WeatherDataService weatherDataService = new WeatherDataService(HourlyForecastActivity.this);

        weatherDataService.getHourlyWeather(cityName, new WeatherDataService.PeriodicWeatherForecastResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(HourlyForecastActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<WeatherReport> reports) {
//                Toast.makeText(HourlyForecastActivity.this, reports.get(1).getCity().getName()+" "+reports.get(1).getWeather(), Toast.LENGTH_SHORT).show();
//                ArrayAdapter arrayAdapter = new ArrayAdapter(HourlyForecastActivity.this, android.R.layout.simple_list_item_1,reports);
//                hourlyForecastLV.setAdapter(arrayAdapter);
                hourlyForecastLV = (ListView) findViewById(R.id.hourlyForecastListView);
                listAdapter = new WeatherReportListAdapter(HourlyForecastActivity.this, reports, images);
                hourlyForecastLV.setAdapter(listAdapter);
            }
        });
    }
}