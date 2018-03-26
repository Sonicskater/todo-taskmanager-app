package com.describe.taskmanager;

import java.util.ArrayList;

/**
 * Created by devon on 3/20/2018.
 */

public interface FirestoreInterface {
    void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent);
    void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent);

}
