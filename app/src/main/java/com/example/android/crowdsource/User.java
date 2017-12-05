package com.example.android.crowdsource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avaniarora on 12/4/17.
 */

public class User {
    public String user_name;
    public String user_address;
    public String user_email;
    public String user_mobile_number;
    public String user_lat_long;
    public List<String> user_task_keys = new ArrayList<String>();

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public User(String user_name, String user_address, String user_email, String user_mobile_number, String user_lat_long, List<String> user_task_keys) {
        this.user_name = user_name;
        this.user_address = user_address;
        this.user_email = user_email;
        this.user_mobile_number = user_mobile_number;
        this.user_lat_long = user_lat_long;
        this.user_task_keys = new ArrayList<String>(user_task_keys);
    }


}


