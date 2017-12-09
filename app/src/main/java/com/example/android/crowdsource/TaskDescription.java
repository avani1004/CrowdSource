package com.example.android.crowdsource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDescription extends AppCompatActivity {
    String emailAddress;
    String key;
    String assignee_email_address;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);

        TextView headerText = (TextView) findViewById(R.id.header);
        headerText.setText(getIntent().getStringExtra("name"));


        myRef = FirebaseDatabase.getInstance().getReference("mobileapplications");
        myRef.setValue("ApplyToJob");

        myRef1 = FirebaseDatabase.getInstance().getReference("ApplyToJob");

        ArrayList<String> text = new ArrayList<String>();


        text.add("\nTask Details:\n " + getIntent().getStringExtra("description"));
        text.add("\nMoney you earn in $:\n " + getIntent().getStringExtra("charge"));
        text.add("\nWhere you'll have to go:\n " + getIntent().getStringExtra("location"));
        //text.add("\nThis is the task key:\n " + getIntent().getStringExtra("task key"));
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
                            //Log.d("value", item.getKey() + "");
                            //Log.d("value", item.child("user_email") + "");
                        //Log.d("value", item.child("user_task_keys").getValue().toString().replaceAll("[^A-Za-z0-9,\\-]","") + "");
                            if(item.child("user_task_keys").getValue()!=null){
                            String user_key_str = item.child("user_task_keys").getValue().toString().replaceAll("[^A-Za-z0-9,\\-]","");
                            String[] user_key = user_key_str.split(",");


                            //List<Object>  = new ArrayList<Object>();
                           // List dummy = java.util.Arrays.asList(item.child("user_task_keys").getValue());

                            /*Log.d("dummy",dummy+"");
                            for(int i =0;i<dummy.size();i++) {
                                Log.d("value", dummy.get(i) + "");
                            }*/
                            String split_key[] = getIntent().getStringExtra("task key").split("/");

                            for(int i =0;i<user_key.length;i++){
                           if(user_key[i].equals(split_key[split_key.length-1])){
                               Log.d("value","value");
                               emailAddress = item.child("user_email").getValue().toString();
                               key = split_key[split_key.length-1];
                               assignee_email_address = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                               ApplyJob applyJob = new ApplyJob(emailAddress,key,assignee_email_address);
                               myRef1.child(UUID.randomUUID().toString()).setValue(applyJob);
                               Intent intent = new Intent(getApplicationContext(),Submission.class);
                               startActivity(intent);
                               break;

                           }}}










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
