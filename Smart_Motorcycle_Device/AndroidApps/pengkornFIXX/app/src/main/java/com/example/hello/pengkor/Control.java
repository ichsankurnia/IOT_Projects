package com.example.hello.pengkor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hello.pengkor.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Control extends AppCompatActivity implements AmbilDataTask.Callback {

    private static final String TAG = "main";

    private static ImageView cdi, start;
    private static Button btn_cdi, btn_start;
    private int current_image, current_image1, current_tombol, current_tombol1;
    int[] tombol={R.drawable.switch_off,R.drawable.switch_on};
    int[] images={R.drawable.cdi_off,R.drawable.cdi_on};
    int[] images1={R.drawable.power_off,R.drawable.power_on};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        /*

        btn_cdi = (Button) findViewById(R.id.button_cdi);
        btn_start = (Button) findViewById(R.id.button_starter);
        cdi = (ImageView) findViewById(R.id.cdi_off);
        start = (ImageView) findViewById(R.id.starter_off);

        btn_cdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_image1++;
                current_image1 = current_image1 % images.length;
                cdi.setImageResource(images1[current_image1]);
                current_tombol++;
                current_tombol = current_tombol % images.length;
                btn_cdi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,tombol[current_tombol]);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_image++;
                current_image = current_image % images.length;
                start.setImageResource(images[current_image]);
                current_tombol1++;
                current_tombol1 = current_tombol1 % images.length;
                btn_start.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,tombol[current_tombol1]);
            }
        }); */
    }
    public void CDI_ON(View view) {
        new AmbilDataTask(this).execute("http://pokokeyakin.ecb2k16.com/upco.php?id=1&status=on");
        Button btn_cdiON = (Button) findViewById(R.id.button_2);
        Button btn_cdiOFF = (Button) findViewById(R.id.button_1);
        btn_cdiON.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_defof);
        btn_cdiOFF.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_off);
        cdi = (ImageView) findViewById(R.id.cdi_off);
        cdi.setImageResource(R.drawable.cdi_on);
    }

    public void CDI_OFF(View view) {
        new AmbilDataTask(this).execute("http://pokokeyakin.ecb2k16.com/upco.php?id=1&status=off");
        Button btn_cdiON = (Button) findViewById(R.id.button_2);
        Button btn_cdiOFF = (Button) findViewById(R.id.button_1);
        btn_cdiON.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_on);
        btn_cdiOFF.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_defon);
        cdi = (ImageView) findViewById(R.id.cdi_off);
        cdi.setImageResource(R.drawable.cdi_off);
    }

    public void Starter_ON(View view) {
        new AmbilDataTask(this).execute("http://pokokeyakin.ecb2k16.com/upco.php?id=2&status=on");
        Button btn_starterON = findViewById(R.id.button_4);
        Button btn_starterOFF = findViewById(R.id.button_3);
        btn_starterOFF.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_off);
        btn_starterON.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_defof);
        start = (ImageView) findViewById(R.id.starter_off);
        start.setImageResource(R.drawable.power_on);
    }

    public void Starter_OFF(View view) {
        new AmbilDataTask(this).execute("http://pokokeyakin.ecb2k16.com/upco.php?id=2&status=off");
        Button btn_starterON = findViewById(R.id.button_4);
        Button btn_starterOFF = findViewById(R.id.button_3);
        btn_starterON.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_on);
        btn_starterOFF.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,R.drawable.switch_defon);
        start = (ImageView) findViewById(R.id.starter_off);
        start.setImageResource(R.drawable.power_off);
    }
    @Override
    public void sendResult(String result) {
        Log.d(TAG, "sendResult: " + result);
    }
}

