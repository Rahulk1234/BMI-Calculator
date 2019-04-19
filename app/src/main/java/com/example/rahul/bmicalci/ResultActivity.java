package com.example.rahul.bmicalci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    TextView tvResult,tv1,tv2,tv3,tv4;
    Button btnBack, btnShare, btnSave;
    private String result;
    int a;
    SharedPreferences sp1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = (TextView)findViewById(R.id.tvResult);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        tv4 = (TextView)findViewById(R.id.tv4);

        btnBack=(Button)findViewById(R.id.btnBack);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnShare=(Button)findViewById(R.id.btnShare);


        sp1 = getSharedPreferences("yo", MODE_PRIVATE);

       final String name = sp1.getString("name", "");
        final String age = sp1.getString("age","");
        String phone = sp1.getString("ph","");
        String gender = sp1.getString("gen","");




        Bundle b =getIntent().getExtras();
        final double bmi =b.getDouble("bmi");


        if(bmi< 18.5)
        {
            result = "underweight";
            a=1;
        }
        else if(bmi<25)
        {
            result ="normal";
            a=2;
        }

        else if(bmi <30)
        {
            result ="overweight";
            a=3;
        }

        else
        {
            result="obese";
            a=4;
        }

        tvResult.setText("Your bmi is "+ bmi  +".So you are " +result);


        if(a==1)
        {
            tv1.setTextColor(Color.parseColor("#FF0000"));
        }

        else if(a==2)
        {
            tv2.setTextColor(Color.parseColor("#FF0000"));
        }
        else if(a==3)
        {
            tv3.setTextColor(Color.parseColor("#FF0000"));
        }
        else
        {
            tv4.setTextColor(Color.parseColor("#FF0000"));
        }

        tv1.setText("Below 18.5 is underweight");
        tv2.setText("Between 18.5 - 25 is normal");
        tv3.setText("between 25 - 30 is overweight");
        tv4.setText("more than 30 is obese");


        Date currentTime = Calendar.getInstance().getTime();





        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yo = new Intent(ResultActivity.this, calculate.class);
                yo.putExtra("name",name);
                startActivity(yo);
            }
        });

        final DatabaseHandler db = new DatabaseHandler(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                db.addAns(String.valueOf(bmi), String.valueOf(date));

            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                sp1.getString("name",name);
                String yo="Name : "+ name +"\n Age : "+ age +"\n "+ "Your bmi is "+ bmi +".\n So you are "+ result +".";


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Bmi ");
                intent.putExtra(Intent.EXTRA_TEXT,yo);



                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(Intent.createChooser(intent,"Share via"));

                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.m1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.about)
        {
            Snackbar.make(findViewById(android.R.id.content),"App developed By Onkar Sargar",Snackbar.LENGTH_LONG).show();
        }

        if(item.getItemId()==R.id.website)
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https:"+"stackoverflow.com"));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
