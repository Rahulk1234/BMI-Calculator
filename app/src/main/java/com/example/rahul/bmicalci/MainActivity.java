package com.example.rahul.bmicalci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.EditText;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    EditText etName,etAge,etPhone;
    Button btnRegister;
    RadioGroup rg;
    RadioButton rbM,rbF;
    SharedPreferences sp1;


    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        rg = (RadioGroup) findViewById(R.id.rg);
        rbF = (RadioButton)findViewById(R.id.rbF);
        rbM= (RadioButton)findViewById(R.id.rbM);



        sp1= getSharedPreferences("yo",MODE_PRIVATE);
        String name= sp1.getString("name","");

        if(!name.equals(""))
        {
            Intent it = new Intent(MainActivity.this,calculate.class);
            startActivity(it);
            finish();
        }

        else

        {

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String gender;

                   String name = etName.getText().toString();
                   String age = etAge.getText().toString();
                   String phone = etPhone.getText().toString();


                    if (name.length() == 0) {
                        Toast.makeText(getApplicationContext(), "enter the name", Toast.LENGTH_SHORT).show();
                        etName.requestFocus();
                        return;

                    }

                    if (age.length() == 0) {
                        Toast.makeText(getApplicationContext(), "invalid age", Toast.LENGTH_SHORT).show();

                        etAge.requestFocus();
                        return;
                    }

                    if (phone.length() != 10 || phone.length() == 0) {
                        Toast.makeText(getApplicationContext(), "invalid phone number", Toast.LENGTH_SHORT).show();

                        etPhone.requestFocus();
                        return;
                    }


                    if (rg.getCheckedRadioButtonId() == -1)
                    {
                        Toast.makeText(MainActivity.this, "Select your gender", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        i = rg.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) findViewById(i);
                        gender = rb.getText().toString();
                    }


                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putString("name", name);
                    editor.putString("age", age);
                    editor.putString("ph", phone);
                    editor.putString("gen", gender);
                    editor.commit();


                    Toast.makeText(getApplicationContext(), "data saved", Toast.LENGTH_SHORT).show();


                    try {
                        Intent i = new Intent(MainActivity.this, calculate.class);
                        i.putExtra("name", name);
                        startActivity(i);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });


        }

    }
}

