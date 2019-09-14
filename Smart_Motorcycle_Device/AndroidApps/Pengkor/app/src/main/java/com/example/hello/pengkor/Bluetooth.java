package com.example.hello.pengkor;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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

public class Bluetooth extends AppCompatActivity {
    RequestQueue rq;
    TextView lat, lng, alti, mph;

    ImageView imgblutut, blutut;
    TextView txtblutut;

    String get_lat, get_long, get_alti, get_mph, get_blt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

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

                        imgblutut = (ImageView) findViewById(R.id.imgblutut);
                        blutut = (ImageView) findViewById(R.id.blutut);
                        txtblutut = (TextView) findViewById(R.id.txtblutut);

                        if(get_blt=="1"){
                            imgblutut.setImageResource(R.drawable.bluetooth_on);
                            blutut.setImageResource(R.drawable.bluetooth_off);
                            txtblutut.setText("Connected");
                        }else {
                            imgblutut.setImageResource(R.drawable.bluetooth_off);
                            blutut.setImageResource(R.drawable.bluetooth_disconnect);
                            txtblutut.setText("Disconnected");
                        }

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
