package com.example.xp.bismillah;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {

    TextView temp,humi,ppm,ldr;
    private String TAG = MainActivity.class.getSimpleName();
    public String a,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        humi = findViewById(R.id.humi);
        ppm = findViewById(R.id.ppm);
        ldr = findViewById(R.id.ldr);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://f-home.ecb2k16.com/jsin.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    //JSONArray contacts = new JSONArray(jsonStr);

                    JSONArray jsonArray = jsonObject.getJSONArray("sensor");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject sensor = jsonArray.getJSONObject(i);

                        a = sensor.getString("temp");
                        b = sensor.getString("humi");
                        c = sensor.getString("ppm");
                        d = sensor.getString("ldr");

                        //HashMap<String, String> contact = new HashMap<>();

                        temp.setText(a);
                        humi.setText(b);
                        ppm.setText(c);
                        ldr.setText(d);
                    }

                }catch (final JSONException e){
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            temp.setText(a);
        }
    }
}
