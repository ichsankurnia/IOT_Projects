package com.example.hello.pengkor;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Monitoring extends AppCompatActivity {

    RequestQueue rq;
    TextView lat, lng, alti, mph;

    String get_lat, get_lang, get_alti, get_mph, get_blt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        rq = Volley.newRequestQueue(this);
        lat = findViewById(R.id.temptext);
        lng = findViewById(R.id.humitext);
        alti = findViewById(R.id.ppmtext);
        mph = findViewById(R.id.ldrtext);

        sendjson();
    }

    public void sendjson(){

        String url  = "http://pokokeyakin.ecb2k16.com/jsin.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("gps");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject sensor = jsonArray.getJSONObject(i);

                        get_lat = sensor.getString("latitude");
                        get_lang = sensor.getString("longitude");
                        get_alti = sensor.getString("altitude");
                        get_mph = sensor.getString("mph");

                        lat.setText(get_lat);
                        lng.setText(get_lang);
                        alti.setText(get_alti);
                        mph.setText(get_mph);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        rq.add(jsonObjectRequest);
        refresh(100);
    }

    private void refresh(int milli){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sendjson();
            }
        };
        handler.postDelayed(runnable,milli);
    }
}
