package com.describe.taskmanager;

import android.widget.Toast;

import java.util.ArrayList;

@Deprecated
public interface UIInterface {
    void updateObject(String fieldName, Object requestedObj);
    void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent);
    void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent);

    //These would have default methods, but if we added them then our app wouldn't support devices older than API 24/Android 7.0,
    //meaning most of our test devices wouldn't work.(All except Devons Phone)
    void firebaseSuccess(String message_title, String message_content);
    void firebaseFailure(String error_code,String message_title,String extra_content);
}