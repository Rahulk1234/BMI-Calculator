package com.example.rahul.bmicalci;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.R.attr.data;

/**
 * Created by Rahul on 26-12-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    SQLiteDatabase db;
    Context context;


    public  DatabaseHandler(Context context)
    {
        super(context,"Yobase.db",null,1);
        Log.d("DB456","DB CREATED/OPENED");

        this.context = context;
        db= this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try
        {
            db.execSQL("CREATE TABLE dataRecords(date VARCHAR2 NOT NULL,bmi DOUBLE)");
            Log.d("DB457","Table CREATED/OPENED");
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Exception while creating database", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS dataRecords");
        onCreate(db);
        Log.d("DB1234","TABLE DROPPED");
    }

    public void addAns(String bmi, String date) {

        ContentValues cv = new ContentValues();
        cv.put("date",date);
        cv.put("bmi",bmi);
        db.insert("dataRecords",null,cv);

        Toast.makeText(context, "saved!!", Toast.LENGTH_SHORT).show();

    }

    public String[] getRecords() {

        Cursor cursor = db.query("dataRecords" ,new String[]{"date","bmi"}, null, null ,null ,null ,null);


        int i= cursor.getCount();
        String[] values = new String[i];
        int trac=0;


        int dateColumn = cursor.getColumnIndex("date");
        int bmiColumn = cursor.getColumnIndex("bmi");
        cursor.moveToFirst();

        if(cursor != null && (i>0))
        {

            do
            {
              String a= cursor.getString(dateColumn);
              String c=cursor.getString(bmiColumn);
              values[trac++]=("\n Date:"+ a +"\n bmi:" +c);
            }while ((cursor.moveToNext()));

        }

        else
        {
            Log.d("D1235","no values");
        }

        cursor.close();
        return values;
    }
}
