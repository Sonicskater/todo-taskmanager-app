package com.describe.taskmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TaskList extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        /*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        */

        ListView resultsListView = findViewById(R.id.results_listview);


        HashMap<String, String> TaskHash = new HashMap<>();

        //Placeholders
        TaskHash.put("Math 265", "Integrals");
        TaskHash.put("English", "Shakespeare");
        TaskHash.put("Physics", "Kinematics");
        TaskHash.put("Chemistry", "Boles Law");
        TaskHash.put("Social", "French Revolution");
        TaskHash.put("Biology", "Mitochondria");

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First line", "Second Line"},
                new int[]{R.id.text2, R.id.text1});

        Iterator iter = TaskHash.entrySet().iterator();

        while (iter.hasNext()) {

                HashMap<String, String> resultsMap = new HashMap<>();
                Map.Entry pair = (Map.Entry)iter.next();

                resultsMap.put("Second Line", pair.getValue().toString());
                resultsMap.put("First Line", pair.getKey().toString());
                listItems.add(resultsMap);


        }

        Log.d("TEST_OUT", listItems.toString());
        resultsListView.setAdapter(adapter);

    }
}