package com.describe.taskmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;

public class TaskList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        ListView resultsListView = (ListView) findViewById(R.id.results_listview);


        HashMap<String, String> nameAddresses = new HashMap<>();
        nameAddresses.put("Math 265", "Integrals");
        nameAddresses.put("English", "Shakespeare");
        nameAddresses.put("Physics", "Kinematics");
        nameAddresses.put("Chemistry", "Boles Law");
        nameAddresses.put("Social", "French Revolution");
        nameAddresses.put("Biology", "Mitochondria");

        List<HashMap<String, String>> listIteams = new ArrayLists<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems,R.layout.list_item),
                new String[]{"First line", "Second Line"},
                new int[]{R.id.text1, R.id.text2}




    }
}
