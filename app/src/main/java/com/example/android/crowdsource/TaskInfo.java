package com.example.android.crowdsource;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by avaniarora on 11/3/17.
 */


public class TaskInfo extends AppCompatActivity {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_ADDRESS_1 = 1;
    private EditText mNameOfTask;
    private EditText mDescription;
    private EditText mPrice;
    private EditText mLocation;
    private LatLng mLocationLatLng;
    private String mBaseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    List<Map> new_entry = new LinkedList<>();
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        mNameOfTask = (EditText) findViewById(R.id.taskName);
        mDescription = (EditText) findViewById(R.id.description);
        mPrice = (EditText) findViewById(R.id.price);
        mLocation = (EditText) findViewById(R.id.location);
        Button done = (Button) findViewById(R.id.done);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Hello", "Inside location");

                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .build();
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(TaskInfo.this);
                    startActivityForResult(intent,PLACE_AUTOCOMPLETE_REQUEST_CODE_ADDRESS_1);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        done.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Task task = new Task(mNameOfTask.getText().toString(), mDescription.getText().toString(),new Float(mPrice.getText().toString()),mLocation.getText().toString(),mLocationLatLng.toString(),user.getEmail().toString());

                //To get the key
                String key = UUID.randomUUID().toString();
                myRef.child("tasks").child(key).setValue(task);
                //myRef.setValue("This should bloody work now too");

                Toast.makeText(getBaseContext(), "Task Created Successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(TaskInfo.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

//        done.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //EditText name_photo = (EditText) findViewById(R.id.text_photo);
//
//                String name = mNameOfTask.getText().toString();
//                String description = mDescription.getText().toString();
//                String price = mPrice.getText().toString();
//                String location = mLocation.getText().toString();
//                String lat_long = mLocationLatLng.toString();
//
//                Map<String, String> dump_structure = new HashMap<String, String>();
//                dump_structure.put("name_of_task", name);
//                dump_structure.put("task_description", description);
//                dump_structure.put("task_price", price);
//                dump_structure.put("task_location", location);
//                dump_structure.put("latitude_longitude", lat_long);
//
//                new_entry.add(dump_structure);
//
//
//
//                // Write a message to the database
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("message");
//
//                //myRef.setValue();
//                myRef.push().setValue(dump_structure);
//
//                //TextView view = (TextView)findViewById(R.id.winner_text);
//
//                //TODO: reading from the firebase json
//
////                myRef.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////                        // This method is called once with the initial value and again
////                        // whenever data at this location is updated.
////                        value = dataSnapshot.getValue(String.class);
////                        //Log.d(TAG, "Value is: " + value);
////                    }
////
////                    @Override
////                    public void onCancelled(DatabaseError error) {
////                        // Failed to read value
////                        //Log.w(TAG, "Failed to read value.", error.toException());
////                    }
////                });
//
//
//
//
//
//            }
//
//
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_ADDRESS_1) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);

                mLocationLatLng = place.getLatLng();
                mLocation.setText(place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

                Toast.makeText(getApplicationContext(), PlaceAutocomplete.getStatus(this, data).toString(), Toast.LENGTH_LONG).show();

            }
        }
    }

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




}