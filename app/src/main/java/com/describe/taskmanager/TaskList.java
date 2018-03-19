package com.describe.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TaskList extends AppCompatActivity implements UIInterface {

    String categoryName;
    SimpleAdapter adapter;
    ListView resultsListView;
    static ArrayList<TaskEvent> taskEvents;
    FirestoreAgent fsAgent;
    MenuActions menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        FloatingActionButton addTask = findViewById(R.id.addTask);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        @NonNull
        ActionBar supportBar = getSupportActionBar();

        supportBar.setDisplayHomeAsUpEnabled(true);
        supportBar.setDisplayShowHomeEnabled(true);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EventCreateView.class);
                startActivity(i);

            }

        });

        fsAgent = new FirestoreAgent();


        this.categoryName = getIntent().getStringExtra("categoryName");
        //set the title at the top of the screen to the current categorys
        supportBar.setTitle(this.categoryName);

        fsAgent.getTaskCollection("", this, this.categoryName);


        //Reference to list view for SimpleAdapter to fill
        resultsListView = findViewById(R.id.results_listview);
        this.menuAdapter = new MenuActions(getApplicationContext(), this);
    }
    //onResume is called whenever this activity is brought back into focus, i.e. from a child dialog.
    @Override
    protected void onResume(){
        super.onResume();
        fsAgent.getTaskCollection("", this, this.categoryName);
    }

    @Override
    public void updateObject(String fieldName, Object requestedObj) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return this.menuAdapter.onOptionSelected(item);
    }

    //Add menu to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
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

        //Iterate through the above hashmap,splitting each key-value pair so that SimpleAdapter can read them as a 2-part hashmap from its list.
        while (iter.hasNext()) {

            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)iter.next();

            resultsMap.put("Title", pair.getKey().toString());
            resultsMap.put("Content", pair.getValue().toString());

            listItems.add(resultsMap);
        }

        resultsListView.setClickable(true);
        final TaskList self = this;
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
                i.putExtra("category",self.categoryName);
                startActivity(i);

                //should now rebuild the list from the fbAgent
            }

            private TaskEvent findTaskByTitle(String title) {
                for (TaskEvent event : TaskList.taskEvents) {
                    if (event.getTitle().equals(title)) {
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