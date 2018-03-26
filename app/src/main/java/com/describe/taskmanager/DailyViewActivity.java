package com.describe.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by camila on 2018-03-23.
 */

public class DailyViewActivity extends AppCompatActivity implements UIInterface, SwipeRefreshLayout.OnRefreshListener
{
    ListView resultsListView;
    FirestoreAgent fsAgent;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedState)
    {
        super.onCreate(savedState);
        setContentView(R.layout.activity_daily_view);
        FloatingActionButton addTask = findViewById(R.id.addTask);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);

        Intent newIntent = getIntent();
        String date = newIntent.getStringExtra("date");

        ActionBar supportBar = getSupportActionBar();
        if (supportBar != null) {
            supportBar.setDisplayHomeAsUpEnabled(true);
            supportBar.setDisplayShowHomeEnabled(true);
            supportBar.setTitle(date);
        }

        fsAgent = FirestoreAgent.getInstance();

        resultsListView = findViewById(R.id.results_listview);
    }

    @Override
    public void onRefresh()
    {
        refreshLayout.setRefreshing(true);
        fsAgent.getTaskCollection("",this,null);
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
    public void updateObject(String fieldName, Object requestedObj) {

    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

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