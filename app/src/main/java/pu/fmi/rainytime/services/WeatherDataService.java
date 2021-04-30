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
import java.util.Calendar;
import java.util.TimeZone;

import pu.fmi.rainytime.models.City;
import pu.fmi.rainytime.models.VolleySingleton;
import pu.fmi.rainytime.models.WeatherReport;


public class WeatherDataService {
    Context context;


    public WeatherDataService(Context context) {
        this.context = context;
    }

    // Used for callback
    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(WeatherReport report);
    }


    public void getCity(String cityName,VolleyResponseListener volleyResponseListener) {
        City[] city = new City[1];
        WeatherReport[] report = new WeatherReport[1];
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=168bd05ec573baa40ca07ac0fa585cfb";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            String lon,lat,weather,timestamp = "";
            double temperature = 0;
            Integer timezone = 0;
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject coord = response.getJSONObject("coord");
                    JSONObject weatherObject = response.getJSONArray("weather").getJSONObject(0);
                    JSONObject temperatureObject = response.getJSONObject("main");
                    lat = coord.getString("lat");
                    lon = coord.getString("lon");
                    weather= weatherObject.getString("main");
                    temperature = temperatureObject.getDouble("temp")- 272.15;
                    city[0] = new City(cityName,lat,lon);
                    timezone = response.getInt("timezone");
                    timestamp = calculateTime(timezone);
                    report[0] = new WeatherReport(city[0],weather,temperature,timestamp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(report[0]);
            }
        }, error -> {
            //Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
            volleyResponseListener.onError("Something went wrong.");
        });


        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }







    public String calculateTime(int timezoneShift){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.SECOND, timezoneShift);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String result = sdf.format(calendar.getTime());
        return result;
    }
}
