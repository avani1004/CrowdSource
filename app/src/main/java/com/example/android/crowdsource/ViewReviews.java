package com.example.android.crowdsource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by avaniarora on 12/7/17.
 */

public class ViewReviews extends AppCompatActivity {
    private DatabaseReference myRef3;
    private TextView textView;
    private ArrayList<String> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reviews);
        myRef3 = FirebaseDatabase.getInstance().getReference("reviews");
        textView = (TextView) findViewById(R.id.reviews_append);
        reviews = new ArrayList<String>(getIntent().getStringArrayListExtra("list"));


        myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    for (DataSnapshot item : dataSnapshot.getChildren()) {

                        if(reviews.contains(item.child("beingReviewed").getValue().toString())){
                            Review review1 = new Review(item.child("title").getValue().toString(),item.child("description").getValue().toString(),Float.parseFloat(item.child("rating").getValue().toString()),item.child("reviewer").getValue().toString(),item.child("beingReviewed").getValue().toString());
                            textView.append(review1.toString());
                        }
                    }
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


}}
