package com.example.android.crowdsource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        TextView headerText = (TextView)findViewById(R.id.header);
        headerText.setText(getIntent().getStringExtra("name"));

        ArrayList<String> text = new ArrayList<String>();


        text.add("Task Details:\n " + getIntent().getStringExtra("description"));
        text.add("Money you earn in $:\n " + getIntent().getStringExtra("charge"));
        text.add("Where you'll have to go:\n " + getIntent().getStringExtra("location"));
        //Toast.makeText(getApplicationContext(), s,
         //       Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, text  );
        ListView list = (ListView) findViewById(R.id.list_description);
        list.setAdapter(adapter);

        Button applyButton = (Button)findViewById(R.id.apply_button);


    }
}
