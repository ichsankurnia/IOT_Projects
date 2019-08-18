package com.example.xp.json;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.System.in;

public class MainActivity extends AppCompatActivity {

    RequestQueue rq;
    TextView temp, hum, ppm, ldr;

    String get_temp, get_hum, get_ppm, get_ldr;

    Button btnjson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq = Volley.newRequestQueue(this);

        temp = findViewById(R.id.temptext);
        hum = findViewById(R.id.humitext);
        ppm = findViewById(R.id.ppmtext);
        ldr = findViewById(R.id.ldrtext);
        btnjson = findViewById(R.id.btnjson);

        /* btnjson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendjson();
            }
        }); */

        sendjson();
        //sendjsonarray();
    }

    public void sendjson(){

        String url  = "http://f-home.ecb2k16.com/jsin.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("sensor");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject sensor = jsonArray.getJSONObject(i);

                        get_temp = sensor.getString("temp");
                        get_hum = sensor.getString("humi");
                        get_ppm = sensor.getString("ppm");
                        get_ldr = sensor.getString("ldr");

                        temp.setText(get_temp);
                        hum.setText(get_hum);
                        ppm.setText(get_ppm);
                        ldr.setText(get_ldr);

                    }
                    
                    /*
                    JSONObject reader = new JSONObject();

                    JSONObject main = reader.getJSONObject("0");
                    String a = main.getString("temp");

                    temp.setText(a); */

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
        refresh(1000);
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

