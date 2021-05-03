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

public class WeeklyForecastActivity extends AppCompatActivity {
    ListView weeklyForecastLV;
    WeatherReportListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_forecast);
        weeklyForecastLV = findViewById(R.id.weeklyForecastListView);
        String cityName = getIntent().getStringExtra("cityName");
        final WeatherDataService weatherDataService = new WeatherDataService(WeeklyForecastActivity.this);

        weatherDataService.getPeriodicWeather(cityName, new WeatherDataService.PeriodicWeatherForecastResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(WeeklyForecastActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(ArrayList<WeatherReport> reports) {
                weeklyForecastLV = (ListView)findViewById(R.id.weeklyForecastListView);
                listAdapter = new WeatherReportListAdapter(WeeklyForecastActivity.this,reports);
                weeklyForecastLV.setAdapter(listAdapter);
            }
        }, Period.WEEKLY);
    }
}