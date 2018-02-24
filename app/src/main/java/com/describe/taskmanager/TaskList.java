package com.describe.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskList extends AppCompatActivity implements UIInterface {

    SimpleAdapter adapter;
    ListView resultsListView;
    static ArrayList<TaskEvent> taskEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        FirestoreAgent fsAgent = new FirestoreAgent();

        fsAgent.getTaskCollection("g2x3irLzu1DTJXbymPXw",this,"category");

        //Reference to list view for SimpleAdapter to fill
        resultsListView = findViewById(R.id.results_listview);
    }

    @Override
    public void updateObject(String fieldName, Object requestedObj) {

    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

        this.taskEvents = collectionContent; //will use later.

        //Hashmap for placeholder data
        HashMap<String, String> TaskHash = new HashMap<>();

        for (TaskEvent event:collectionContent){
            TaskHash.put(event.getTitle(),event.getDescription());
        }
        Log.d("DEBUG_TASKLIST",collectionContent.toString());
        //Create a sit of hashmaps for the SimpleAdapter to read
        List<HashMap<String, String>> listItems = new ArrayList<>();
        adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"Content","Title"},
                new int[]{R.id.title, R.id.content});
        //Create iterator to convert single hashmaps to dual hashmaps.
        Iterator iter = TaskHash.entrySet().iterator();

        //Iterate thru the above hashmap,splitting each key-value pair so that SimpleAdapter can read them as a 2-part hashmap from its list.
        while (iter.hasNext()) {

            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)iter.next();

            resultsMap.put("Title", pair.getKey().toString());
            resultsMap.put("Content", pair.getValue().toString());

            listItems.add(resultsMap);
        }

        resultsListView.setClickable(true);
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                HashMap<String, String> o = (HashMap<String, String>)resultsListView.getItemAtPosition(position);
               // Toast.makeText(getApplicationContext(), o.get("Title"),Toast.LENGTH_SHORT).show();

                TaskEvent taskEvent = this.findTaskByTitle(o.get("Title"));
                if (taskEvent == null) {
                    Log.d("TaskList", "TaskEvent for taskEvent: " + o.get("Title") + ".");
                    return;  //taskEvent for
                }

                Intent i = new Intent(getApplicationContext(),TaskEventView.class);
                i.putExtra("taskEvent", taskEvent);
                startActivity(i);
            }

            private TaskEvent findTaskByTitle(String title) {
                for (TaskEvent event : TaskList.taskEvents) {
                    if (event.getTitle() == title) {
                        return event;
                    }
                }
                return null;
            }
        });
        //Apply the adapter
        resultsListView.setAdapter(adapter);
    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

    }

    @Override
    public void firebaseSuccess(String message_title, String message_content) {

    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content) {

    }
}