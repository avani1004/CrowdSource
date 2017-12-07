package com.example.android.crowdsource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button mCreateTask;
    private Button mViewTask;
    private Button mMyTasks;
    private Button mLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCreateTask = (Button) findViewById(R.id.create);
        mViewTask = (Button) findViewById(R.id.button2);
        mMyTasks = (Button) findViewById(R.id.myTask);
        mLogout = (Button)findViewById(R.id.logout);

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
        mMyTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyTasks.class);
                startActivity(intent);
            }

        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                    FirebaseAuth fAuth = FirebaseAuth.getInstance();
                    fAuth.signOut();
                    Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }

        });
    }
}
