package com.describe.taskmanager;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchAgent implements UIInterface {
    //Ensure that the same instance of search agent is reused.
    static private SearchAgent instance;
    private HashMap<Category,ArrayList<TaskEvent>> cachedDatabase;
    private FirestoreAgent fsAgent;

    private ArrayList<Category> catList;

    public static SearchAgent getInstance(){
        if (instance==null){
            instance = new SearchAgent();
        }
        else{
            instance.updateCache();
        }

        return instance;
    }
    private SearchAgent(){
        this.cachedDatabase = new HashMap<>();
        fsAgent = FirestoreAgent.getInstance();
        this.updateCache();

    }
    private HashMap<Category,ArrayList<TaskEvent>> cacheCopy(){
        HashMap<Category,ArrayList<TaskEvent>> out = new HashMap<>();
        for(Category cat : this.cachedDatabase.keySet()){
            ArrayList<TaskEvent> temp = new ArrayList<>();
            for(TaskEvent task: this.cachedDatabase.get(cat))
                temp.add(new TaskEvent(task));

            out.put(new Category(cat),temp);
        }
        return out;
    }
    private void updateCache(){
        cachedDatabase = new HashMap<>();
        fsAgent.getCategoryCollection("",this);
    }
    public HashMap<Category,ArrayList<TaskEvent>> SearchString(ArrayList<String> searchTerms){
        HashMap<Category,ArrayList<TaskEvent>> result = cacheCopy();

        for(String term : searchTerms) {

            HashMap<Category,ArrayList<TaskEvent>> temp = new HashMap<>();
            for (Category cat : result.keySet()) {

                ArrayList<TaskEvent> tempList = new ArrayList<>();
                for (TaskEvent task : result.get(cat)) {

                    if (task.getTitle().contains(term) || task.getDescription().contains(term)){
                        tempList.add(task);
                    }
                }
                //Don't add the category if its empty
                if (!tempList.isEmpty()){
                    temp.put(cat,tempList);
                }
            }
            result = temp;
        }


        return result;
    }
    @Override
    public void updateObject(String fieldName, Object requestedObj) {

    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {
        Category catRef = null;
        for(Category cat :catList){
            if (cat.getCategoryTitle().equals(collectionName)){
                catRef = cat;
            }
        }
        if (catRef!=null) {
            cachedDatabase.put(catRef, collectionContent);
        }
        else{
            Log.d("SearchAgent",collectionName+" was not found in catList");
        }
    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {
        catList = collectionContent;
        for (Category cat : catList){
            fsAgent.getTaskCollection("",this,cat.getCategoryTitle());
        }
    }

    @Override
    public void firebaseSuccess(String message_title, String message_content) {

    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content) {

    }
}
