package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by avaniarora on 12/6/17.
 */

public class PeopleAppliedToTasks extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView1;
    private List<String> email_address_list;
    //private HashMap<String,String> task_id_email;
    private DatabaseReference myRef1;
    private DatabaseReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_applied_tasks);
        mListView1 = (ListView) findViewById(R.id.applied_people_list);
        email_address_list = new ArrayList<String>();
        myRef1 = FirebaseDatabase.getInstance().getReference("mobileapplications");
        myRef1.setValue("AcceptedJobs");
        //task_id_email = new
        mListView1.setOnItemClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("ApplyToJob");

        myRef2 = FirebaseDatabase.getInstance().getReference("AcceptedJobs");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        Log.d("item",item.getValue()+"");
                        if(item.child("assignor_email_address").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                           // Log.d("hi","hello");
                            email_address_list.add(item.child("assignee_email_address").getValue().toString());
                        }
                    }
                }
                displayList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


            public void displayList() {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, email_address_list);
                mListView1.setAdapter(adapter);
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(this, NotifyAssignee.class);

                String selectedFromList = (mListView1.getItemAtPosition(i).toString());
                //Log.d("selected",selectedFromList);
                PopulateAcceptedTasksList populateAcceptedTasksList = new PopulateAcceptedTasksList(selectedFromList,getIntent().getStringExtra("key"),getIntent().getStringExtra("task_name"));
                myRef2.child(UUID.randomUUID().toString()).setValue(populateAcceptedTasksList);
                intent.putExtra("name", selectedFromList);
                startActivity(intent);
            }
        }

