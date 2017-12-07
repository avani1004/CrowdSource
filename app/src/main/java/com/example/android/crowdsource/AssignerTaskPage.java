package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by umang on 12/6/17.
 */

public class AssignerTaskPage extends AppCompatActivity{

    private TextView tp;
    public String task_name;
    private Button mPostReview;
    private Button mPayByCash;
    private Button mPayViaVenmo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assigner_task_page);

        tp = (TextView) findViewById(R.id.textView2);
//        tp.setText("Hello World");

        mPostReview = (Button) findViewById(R.id.post_review);
        mPayByCash = (Button) findViewById(R.id.pay_cash);
        mPayViaVenmo = (Button) findViewById(R.id.pay_venmo);

        mPostReview.setVisibility(View.GONE);
        mPayByCash.setVisibility(View.GONE);
        mPayViaVenmo.setVisibility(View.GONE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("AssignedTasks");
        final DatabaseReference myRef2 = database.getReference("tasks");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){

                String taskKey = getIntent().getStringExtra("key");
                if (dataSnapshot.hasChildren()) {
//                    String key = "pqr";
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                   String assignee = getIntent().getStringExtra("email");
                   // Log.d("assignee", assignee);
                    retrieveTaskName(myRef2, taskKey);
                    myRef.child(taskKey).setValue(new AssignedTask(getTask_name(), true, false, false, false, assignee,email ));
                    Log.d("AssignerTask", "New AssignedTask created with task name "+ getTask_name());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("dd", "Failed to read value.", databaseError.toException());
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                if (dataSnapshot.child("startedFlag").toString().contains("true"))
                    tp.setText("You have started task.\n Waiting for acknowledgement");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Log.d("AssignerTask", "The DataSnapshot has key " + dataSnapshot.getKey() + "changed");

                if (dataSnapshot.child("completedFlag").toString().contains("true")) {
                    tp.setText("Assignee has completed task");
                    mPostReview.setVisibility(View.VISIBLE);
                    mPayByCash.setVisibility(View.VISIBLE);
                    mPayViaVenmo.setVisibility(View.VISIBLE);

                }

                else if (dataSnapshot.child("acknowledgedFlag").toString().contains("true"))
                    tp.setText("Assignee has acknowledged task");

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

        mPayViaVenmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.venmo");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }

        });

        mPostReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignerTaskPage.this, PostReview.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));


                startActivity(intent);
            }

        });

    }

    public void retrieveTaskName(DatabaseReference myRef2, final String taskkey)
    {
        String ans = "";
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){

                if(dataSnapshot.child(taskkey).exists()) {
                    String s  = dataSnapshot.child(taskkey).child("name").getValue().toString();
                    Log.d("AssignerTaskPage", "Retreiving Task Name as "+ s);
                    setTask_name(s);
                    Log.d("AssignerTaskPage", "Verifying Task Name as "+ getTask_name());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                setTask_name("Task abc");
            }
        });
    }

    public void setTask_name(String s)
    {
        task_name = s;
    }

    public String getTask_name()
    {
        return task_name;
    }


}
