package com.example.android.crowdsource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by umang on 12/7/17.
 */

public class PostReview extends AppCompatActivity{

    private EditText mTitle;
    private EditText mDescription;
    private EditText mRating;
    private Button mPost;
    private String reviewer;
    private String beingReviewed;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_reviews);

        mTitle = (EditText) findViewById(R.id.review_title);
        mDescription = (EditText) findViewById(R.id.review_description);
        mRating = (EditText) findViewById(R.id.rating);
        mPost = (Button) findViewById(R.id.post_review);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mPost.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reviewer = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                beingReviewed = getIntent().getStringExtra("email"); //Need to get this from intent
                String key = UUID.randomUUID().toString();

                Review review = new Review(mTitle.getText().toString(), mDescription.getText().toString(),new Float(mRating.getText().toString()), reviewer,beingReviewed);

                myRef.child("reviews").child(key).setValue(review);
//                myRef1.addListenerForSingleValueEvent(new ValueEventListener(){
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.hasChildren())
//                        {
//                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
//                            {
//                                if (dataSnapshot1.hasChildren() && dataSnapshot1.getKey().contains("user information"))
//                                {
//                                    for (DataSnapshot item : dataSnapshot1.getChildren())
//
//                                    {
//                                        //Log.d("check",item.getKey()+"");
//
//                                        User user1 = item.getValue(User.class);
//                                        List<String> taskKeys = user1.user_task_keys;
//                                        taskKeys.add(key);
//                                        //boolean value = user1.user_email.equals(user.getEmail());
//                                        //Log.d("value",value+"");
//                                        if(user1.user_email.equals(user.getEmail())){
//
//                                            myRef1.child("user information").child(item.getKey()).child("user_task_keys").setValue(taskKeys);
//                                        }
//                                        // Map<String, List<String>> store = item.getValue().;
//                                        //Log.d("check",item.getValue()+"");
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });


                //myRef.setValue("This should bloody work now too");
                //if(user)
                Toast.makeText(getBaseContext(), "Review Created Successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(PostReview.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}
