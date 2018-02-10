package com.describe.taskmanager;

import java.util.ArrayList;

/**
 * Created by devon on 2/2/2018.
 */

public interface UIInterface {
    void updateObject(String fieldName, Object requestedObj);
    void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent);
    void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent);
    void firebaseSuccess(String message_title, String message_content);
    void firebaseFailure(String error_code,String message_title,String extra_content);
}

