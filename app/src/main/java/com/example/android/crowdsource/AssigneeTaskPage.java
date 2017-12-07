package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by umang on 12/5/17.
 */

public class AssigneeTaskPage extends AppCompatActivity {

    private Button mAckStart;
    private Button mTaskComp;
    private TextView tp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignee_task_page);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("AssignedTasks");

        tp = (TextView) findViewById(R.id.textView);
        tp.setText("The assigner has not yet started the task");

        mAckStart = (Button) findViewById(R.id.ackStart);
        mAckStart.setVisibility(View.GONE);
        mTaskComp = (Button) findViewById(R.id.task_comp);
        mTaskComp.setVisibility(View.GONE);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.d("AssigneeTask", "The DataSnapshot has key " + dataSnapshot.getKey());

                String taskKey = getIntent().getStringExtra("key");
                if (dataSnapshot.getKey().contains(taskKey)) {
                    //Instead of contains pqr do getIntent.getStringExtra("key")
//                    Log.d("AssigneeTask", "pqr identified");
                    tp.setText("The assigner has started the task. Click button below to acknowledge");
                    mAckStart.setVisibility(View.VISIBLE);

//                    if (dataSnapshot.child("pqr").child("startedFlag").getValue().toString().contains("true")) {
//                        Log.d("AssigneeTask", "Inside if - task started");
//                        mAckStart.setVisibility(View.VISIBLE);
//                    }
                }
                else
                {
                    Log.d("AssigneeTask", "Inside else - task not started");
                }

            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Log.d("AssigneeTask", "The DataSnapshot has key " + dataSnapshot.getKey() + "changed");

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
        mAckStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskKey = getIntent().getStringExtra("key");
                Log.d("AssigneeTask", "Value of Task Key " + taskKey);
                myRef.child(taskKey).child("acknowledgedFlag").setValue("true");
                mAckStart.setVisibility(View.VISIBLE);

                Log.d("AssigneeTask", "Assignee clicked ack task");
                tp.setText("You can now start working on your task \n. Click button below once you are done");
                mTaskComp.setVisibility(View.VISIBLE);
            }

        });

        mTaskComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskKey = getIntent().getStringExtra("key");
                Log.d("AssigneeTask", "Value of Task Key " + taskKey);
                myRef.child(taskKey).child("completedFlag").setValue("true");
                Log.d("AssigneeTask", "Assignee clicked task complete");
                tp.setText("Task completed \n. Waiting for payment");
            }

        });


    }
}