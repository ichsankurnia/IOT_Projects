package com.example.hello.pengkor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   private CardView bluetoothmain,controlmain,mapsmain,monitoringmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Deklarasi Card
        bluetoothmain = (CardView) findViewById(R.id.connectivity_main);
        controlmain = (CardView) findViewById(R.id.control_main);
        mapsmain = (CardView) findViewById(R.id.maps_main);
        monitoringmain = (CardView) findViewById(R.id.monitoring_main);

        //Tambah clicklistener ke Card
        bluetoothmain.setOnClickListener(this);
        controlmain.setOnClickListener(this);
        mapsmain.setOnClickListener(this);
        monitoringmain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i ;

        switch (v.getId()) {
            case R.id.connectivity_main:
                i = new Intent(this, Bluetooth.class);
                startActivity(i);
                break;
            case R.id.control_main:
                i = new Intent(this, Control.class);
                startActivity(i);
                break;
            case R.id.maps_main:
                i = new Intent(this, Maps.class);
                startActivity(i);
                break;
            case R.id.monitoring_main:
                i = new Intent(this, Monitoring.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
