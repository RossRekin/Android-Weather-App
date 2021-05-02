package pu.fmi.rainytime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pu.fmi.rainytime.models.City;
import pu.fmi.rainytime.models.HourlyForecastActivity;
import pu.fmi.rainytime.models.WeatherReport;
import pu.fmi.rainytime.services.WeatherDataService;

public class MainActivity extends AppCompatActivity {

    EditText cityET;
    TextView locationTV,temperatureTV,forecastTV,dateTV;
    Button dailyForecastB,weeklyForecastB,showForecastB,checKHistoryB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityET=findViewById(R.id.cityEditText);
        locationTV=findViewById(R.id.locationTextView);
        temperatureTV=findViewById(R.id.temperatureTextView);
        forecastTV=findViewById(R.id.forecastTextView);
        dateTV=findViewById(R.id.dateTextView);
        dailyForecastB=findViewById(R.id.dailyForecastButton);
        checKHistoryB=findViewById(R.id.checkHistoryButton);
        weeklyForecastB=findViewById(R.id.weekForecastButton);
        showForecastB=findViewById(R.id.showForecastButton);

        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        showForecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityET.getText().toString();

                weatherDataService.getCurrentWeather(cityName, new WeatherDataService.CurrentWeatherResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(WeatherReport report) {
                        locationTV.setText(report.getCity().getName());
                        temperatureTV.setText(String.valueOf(report.getTemp()+" °C"));
                        forecastTV.setText(report.getWeather());
                        dateTV.setText(report.getTimestamp());
                    }
                });
            }
        });

        // TODO
        dailyForecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = locationTV.getText().toString();
                Intent hourlyForecastIntent = new Intent(MainActivity.this, HourlyForecastActivity.class);
                hourlyForecastIntent.putExtra("cityName",cityName);
                startActivity(hourlyForecastIntent);
            }
        });

        weeklyForecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = locationTV.getText().toString();
                Intent weeklyForecastIntent = new Intent(MainActivity.this, WeeklyForecastActivity.class);
                weeklyForecastIntent.putExtra("cityName",cityName);
                startActivity(weeklyForecastIntent);
            }
        });

        // TODO
        checKHistoryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "check history test", Toast.LENGTH_SHORT).show();
            }
        });





    }
}