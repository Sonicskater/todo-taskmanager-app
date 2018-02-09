package com.describe.taskmanager;

/**
 * Created by devon on 1/26/2018.
 */

public class category {

    private int categoryID = 0;
    private String categoryTitle  = "Default";
    private int[] color = {0,255,0};

    public category(int categoryID, String categoryTitle, int[] categoryColor){
        this.categoryID = categoryID;
        this.categoryTitle = categoryTitle;
        this.color[0] = categoryColor[0];
        this.color[1] = categoryColor[1];
        this.color[2] = categoryColor[2];
    }
}
