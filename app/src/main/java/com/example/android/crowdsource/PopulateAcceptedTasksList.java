package com.example.android.crowdsource;

/**
 * Created by avaniarora on 12/6/17.
 */

public class PopulateAcceptedTasksList {

    public String email;
    public String key;
    public String task_name;

    public PopulateAcceptedTasksList(){

    }

    public PopulateAcceptedTasksList(String email,String key,String task_name){
        this.email = email;
        this.key = key;
        this.task_name = task_name;
    }

}
