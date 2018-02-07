package com.describe.taskmanager
        ;

/**
 * Created by devon on 1/26/2018.
 */

public class task {
    private String ownerID = "0";
    private String taskTitle = "";
    private String taskContents = "";
    private int categoryID = 0;

    //Need empty constructor in order to allow firebase to convert the object to a form it can use automatically.
    //No I don't know why
    public task(){}

    public task(String creatorID,String title, String content, int categoryID){
        this.categoryID = categoryID;
        this.taskTitle = title;
        this.ownerID = creatorID;
        this.taskContents = content;

    }
    public void setTaskTitle(String newTitle){

        this.taskTitle = newTitle;

    }
    public void setTaskContents(String newContents){

        this.taskContents = newContents;

    }
    public String getOwnerID(){
        return this.ownerID;
    }
    public String getTaskTitle(){
        return this.taskTitle;
    }
    public String getTaskContents(){
        return this.taskContents;
    }
    public int getCategoryID(){
        return this.categoryID;
    }

}
