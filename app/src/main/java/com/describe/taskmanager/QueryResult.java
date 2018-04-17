package com.describe.taskmanager;



import java.util.ArrayList;
import java.util.HashMap;


class QueryResult {
    //Enum to prevent default case when possible
    public enum searchPriority{DEFAULT,END_DATE,START_DATE,LOCATION}

    private HashMap<Category,ArrayList<TaskEvent>> data;

    //Constructs a subset of the database based on provided parameters
    QueryResult(HashMap<Category,ArrayList<TaskEvent>> data,ArrayList<QueryTerm> terms){
        this.data = data;

        //Construct the resultant data set based on terms provided.
        for(QueryTerm term : terms) {
            HashMap<Category,ArrayList<TaskEvent>> temp = new HashMap<>();
            for (Category cat : data.keySet()) {
                ArrayList<TaskEvent> tempList = new ArrayList<>();
                for (TaskEvent task : data.get(cat)) {
                    //Call the queries .compare() function to check for validity.
                    if (term!=null&&term.compare(task)){
                        tempList.add(task);
                    }
                }
                temp.put(cat,tempList);
            }
            this.data = temp;
        }
    }
    //return result seperated by category
    HashMap<Category,ArrayList<TaskEvent>> getResultWithCategories(searchPriority priority){
        switch (priority) {
            case DEFAULT:
                return this.data;
            case START_DATE:
                return startDateSortCategories();
            case END_DATE:
                return endDateSortCategories();
            case LOCATION:
                return new HashMap<>();
            default:
                return new HashMap<>();
        }

    }
    //unused functions for sorting the data. couldn't design ui to make use of in time.
    private HashMap<Category,ArrayList<TaskEvent>> startDateSortCategories(){
        return this.data;
    }
    private HashMap<Category,ArrayList<TaskEvent>>   endDateSortCategories(){
        return this.data;
    }
    //remvoes tasks from their categories.
    private ArrayList<TaskEvent> flattenData(){
        ArrayList<TaskEvent> out = new ArrayList<>();
        for(Category category:this.data.keySet()){
           out.addAll(this.data.get(category));
        }
        return out;
    }
    //returns tasks, NOT seperated into categories.
    public ArrayList<TaskEvent> getResult(searchPriority priority){
        switch (priority) {
            case DEFAULT:
                return flattenData();
            case START_DATE:
                return startDateSort();
            case END_DATE:
                return endDateSort();
            case LOCATION:
                return new ArrayList<>();
            default:
                return new ArrayList<>();
        }
    }

    //Sort by data, crappy bubble sort. Not used for same reason as start/endDateCategories
    private ArrayList<TaskEvent> startDateSort() {
        ArrayList<TaskEvent> temp = flattenData();
        boolean done = false;

        while(!done) {
            done = true;
            for (int i = 0; i <= temp.size() - 2; i++) {

                if (temp.get(i).getDate().before(temp.get(i + 1).getDate())) {
                    TaskEvent temp2 = temp.get(i);
                    temp.set(i, temp.get(i + 1));
                    temp.set(i + 1, temp2);
                    done = false;
                }
            }
        }
        return temp;
    }

    //same as above, reverse direction
    private ArrayList<TaskEvent> endDateSort() {
        ArrayList<TaskEvent> temp = new ArrayList<>();

        for (TaskEvent task : flattenData()){
            if (task.hasAlarm()){
                temp.add(task);
            }
        }
        boolean done = false;

        while(!done) {
            done = true;
            for (int i = 0; i <= temp.size() - 2; i++) {

                if (temp.get(i).getAlarmDate().before(temp.get(i + 1).getAlarmDate())) {
                    TaskEvent temp2 = temp.get(i);
                    temp.set(i, temp.get(i + 1));
                    temp.set(i + 1, temp2);
                    done = false;
                }
            }
        }
        return temp;
    }
}
