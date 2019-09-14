package com.example.hello.pengkor;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Maps extends AppCompatActivity {

    RequestQueue rq;

    String get_lat, get_long, get_alti, get_mph, get_blt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        rq = Volley.newRequestQueue(this);

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
                        get_long = sensor.getString("longitude");
                        get_alti = sensor.getString("altitude");
                        get_mph = sensor.getString("mph");
                        get_blt = sensor.getString("bluetooth");
                        Button btn = findViewById(R.id.btn);

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent maps = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/search/?api=1&query="+get_lat+","+get_long));
                                startActivity(maps);
                            }
                        });
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
