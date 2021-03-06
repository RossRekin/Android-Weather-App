package pu.fmi.rainytime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pu.fmi.rainytime.database.DbHelper;
import pu.fmi.rainytime.models.IconPicker;
import pu.fmi.rainytime.models.WeatherReport;
import pu.fmi.rainytime.services.WeatherDataService;

public class MainActivity extends AppCompatActivity {

    EditText cityET;
    TextView locationTV, temperatureTV, forecastTV, dateTV;
    Button dailyForecastB, weeklyForecastB, showForecastB, checkHistoryB;
    ImageView forecastIV;
    final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
    final IconPicker iconPicker = new IconPicker();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityET = findViewById(R.id.cityEditText);
        locationTV = findViewById(R.id.locationTextView);
        temperatureTV = findViewById(R.id.temperatureTextView);
        forecastTV = findViewById(R.id.forecastTextView);
        dateTV = findViewById(R.id.dateTextView);
        dailyForecastB = findViewById(R.id.dailyForecastButton);
        checkHistoryB = findViewById(R.id.checkHistoryButton);
        weeklyForecastB = findViewById(R.id.weekForecastButton);
        showForecastB = findViewById(R.id.showForecastButton);
        forecastIV = findViewById(R.id.forecastImageView);
        getCurrentWeather("Sofia");


        showForecastB.setOnClickListener(v -> {
            String cityName = cityET.getText().toString();
            if (cityName.isEmpty()){
                Toast.makeText(this, "Please Enter a Location", Toast.LENGTH_SHORT).show();
            }else {
                getCurrentWeather(cityName);
            }
            });

        dailyForecastB.setOnClickListener(v -> {
            String cityName = locationTV.getText().toString();
            Intent hourlyForecastIntent = new Intent(MainActivity.this, HourlyForecastActivity.class);
            hourlyForecastIntent.putExtra("cityName", cityName);
            startActivity(hourlyForecastIntent);
        });

        weeklyForecastB.setOnClickListener(v -> {
            String cityName = locationTV.getText().toString();
            Intent weeklyForecastIntent = new Intent(MainActivity.this, WeeklyForecastActivity.class);
            weeklyForecastIntent.putExtra("cityName", cityName);
            startActivity(weeklyForecastIntent);
        });

        // TODO
        checkHistoryB.setOnClickListener(v -> {
            Intent seachHistoryIntent = new Intent(MainActivity.this, SearchHistoryActivity.class);
            startActivity(seachHistoryIntent);
        });
    }

    private void fillDataFields(WeatherReport report) {
        locationTV.setText(report.getCity().getName());
        temperatureTV.setText(String.valueOf(report.getTemp() + " ??C"));
        forecastTV.setText(report.getWeather());
        dateTV.setText(report.getTimestamp());
        int image = iconPicker.pickIcon(report.getWeather(),report.getTimestamp());
        forecastIV.setImageResource(image);
    }

    private void getCurrentWeather(String cityName) {
        weatherDataService.getCurrentWeather(cityName, new WeatherDataService.CurrentWeatherResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(WeatherReport report) {
                fillDataFields(report);
            }
        });
    }

}