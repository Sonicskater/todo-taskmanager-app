package com.describe.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class DailyViewActivity extends AppCompatActivity implements FirestoreInterface, SwipeRefreshLayout.OnRefreshListener
{
    ListView resultsListView;
    FirestoreAgent fsAgent;
    Calendar date;
    SimpleAdapter adapter;
    ArrayList<TaskEvent> taskE;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedState)
    {
        super.onCreate(savedState);
        setContentView(R.layout.activity_daily_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);

        Intent newIntent = getIntent();
        String tempDate =  newIntent.getStringExtra("date");
        date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM dd, yyyy", Locale.ENGLISH);
        try {
            date.setTime(sdf.parse(tempDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ActionBar supportBar = getSupportActionBar();
        if (supportBar != null) {
            supportBar.setDisplayHomeAsUpEnabled(true);
            supportBar.setDisplayShowHomeEnabled(true);
            supportBar.setTitle(tempDate);
        }

        taskE = new ArrayList<>();

        fsAgent = FirestoreAgent.getInstance();
        fsAgent.getAllTasks(this);

        resultsListView = findViewById(R.id.results_listview);
    }

    @Override
    public void onRefresh()
    {
        refreshLayout.setRefreshing(true);
        //fsAgent.getTaskCollectionForDate("",this, date);
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

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent)
    {
        for (TaskEvent event : collectionContent)
        {
            if (event.hasAlarm()) {
                Calendar eventDate = Calendar.getInstance();
                eventDate.setTime(event.getAlarmDate());
                if (eventDate.get(Calendar.YEAR) == date.get(Calendar.YEAR) && eventDate.get(Calendar.MONTH) == date.get(Calendar.MONTH) && eventDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                    taskE.add(event);
                }
            }
        }
        final ArrayList<TaskEvent> taskEvents = taskE; //will use later.

//Hashmap for placeholder data
        HashMap<String, String> TaskHash = new HashMap<>();

        for (TaskEvent event:taskE){
            TaskHash.put(event.getTitle(),event.getDescription());
        }
        Log.d("DEBUG_TASKLIST",taskE.toString());
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
                i.putExtra("category", "");
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

        //we need to redraw the list
    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

    }
}