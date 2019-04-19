package com.example.rahul.bmicalci;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class calculate extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    Button btnCalculate, btnHistory;
    TextView tvWelcome, tvHeight, tvFeet, tvWeight, tvInch, tvLoc, tvTemp;
    Spinner spnHeight, spnInch;
    EditText etWeight;
    double bmi;
    double weight;
    String name;
    SharedPreferences sp1;

    GoogleApiClient gac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnHistory = (Button) findViewById(R.id.btnHistory);

        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvLoc = (TextView) findViewById(R.id.tvLoc);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvFeet = (TextView) findViewById(R.id.tvFeet);
        tvInch = (TextView) findViewById(R.id.tvInch);

        spnHeight = (Spinner) findViewById(R.id.spnHeight);
        spnInch = (Spinner) findViewById(R.id.spnInch);

        etWeight = (EditText) findViewById(R.id.etWeight);

        Intent i = getIntent();

        Intent yo = getIntent();

        sp1 = getSharedPreferences("yo", MODE_PRIVATE);
        name = sp1.getString("name", "");


        tvWelcome.setText("Welcome " + name);



        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        gac = builder.build();


        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        spnHeight.setAdapter(adapter);


        final ArrayList<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        list1.add("9");
        list1.add("10");
        list1.add("11");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        spnInch.setAdapter(adapter1);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String yo = etWeight.getText().toString();
                if (yo.length() == 0) {
                    etWeight.setError("enter the weight");
                    etWeight.requestFocus();
                    return;
                }

                weight = Double.parseDouble(yo);

                int feet = Integer.parseInt(spnHeight.getSelectedItem().toString());
                int inch = Integer.parseInt(spnInch.getSelectedItem().toString());

                double feet1 = inch / 12;
                double height = feet + feet1;
                double heightm = height / 3.28;


                bmi = weight / (heightm * heightm);

                Intent in = new Intent(calculate.this, ResultActivity.class);
                Bundle b = new Bundle();
                b.putString("name", name);
                b.putDouble("bmi", bmi);
                in.putExtras(b);
                startActivity(in);
            }
        });


        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(calculate.this, HistoryDisplay.class);
                startActivity(in);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (gac != null) {
            gac.connect();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (gac != null)
            gac.disconnect();


    }

    @Override
    public void onConnected(Bundle bundle) {

        Location loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if (loc != null) {
            Geocoder g = new Geocoder(this, Locale.ENGLISH);
            try {
                List<android.location.Address> al = g.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);

                Address add = al.get(0);

                String msg = add.getLocality();

                Task1 t1 = new Task1();
                t1.execute("http://api.openweathermap.org/data/2.5" + "/weather?units=metric&q=" + msg
                        + "&appid=c6e315d09197cec231495138183954bd");


                tvLoc.setText(msg + " : ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "check gps", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        gac.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d("DB3214", "LOCATION NAHI MILAT AHE");

    }

    class Task1 extends AsyncTask<String, Void, Double> {

        double temp;

        @Override
        protected Double doInBackground(String... params) {
            String line = "", json = "";
            try {
                URL u = new URL(params[0]);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.connect();

                InputStream is = c.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                while ((line = br.readLine()) != null) {
                    json = json + line + "\n";
                }
                if (json != null) {
                    JSONObject j = new JSONObject(json);
                    JSONObject q = j.getJSONObject("main");
                    temp = q.getDouble("temp");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return temp;

        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvTemp.setText("" + aDouble + "\u00b0C");
        }

    }



    private Boolean exit = false;


    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to exit?");
        builder.setCancelable(false);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("EXIT");
        alert.show();
    }
}