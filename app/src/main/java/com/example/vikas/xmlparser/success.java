package com.example.vikas.xmlparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class success extends AppCompatActivity {

    TextView Name , Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        Bundle bundle = getIntent().getExtras();
        Name.setText("welcome     " + bundle.getString("name"));
        Email.setText("Email      " + bundle.getString("email"));
    }
}
