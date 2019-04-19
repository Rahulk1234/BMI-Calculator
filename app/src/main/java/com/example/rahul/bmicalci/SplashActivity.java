package com.example.rahul.bmicalci;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    ImageView IvSp;
    TextView tvEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        IvSp =(ImageView)findViewById(R.id.IvSp);
        tvEntry=(TextView)findViewById(R.id.tvEntry);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {


                    sleep(3000);
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        myThread.start();
    }
}
