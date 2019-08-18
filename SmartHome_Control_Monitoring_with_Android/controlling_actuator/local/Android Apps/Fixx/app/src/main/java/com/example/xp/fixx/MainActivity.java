package com.example.xp.fixx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnDataSendToActivity {

    ImageView bg_state;
    Button btn_ol, btn_il, btn_ro, btn_fan;
    TextView txt_network, temp, hum, gas;
    String url = "http://192.168.43.93/"; //Define your NodeMCU IP Address here Ex: http://192.168.1.4/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg_state = findViewById(R.id.bg_status);
        txt_network = findViewById(R.id.txt_network);

        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        gas = (TextView) findViewById(R.id.gas);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isNetworkAvailable()){
                    bg_state.setImageResource(R.drawable.background_on);
                    txt_network.setText("Welcome to My Future Home");
                }else{
                    bg_state.setImageResource(R.drawable.background);
                    txt_network.setText("Cound not connect to the server");
                }
                updateStatus();
                handler.postDelayed(this, 2000);
            }
        }, 5000);  //the time is in miliseconds


        btn_ol = findViewById(R.id.luar);
        btn_il = findViewById(R.id.dalam);
        btn_ro = findViewById(R.id.atap);
        btn_fan = findViewById(R.id.kipas);

        btn_ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"out_lamp";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });

        btn_il.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"in_lamp";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
        btn_ro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"roof";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
        btn_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"fan";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void sendData(String str) {
        updateButtonStatus(str);
    }

    private void updateStatus(){
        String url_rl = url+"status";
        StatusTask task = new StatusTask(url_rl, this);
        task.execute();
    }

    //Function for updating Button Status
    private void updateButtonStatus(String jsonStrings){
        try {
            JSONObject json = new JSONObject(jsonStrings);

            String out_lamp = json.getString("ol");
            String in_lamp = json.getString("il");
            String roof = json.getString("ro");
            String fan = json.getString("fan");


            if(out_lamp.equals("1")){
                btn_ro.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_ro.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(in_lamp.equals("1")){
                btn_il.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_il.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(roof.equals("1")){
                btn_ro.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_ro.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(fan.equals("1")){
                btn_fan.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_fan.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}


