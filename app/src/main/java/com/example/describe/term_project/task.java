package com.example.describe.term_project;

/**
 * Created by devon on 1/26/2018.
 */

public class task {
    private int ownerID = 0;
    private String taskTitle = "";
    private String taskContents = "";
    private int categoryID = 0;

    public task(int creatorID,String title, String content, int categoryID){
        this.categoryID = categoryID;
        this.taskTitle = title;
        this.ownerID = creatorID;
        this.taskContents = content;

    }
    public void setTaskTitle(int editorID, String newTitle){
        if (editorID == this.ownerID){
            this.taskTitle = newTitle;
        }
        else{
            //Display "You do not have permissions" pop-up
        }
    }
    public void setTaskContents(int editorID, String newContents){
        if (editorID == this.ownerID){
            this.taskContents = newContents;
        }
        else{
            //Display "You do not have permissions" pop-up
        }
    }

}
