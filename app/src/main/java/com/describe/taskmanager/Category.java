package com.describe.taskmanager;

/**
 * Created by devon on 1/26/2018.
 */

public class Category {

    private int categoryID = 0;
    private String categoryTitle  = "Default";

    //default category constructor
    public Category(){}

    //category constructor
    public Category(int categoryID, String categoryTitle){
        this.categoryID = categoryID;
        this.categoryTitle = categoryTitle;

    }

    //getters and setters for category ID and Title
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
