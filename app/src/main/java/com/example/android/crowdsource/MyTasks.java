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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by avaniarora on 12/4/17.
 */

public class MyTasks extends AppCompatActivity {
   private ListView mListView1;
    private ListView mListView2;
    ArrayList<String> list;
    ArrayList<String> list1;
    //ArrayList<String> list2;
    HashMap<String,String> key_task_name;
    HashMap<String,String> key_task_name2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tasks);
        mListView1 = (ListView) findViewById(R.id.listView1);
        mListView2 = (ListView) findViewById(R.id.listView2);
        list = new ArrayList<String>();
        list1 = new ArrayList<String>();
        //list2 = new ArrayList<String>();
        key_task_name = new HashMap<String, String>();
        key_task_name2 = new HashMap<String, String>();
        //mListView1.setOnItemClickListener(this);
        //mListView2.setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

       // FirebaseDatabase database = ;
        final DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("tasks");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.hasChildren() && dataSnapshot1.getKey().contains("tasks")) {
                            for (DataSnapshot item : dataSnapshot1.getChildren()) {
                                String key_task = item.getKey();
                                //Log.d("key",key_task);
                                Task task = item.getValue(Task.class);
                                String email = task.email;

                                //Log.d("name",FirebaseAuth.getInstance().getCurrentUser().getEmail()+"");
                                if (email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                    //Log.d("name",task.name);
                                    key_task_name.put(key_task, task.name);
                                    list.add(task.name);
                                }
                            }
                        }
                    }
                }
                displayList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(MyTasks.this,PeopleAppliedToTasks.class);
                String selectedFromList = (mListView1.getItemAtPosition(position).toString());
                for(Map.Entry<String,String> value: key_task_name.entrySet()){
                    if(value.getValue().equals(selectedFromList)) {
                        intent.putExtra("key",value.getKey());
                        intent.putExtra("task_name",selectedFromList);
                        break;
                    }

                }
                startActivity(intent);

            }
        });



        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("AcceptedJobs");

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        //String key_task = dataSnapshot1.getKey();;

                        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(dataSnapshot1.child("email").getValue().toString())){

                            list1.add(dataSnapshot1.child("task_name").getValue().toString());
                            key_task_name2.put(dataSnapshot1.child("key").getValue().toString(), dataSnapshot1.child("task_name").getValue().toString());

                        }
                    }
                }

                displayList1();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //Log.d("hello","hello");
                Intent intent = new Intent(MyTasks.this,AssigneeTaskPage.class);
                String selectedFromList = (mListView2.getItemAtPosition(position).toString());
                for(Map.Entry<String,String> value: key_task_name2.entrySet()){
                    if(value.getValue().equals(selectedFromList)) {
                        //Log.d("key",value.getKey());
                        intent.putExtra("key",value.getKey());
                        intent.putExtra("task_name",selectedFromList);
                        break;
                    }

                }
                startActivity(intent);
                //String item = ((TextView)view).getText().toString();

                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

            }
        });


    }

    public void displayList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list );
        mListView1.setAdapter(adapter);
    }

    public void displayList1(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list1 );
        mListView2.setAdapter(adapter);
    }

   /* @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,PeopleAppliedToTasks.class);
        String selectedFromList = (mListView1.getItemAtPosition(i).toString());
        for(Map.Entry<String,String> value: key_task_name.entrySet()){
            if(value.getValue().equals(selectedFromList)) {
                intent.putExtra("key",value.getKey());
                intent.putExtra("task_name",selectedFromList);
                break;
            }

    }
        startActivity(intent);
    }*/
}