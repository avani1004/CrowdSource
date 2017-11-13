package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by avaniarora on 11/3/17.
 */

public class TaskInfo extends AppCompatActivity {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_ADDRESS_1 = 1;
    private EditText mNameOfTask;
    private EditText mDescription;
    private EditText mPrice;
    private  EditText mLocation;
    private LatLng mLocationLatLng;
    private String mBaseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        mNameOfTask = (EditText) findViewById(R.id.taskName);
        mDescription = (EditText) findViewById(R.id.description);
        mPrice = (EditText) findViewById(R.id.price);
        mLocation = (EditText) findViewById(R.id.location);
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
}}