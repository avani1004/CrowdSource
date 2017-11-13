package com.example.android.crowdsource;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by avaniarora on 11/3/17.
 */

public class Database {
   //private DatabaseReference mDatabase;
    //mDatabase = FirebaseDatabase.getInstance().getReference();
   // Fire
   FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

}
