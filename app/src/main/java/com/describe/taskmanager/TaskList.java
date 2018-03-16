package com.describe.taskmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.SimpleAdapter;
//import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
//import java.util.Map;

public class TaskList extends Activity implements UIInterface, SwipeRefreshLayout.OnRefreshListener {

    String categoryName;
    SimpleAdapter adapter;
    ListView resultsListView;
    //static ArrayList<TaskEvent> taskEvents;
    FirestoreAgent fsAgent;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        FloatingActionButton addTask = findViewById(R.id.addTask);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);
        categoryName = getIntent().getStringExtra("categoryName");

        /*
        ActionBar supportBar = getSupportActionBar();
        if (supportBar != null) {
            supportBar.setDisplayHomeAsUpEnabled(true);
            supportBar.setDisplayShowHomeEnabled(true);
            supportBar.setTitle(this.categoryName);
        }
        */
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EventCreateView.class);
                startActivity(i);

            }

        });

        fsAgent = FirestoreAgent.getInstance();


        this.onRefresh();
        //set the title at the top of the screen to the current category





        //Reference to list view for SimpleAdapter to fill
        resultsListView = findViewById(R.id.results_listview);
    }
    //onResume is called whenever this activity is brought back into focus, i.e. from a child dialog.
    @Override
    protected void onResume(){
        super.onResume();
        this.onRefresh();
    }
    @Override
    public void updateObject(String fieldName, Object requestedObj) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        if (item.getItemId()==R.id.action_settings)
        {
            Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.action_refresh)
        {
            this.onRefresh();
        }
        return true;
    }
    //Add menu to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.onRefresh();
        return true;
    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

        final ArrayList<TaskEvent> taskEvents = collectionContent; //will use later.

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
        //Iterator iter = TaskHash.entrySet().iterator();

        //Iterate through the above hashmap,splitting each key-value pair so that SimpleAdapter can read them as a 2-part hashmap from its list.
        for (String key : TaskHash.keySet()){
            HashMap<String, String> resultsMap = new HashMap<>();


            resultsMap.put("Title", key);
            resultsMap.put("Content", TaskHash.get(key));

            listItems.add(resultsMap);
        }
        /*
        while (iter.hasNext()) {

            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)iter.next();

            resultsMap.put("Title", pair.getKey().toString());
            resultsMap.put("Content", pair.getValue().toString());

            listItems.add(resultsMap);
        }
        */

        resultsListView.setClickable(true);
        final TaskList self = this;
        resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                @SuppressWarnings("unchecked")
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
                for (TaskEvent event : taskEvents) {
                    if (event.getTitle().equals(title)) {
                        return event;
                    }
                }
                return null;
            }
        });
        //Apply the adapter
        resultsListView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
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
    //refreshs list
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        fsAgent.getTaskCollection("", this, this.categoryName);
    }
}