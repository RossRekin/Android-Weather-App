package pu.fmi.rainytime.services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import pu.fmi.rainytime.models.City;
import pu.fmi.rainytime.models.VolleySingleton;
import pu.fmi.rainytime.models.WeatherReport;


public class WeatherDataService {
    Context context;
    private static final String ACCESS_TOKEN = "";

    public WeatherDataService(Context context) {
        this.context = context;
    }

    // Used for callback
    public interface CityCoordsResponse {
        void onError(String message);

        void onResponse(City city);
    }

    public interface CurrentWeatherResponse {
        void onError(String message);

        void onResponse(WeatherReport weatherReport);
    }

    public interface WeeklyWeatherResponse {
        void onError(String message);

        void onResponse(ArrayList<WeatherReport> reports);
    }


    public void getCityCoords(String cityName, CityCoordsResponse cityCoordsResponse) {
        City[] city = new City[1];
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + ACCESS_TOKEN;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            String lon, lat = "";

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject coord = response.getJSONObject("coord");
                    lat = coord.getString("lat");
                    lon = coord.getString("lon");
                    city[0] = new City(cityName, lat, lon);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cityCoordsResponse.onResponse(city[0]);
            }
        }, error -> {
            cityCoordsResponse.onError("Something went wrong.");
        });

        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

    public void getCurrentWeather(String cityName, CurrentWeatherResponse currentWeatherResponse) {
        WeatherReport weatherReport = new WeatherReport();

        this.getCityCoords(cityName, new CityCoordsResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(City responseCity) {
                String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + responseCity.getLatitude() + "&lon=" + responseCity.getLongitude() + "&appid=" + ACCESS_TOKEN;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    String timestamp, weather = "";
                    double temperature = 0;
                    Integer timezone = 0;

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject currentWeatherData = response.getJSONObject("current");
                            temperature = currentWeatherData.getDouble("temp") - 272.15;
                            temperature = Math.round(temperature*100.0)/100.0;
                            weather = currentWeatherData.getJSONArray("weather").getJSONObject(0).getString("main");
                            timezone = response.getInt("timezone_offset");
                            timestamp = calculateTime(timezone);
                            weatherReport.setCity(responseCity);
                            weatherReport.setTemp(temperature);
                            weatherReport.setTimestamp(timestamp);
                            weatherReport.setWeather(weather);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        currentWeatherResponse.onResponse(weatherReport);
                    }
                }, error -> {
                    currentWeatherResponse.onError("Something went wrong.");
                });

                VolleySingleton.getInstance(context).addToRequestQueue(request);
            }
        });
    }

    public void getWeeklyWeather(String cityName, WeeklyWeatherResponse weeklyWeatherResponse) {
        ArrayList<WeatherReport> reports = new ArrayList<WeatherReport>();

        this.getCityCoords(cityName, new CityCoordsResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(City responseCity) {
                String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + responseCity.getLatitude() + "&lon=" + responseCity.getLongitude() + "&appid=" + ACCESS_TOKEN;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    String timestamp, weather = "";
                    double temperature = 0;
                    long dt = 0;
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weeklyWeatherData = response.getJSONArray("daily");
                            for (int i = 0; i <= 8; i++) {
                                JSONObject currentDay = weeklyWeatherData.getJSONObject(i);
                                temperature = currentDay.getJSONObject("temp").getDouble("day");
                                temperature = Math.round(temperature*100.0)/100.0;
                                weather = currentDay.getJSONArray("weather").getJSONObject(0).getString("main");
                                dt = currentDay.getLong("dt");
                                timestamp = calculateDateFromUnixTime(dt);
                                WeatherReport currentReport = new WeatherReport(responseCity,weather,temperature,timestamp);
                                reports.add(currentReport);
                            }
                            //Toast.makeText(context, weeklyWeatherData.getJSONObject(2).getString("dt"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        weeklyWeatherResponse.onResponse(reports);
                    }
                }, error -> {
                    weeklyWeatherResponse.onError("Something went wrong.");
                });

                VolleySingleton.getInstance(context).addToRequestQueue(request);
            }
        });
    }

    private String calculateTime(int timezoneOffset) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.SECOND, timezoneOffset);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String result = sdf.format(calendar.getTime());
        return result;
    }
    private String calculateDateFromUnixTime(long unixTime){
        long unixSeconds = 1619780400;
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
