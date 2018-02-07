package com.describe.taskmanager.domain;

import java.util.Date;

/**
 * Created by bencook on 2018-01-31.
 */

public class Event {

    private String title;
    private String description;
    private Date date;
    private String time;

    //getters and setters
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

    /**
     * Created by devon on 1/26/2018.
     */

    public static class user {
        private int userID = 0;
        private int[] userCatagories = {0};
        private String username = "default_user";
    }
    //default
    public Event(){

    }
    public Event(String title, String description, Date date, String Time){
        this.title = title;
        this.description = title;
        this.date = date;
        this.time = time;
    }
}
