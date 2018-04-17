package com.describe.taskmanager;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryAgent implements FirestoreInterface{
    private static FirestoreAgent fsAgent;
    private HashMap<Category,ArrayList<TaskEvent>> localCopy = null;
    private static QueryAgent instance = null;
    private ArrayList<Category> catList;
    //only deprecated so no one uses except for tests
    @Deprecated
    public void setLocalCopy(HashMap<Category,ArrayList<TaskEvent>> newLocal){
        this.localCopy = newLocal;
    }
    //Singleton pattern
    public static QueryAgent getInstance(){
        if (instance== null){
            instance=new QueryAgent();
        }
        fsAgent = FirestoreAgent.getInstance();
        instance.getLocalCopy();
        return instance;
    }
    //empty constructor for units tests
    @Deprecated
    public QueryAgent(){

    }
    //Caches the database for searching so as not to spam the database.
    private void getLocalCopy(){
        localCopy = new HashMap<>();

        fsAgent.getCategoryCollection(this);
    }
    //Main Query Function
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
            Log.d("QueryAgent",collectionName+" was not found in catList");
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
