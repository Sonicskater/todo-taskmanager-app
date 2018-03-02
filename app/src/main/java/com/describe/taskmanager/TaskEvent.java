package com.describe.taskmanager;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bencook on 2018-01-31.
 */

public class TaskEvent implements Serializable {


    private int id;
    private String title;
    private String description;
    private Date date;
    private String time;

    //getters and setters

    //this is a recent update, makes it so the event knows about it's own ID number
    public void setId(int id) { this.id = id;}
    public int getId(){ return this.id; }


    public void setTitle (String newTitle){
        this.title = newTitle;
    }
    public String getTitle(){
        return this.title;
    }
    public void setDescription (String newDescription){
        this.description = newDescription;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDate (Date newDate){
        this.date = newDate;
    }
    public Date getDate(){
        return this.date;
    }
    public void setTime (String newTime){
        this.time = newTime;
    }
    public String getTime(){
        return time;
    }

    //default
    public TaskEvent(){

    }
    public TaskEvent(String title, String description, Date date, String Time){
        this.title = title;
        this.description = title;
        this.date = date;
        this.time = time;
    }
}
