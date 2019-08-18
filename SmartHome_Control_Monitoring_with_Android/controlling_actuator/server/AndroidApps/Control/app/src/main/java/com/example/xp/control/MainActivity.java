package com.example.xp.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AmbilDataTask.Callback {

    private static final String TAG = "main";

    //Biodata mutia = new Biodata();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* D1on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("http://f-home.ecb2k16.com/upco.php?id=1&status=on"));
                //startActivity(browser);
                //bitmap = D1ON("http://f-home.ecb2k16.com/upco.php?id=1&status=on");
                //mutia.D1ON("on");
            } */
    }

    public void DeSatu(View view) {
        new AmbilDataTask(this).execute("http://f-home.ecb2k16.com/upco.php?id=1&status=on");
    }

    public void DeSatuOFF(View view) {
        new AmbilDataTask(this).execute("http://f-home.ecb2k16.com/upco.php?id=1&status=off");
    }

    public void DeDuaON(View view) {
        new AmbilDataTask(this).execute("http://f-home.ecb2k16.com/upco.php?id=2&status=on");
    }

    public void DeDuaOFF(View view) {
        new AmbilDataTask(this).execute("http://f-home.ecb2k16.com/upco.php?id=2&status=off");
    }

    public void DeTigaON(View view) {
        new AmbilDataTask(this).execute("http://f-home.ecb2k16.com/upco.php?id=3&status=on");
    }

    public void DeTigaOFF(View view) {
        new AmbilDataTask(this).execute("http://f-home.ecb2k16.com/upco.php?id=3&status=off");
    }

    @Override
    public void sendResult(String result) {
        Log.d(TAG, "sendResult: " + result);
    }
}
