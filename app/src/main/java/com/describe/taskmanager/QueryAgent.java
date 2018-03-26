package com.describe.taskmanager;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryAgent implements FirestoreInterface{
    private static FirestoreAgent fsAgent;
    private HashMap<Category,ArrayList<TaskEvent>> localCopy = null;
    private static QueryAgent instance = null;
    private ArrayList<Category> catList;
    public static QueryAgent getInstance(){
        if (instance== null){
            instance=new QueryAgent();
        }
        fsAgent = FirestoreAgent.getInstance();
        instance.getLocalCopy();
        return instance;
    }
    private QueryAgent(){

    }
    private void getLocalCopy(){
        localCopy = new HashMap<>();

        fsAgent.getCategoryCollection(this);
    }
    QueryResult Query(ArrayList<QueryTerm> queryTerms){

        return new QueryResult(localCopy,queryTerms);
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
            localCopy.put(catRef, collectionContent);
        }
        else{
            Log.d("SearchAgent",collectionName+" was not found in catList");
        }
    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {
        catList = collectionContent;
        for (Category cat : catList){
            fsAgent.getTaskCollection(this,cat.getCategoryTitle());
        }
    }
}
