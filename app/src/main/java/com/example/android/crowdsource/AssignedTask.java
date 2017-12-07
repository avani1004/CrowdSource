package com.example.android.crowdsource;

/**
 * Created by umang on 12/5/17.
 */

public class AssignedTask {

    public String name;
    public Boolean startedFlag;
    public Boolean acknowledgedFlag;
    public Boolean completedFlag;
    public Boolean paidFlag;
    public String assignee;
    public String assigner;

    public AssignedTask()
    {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public AssignedTask(String name, Boolean startedFlag, Boolean acknowledgedFlag, Boolean completedFlag, Boolean paidFlag, String assignee, String assigner)
    {
        this.name = name;
        this.startedFlag = startedFlag;
        this.acknowledgedFlag = acknowledgedFlag;
        this.completedFlag = completedFlag;
        this.paidFlag = paidFlag;
        this.assignee = assignee;
        this.assigner = assigner;
    }

//    @Override
//    public String toString()
//    {
//        return "Task Name: "+ this.name + "\n" + "Description: "+ this.description + "\n"  + "Charge: "+ this.charge + "\n" + "Location: "+ this.location + "\n" + "Latitude/Longitude: "+ this.latlang+ "\n\n" + "EmailId:" + this.email+"\n\n";
//    }
}
