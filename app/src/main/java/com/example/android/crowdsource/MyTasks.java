package com.example.android.crowdsource;

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

/**
 * Created by avaniarora on 12/4/17.
 */

public class MyTasks extends AppCompatActivity implements AdapterView.OnItemClickListener{
   private ListView mListView1;
    private ListView mListView2;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_tasks);
        mListView1 =(ListView) findViewById(R.id.listView1);
        mListView2 = (ListView) findViewById(R.id.listView2);
        list = new ArrayList<String>();
        mListView1.setOnItemClickListener(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        if (dataSnapshot1.hasChildren() && dataSnapshot1.getKey().contains("tasks"))
                        {
                            for (DataSnapshot item : dataSnapshot1.getChildren())
                            {
                                Task task = item.getValue(Task.class);
                                String email = task.email;
                                //Log.d("name",FirebaseAuth.getInstance().getCurrentUser().getEmail()+"");
                                if(email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                    //Log.d("name",task.name);

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

    }

    public void displayList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list );
        mListView1.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}