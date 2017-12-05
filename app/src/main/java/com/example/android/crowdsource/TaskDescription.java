package com.example.android.crowdsource;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        TextView headerText = (TextView) findViewById(R.id.header);
        headerText.setText(getIntent().getStringExtra("name"));

        ArrayList<String> text = new ArrayList<String>();


        text.add("\nTask Details:\n " + getIntent().getStringExtra("description"));
        text.add("\nMoney you earn in $:\n " + getIntent().getStringExtra("charge"));
        text.add("\nWhere you'll have to go:\n " + getIntent().getStringExtra("location"));
        text.add("\nThis is the task key:\n " + getIntent().getStringExtra("task key"));
        //Toast.makeText(getApplicationContext(), s,
        //       Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, text);
        ListView list = (ListView) findViewById(R.id.list_description);
        list.setAdapter(adapter);

        Button applyButton = (Button) findViewById(R.id.apply_button);

        applyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //TODO: find user in user information

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("user information");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        ArrayList<Double> distlist = new ArrayList<Double>();

                        for (DataSnapshot item : dataSnapshot.getChildren()){
//                            Log.d("value", item.getValue() + "");
//                            Log.d("value", item.getKey() + "");
//                            Log.d("value", item.child("user_task_keys").getValue() + "");


                            //List<Object>  = new ArrayList<Object>();
                            List dummy = java.util.Arrays.asList(item.child("user_task_keys").getValue());

                            List userTaskKeys = java.util.Arrays.asList(dummy.get(0));


                            for(int i=0; i<userTaskKeys.size(); ++i){
                                Log.d("value", userTaskKeys.get(i) + "");

                            }







                        }





                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
