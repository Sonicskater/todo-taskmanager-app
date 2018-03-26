package com.describe.taskmanager;

import java.io.Serializable;
import java.util.Date;


public class TaskEvent implements Serializable {


    private int id;
    private String title;
    private String description;
    private Date date;
    private String createTime;
    private Date alarmDate;


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
    public void setCreateTime(String newTime){
        this.createTime = newTime;
    }
    public String getCreateTime(){
        return createTime;
    }
    public void setAlarmDate (Date newDate){
        this.alarmDate = newDate;
    }
    public Date getAlarmDate(){
        return this.alarmDate;
    }

    //default
    public TaskEvent(){ }

    public TaskEvent(TaskEvent task){
        this.title = task.title;
        this.description = task.description;
        this.date = task.date;
        this.createTime = task.createTime;
        this.alarmDate = task.alarmDate;
    }

    public TaskEvent(String title, String description, Date date, String createTime){
        this.title = title;
        this.description = description;
        this.date = date;
        this.createTime = createTime;
        this.alarmDate = null;

    }
    public TaskEvent(String title, String description, Date date, String createTime,Date alarmDate){
        this.title = title;
        this.description = description;
        this.date = date;
        this.createTime = createTime;
        setAlarmDate(alarmDate);

    }

    boolean hasAlarm(){
        return (this.alarmDate!=null);
    }
}
