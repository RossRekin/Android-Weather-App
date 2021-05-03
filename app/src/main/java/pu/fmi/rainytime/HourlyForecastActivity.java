package pu.fmi.rainytime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pu.fmi.rainytime.enums.Period;
import pu.fmi.rainytime.models.WeatherReport;
import pu.fmi.rainytime.models.WeatherReportListAdapter;
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

        weatherDataService.getPeriodicWeather(cityName, new WeatherDataService.PeriodicWeatherForecastResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(HourlyForecastActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayList<WeatherReport> reports) {
                hourlyForecastLV = (ListView) findViewById(R.id.hourlyForecastListView);
                listAdapter = new WeatherReportListAdapter(HourlyForecastActivity.this, reports, images);
                hourlyForecastLV.setAdapter(listAdapter);
            }
        }, Period.HOURLY);
    }
}