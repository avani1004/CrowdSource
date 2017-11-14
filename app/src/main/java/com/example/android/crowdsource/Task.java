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

    public Task()
    {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String name, String description, float charge, String location, String latlang)
    {
        this.name = name;
        this.description = description;
        this.charge = charge;
        this.location = location;
        this.latlang = latlang;
    }

    @Override
    public String toString()
    {
        return "Task Name: "+ this.name + "\n" + "Description: "+ this.description + "\n"  + "Charge: "+ this.charge + "\n" + "Location: "+ this.location + "\n" + "Latitude/Longitude: "+ this.latlang+ "\n\n";
    }

}
