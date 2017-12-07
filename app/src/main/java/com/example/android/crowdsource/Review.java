package com.example.android.crowdsource;

/**
 * Created by umang on 11/14/17.
 */

public class Review {

    public String reviewer;
    public String beingReviewed;
    public float rating;
    public String title;
    public String description;

    public Review()
    {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Review(String title, String description, float rating, String reviewer, String beingReviewed)
    {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.reviewer = reviewer;
        this.beingReviewed = beingReviewed;
    }

    @Override
    public String toString()
    {
        return "Title: "+ this.title+ "\n" + "Reviewer: "+ this.reviewer + "\n"  + "Being Reviewed: "+ this.beingReviewed + "\n" + "Rating: "+ this.rating + " stars\n" + "Description: "+ this.description+ "\n\n";
    }

}
