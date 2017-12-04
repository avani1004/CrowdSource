package com.example.android.crowdsource;

/**
 * Created by umang on 11/14/17.
 */

public class Task {

    public String name;
    public String description;
    public float charge;
    public String location;
    public String latlang;
    public String email;

    public Task()
    {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String name, String description, float charge, String location, String latlang,String email)
    {
        this.name = name;
        this.description = description;
        this.charge = charge;
        this.location = location;
        this.latlang = latlang;
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "Task Name: "+ this.name + "\n" + "Description: "+ this.description + "\n"  + "Charge: "+ this.charge + "\n" + "Location: "+ this.location + "\n" + "Latitude/Longitude: "+ this.latlang+ "\n\n" + "EmailId:" + this.email+"\n\n";
    }

}
