package com.describe.taskmanager;

import java.util.ArrayList;

public class QueryAgent implements FirestoreInterface{
    private static QueryAgent instance = null;
    public static QueryAgent getInstance(){
        if (instance== null){
            instance=new QueryAgent();
        }
        return instance;
    }
    private QueryAgent(){

    }
    public QueryResult Query(ArrayList<QueryTerm> queryTerms){

        return new QueryResult();
    }
    public QueryResult Query(QueryResult query){

        return new QueryResult();
    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

    }
}
