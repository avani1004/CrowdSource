package com.example.android.crowdsource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by avaniarora on 12/6/17.
 */

public class NotifyAssignee extends AppCompatActivity {


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_assignee);
        textView = (TextView) findViewById(R.id.notify);
        textView.setText("We will notify "+getIntent().getStringExtra("name")+" that you have accepted the request");
}}
