package com.example.android.crowdsource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    public String email;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assigner_task_page);

        tp = (TextView) findViewById(R.id.textView2);
        tp.setText("Hello World");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("AssignedTasks");
//        final DatabaseReference myRef2 = database.getReference("tasks");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){

                String taskKey = getIntent().getStringExtra("key");
                if (dataSnapshot.hasChildren()) {
//                    String key = "pqr";
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    String assignee = "default@ucsc.edu";
//                    setAssignerEmail(myRef2, taskKey); //Sorry, not being used
                    myRef.child(taskKey).setValue(new AssignedTask("Repair Laptop", true, false, false, false, assignee,email ));

                    Log.d("AssignerTask", "New AssignedTask created");
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

                if (dataSnapshot.child("completedFlag").toString().contains("true"))
                    tp.setText("Assignee has completed task");

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

    }

    public void setAssignerEmail(DatabaseReference myRef2, final String taskkey)
    {
        String ans = "";
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){

                if(dataSnapshot.child(taskkey).exists())
                     setEmail(dataSnapshot.child(taskkey).child("email").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                setEmail("default@ucsc.edu");
            }
        });
    }

    public void setEmail(String s)
    {
        email = s;
    }

    public String getEmail()
    {
        return email;
    }


}
