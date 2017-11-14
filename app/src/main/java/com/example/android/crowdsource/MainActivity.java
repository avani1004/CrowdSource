package com.example.android.crowdsource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mCreateTask;
    private Button mViewTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCreateTask = (Button) findViewById(R.id.create);
        mViewTask = (Button) findViewById(R.id.button2);

        mCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskInfo.class);
                startActivity(intent);
            }

        });

        mViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewTasks.class);
                startActivity(intent);
            }

        });
    }
}
