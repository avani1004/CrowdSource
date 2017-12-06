package com.example.android.crowdsource;

/**
 * Created by avaniarora on 12/6/17.
 */

public class ApplyJob {

    public String assignor_email_address;
    public String key;
    public String assignee_email_address;


    public ApplyJob(){

    }

    public ApplyJob(String assignor_email_address, String key, String assignee_email_address){
        this.assignor_email_address = assignor_email_address;
        this.key = key;
        this.assignee_email_address = assignee_email_address;

    }
}
