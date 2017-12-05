package com.example.android.crowdsource;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by umang on 11/14/17.
 */

public class ViewTasks extends AppCompatActivity implements AdapterView.OnItemClickListener{


    ArrayList<String> text = new ArrayList<String>();
    HashMap<Integer, Task> sorted_task_list = new HashMap<Integer, Task>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tasks);
        //final TextView viewtasks = (TextView)findViewById(R.id.display);

        //*********************for clickable list***********************************

        //* *EDIT* *
        ListView listview = (ListView) findViewById(R.id.list_output);
        listview.setOnItemClickListener(this);

        //**************************************************************************




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<Double, Task> hm = new HashMap<Double, Task>();
                ArrayList<Double> distlist = new ArrayList<Double>();
                if (dataSnapshot.hasChildren())
                {
                    int count = 0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        if (dataSnapshot1.hasChildren() && dataSnapshot1.getKey().contains("tasks"))
                        {
                            for (DataSnapshot item : dataSnapshot1.getChildren())
                            {
                                Task task = item.getValue(Task.class);
                                //viewtasks.append(task.toString());
                                String ll = task.latlang.split(":")[1];
                                String ll2 = ll.substring(2, ll.length()-1);
                                //viewtasks.append("Latitude: "+ ll2.split(",")[0]+"\n");
                                //viewtasks.append("Longitude: "+ ll2.split(",")[1]+ "\n\n");



                                LatLng current = new LatLng(36.999974, -122.064248);
                                Double dist = computeDistance(current, new LatLng(new Double(ll2.split(",")[0]), new Double(ll2.split(",")[1])));
                                distlist.add(dist);
                                hm.put(dist, task);
                            }
                        }
                        else
                        {
                            //String s = (String) dataSnapshot1.getValue();
//                          viewtasks.append(s+ "\n");
                        }
                    }
                }

                Collections.sort(distlist);
                for (int i = 0; i < distlist.size(); i++) {
                    //viewtasks.append(hm.get(distlist.get(i)).toString());
                    Task task = hm.get(distlist.get(i));
                    text.add(task.name);
                    Log.i("ViewTasks","What I was looking: " + task);
                    sorted_task_list.put(i, task);
                }

                displayList();

            }
            @Override
            public void onCancelled(DatabaseError error) {
//                Log.w("dd", "Failed to read value.", error.toException());
            }
        });

    }

    //***************************************************************************
    public void displayList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, text  );
        ListView list = (ListView) findViewById(R.id.list_output);
        list.setAdapter(adapter);
    }


    //***************************************************************************

    public Double computeDistance(LatLng latLngA, LatLng latLngB) {
        Location locationA = new Location("Address 1");
        locationA.setLatitude(latLngA.latitude);
        locationA.setLongitude(latLngA.longitude);

        Location locationB = new Location("Address 2");
        locationB.setLatitude(latLngB.latitude);
        locationB.setLongitude(latLngB.longitude);

        double distance = locationA.distanceTo(locationB);
        //Log.d("distance", distance + "");

        return distance;

    }

    //****************************on clicking list items*****************

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("ViewTask", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        intent.setClass(this, TaskDescription.class);

        // information of current task
        Task current_task = sorted_task_list.get(position);
        intent.putExtra("name", current_task.name);
        intent.putExtra("description", current_task.description);
        intent.putExtra("charge", String.valueOf(current_task.charge));

        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        //current_task.
        intent.putExtra("location", String.valueOf(current_task.location));
        // Or / And
        intent.putExtra("id", id);
        startActivity(intent);
    }
}


