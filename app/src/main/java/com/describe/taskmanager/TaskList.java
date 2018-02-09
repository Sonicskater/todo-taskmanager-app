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


        //Reference to list view for SimpleAdapter to fill
        ListView resultsListView = findViewById(R.id.results_listview);

        //Hashmap for placeholder data
        HashMap<String, String> TaskHash = new HashMap<>();

        //Placeholder data
        //###
        TaskHash.put("Math 265", "Integrals");
        TaskHash.put("English", "Shakespeare");
        TaskHash.put("Physics", "Kinematics");
        TaskHash.put("Chemistry", "Boles Law");
        TaskHash.put("Social", "French Revolution");
        TaskHash.put("Biology", "Mitochondria");
        //###

        //Create a sit of hashmaps for the SimpleAdapter to read
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"Second Line","First Line"},
                new int[]{R.id.text2, R.id.text1});
        //Create iterator to convert single hashmaps to dual hashmaps.
        Iterator iter = TaskHash.entrySet().iterator();

        //Iterate thru the above hashmap,splitting each key-value pair so that SimpleAdapter can read them as a 2-part hashmap from its list.
        while (iter.hasNext()) {

                HashMap<String, String> resultsMap = new HashMap<>();
                Map.Entry pair = (Map.Entry)iter.next();

                resultsMap.put("Second Line", pair.getValue().toString());
                resultsMap.put("First Line", pair.getKey().toString());

                listItems.add(resultsMap);


        }

        //Apply the adapter
        resultsListView.setAdapter(adapter);

    }
}