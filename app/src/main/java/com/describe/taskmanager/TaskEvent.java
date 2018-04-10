package com.describe.taskmanager;

import android.net.Uri;

import java.io.Serializable;
import java.util.Date;


public class TaskEvent implements Serializable {


    private int id;
    private String title;
    private String description;
    private Date date;
    private boolean hasNotified = false; //used to note when the event has notified

    public boolean hasImage(){
        return !(this.path==null);
    }
    public Uri getDlURL() {
        return dlURL;
    }

    public void setDlURL(Uri dlURL) {
        this.dlURL = dlURL;
    }

    private Uri dlURL;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public long getCalID() {
        return calID;
    }

    public void setCalID(long calID) {
        if (this.isSynced()) {
            this.calID = calID;
        }
    }

    private long calID;
    private String createTime;
    private Date alarmDate;

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    private boolean synced;


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
    @Deprecated
    public void setDate (Date newDate){
        this.date = newDate;
    }
    @Deprecated
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
    public boolean getHasNotified() {return this.hasNotified;}
    public void setHasNotified(boolean hasNotified) { this.hasNotified = hasNotified;}

    //default
    public TaskEvent(){ }

    public TaskEvent(TaskEvent task){
        this.title = task.title;
        this.description = task.description;
        this.date = task.date;
        this.createTime = task.createTime;
        this.alarmDate = task.alarmDate;
    }

    public TaskEvent(String title, String description, Date date, String createTime,boolean synced){
        this.title = title;
        this.description = description;
        this.date = date;
        this.createTime = createTime;
        this.alarmDate = null;
        setSynced(synced);

    }
    public TaskEvent(String title, String description, Date date, String createTime,boolean synced,Date alarmDate){
        this.title = title;
        this.description = description;
        this.date = date;
        this.createTime = createTime;
        setSynced(synced);
        setAlarmDate(alarmDate);

    }

    boolean hasAlarm(){
        return (this.alarmDate!=null);
    }
}
