package pu.fmi.rainytime.services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import pu.fmi.rainytime.models.City;
import pu.fmi.rainytime.models.VolleySingleton;



public class WeatherDataService {
    Context context;


    public WeatherDataService(Context context) {
        this.context = context;
    }

    // Used for callback
    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(City city);
    }


    public void getCity(String cityName,VolleyResponseListener volleyResponseListener) {
        City[] city = new City[1];
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=168bd05ec573baa40ca07ac0fa585cfb";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            String lon = "";
            String lat = "";
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject coord = response.getJSONObject("coord");
                    lat = coord.getString("lat");
                    lon = coord.getString("lon");
                    city[0] = new City(cityName,lat,lon);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(city[0]);
            }
        }, error -> {
            //Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
            volleyResponseListener.onError("Something went wrong.");
        });


        VolleySingleton.getInstance(context).addToRequestQueue(request);
        //return city[0];
    }
}
