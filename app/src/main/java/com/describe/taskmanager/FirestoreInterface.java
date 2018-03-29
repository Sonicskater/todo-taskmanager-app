package com.describe.taskmanager;

import java.util.ArrayList;


public interface FirestoreInterface {
    void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent);
    void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent);

}
