package pu.fmi.rainytime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pu.fmi.rainytime.models.City;
import pu.fmi.rainytime.models.VolleySingleton;
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


                weatherDataService.getCity(cityName, new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(City city) {
                        Toast.makeText(MainActivity.this, city.getLatitude(), Toast.LENGTH_SHORT).show();
                    }
                });
                //City city = weatherDataService.getCity(cityName);
                //

            }
        });

        // TODO
        dailyForecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "daily forecast test", Toast.LENGTH_SHORT).show();
            }
        });

        // TODO
        weeklyForecastB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "weekly forecast test", Toast.LENGTH_SHORT).show();
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