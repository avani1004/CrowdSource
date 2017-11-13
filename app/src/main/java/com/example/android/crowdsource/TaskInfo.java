package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

/**
 * Created by avaniarora on 11/3/17.
 */

public class TaskInfo extends AppCompatActivity {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_ADDRESS_1 = 1;
    private EditText mNameOfTask;
    private EditText mDescription;
    private EditText mPrice;
    private  EditText mLocation;
    //private LatLng mAddress1LatLng;
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
                try {

                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setCountry("US")
                            .build();

                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .setFilter(typeFilter)
                                    .build(AddressActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_ADDRESS_1);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}