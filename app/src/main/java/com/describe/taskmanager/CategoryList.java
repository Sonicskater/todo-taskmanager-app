package com.describe.taskmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryList extends AppCompatActivity implements UIInterface
{
    CategoryAdapter gridAdapter;
    GridView gridview;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        //Code derived from Android Documentation
        FirestoreAgent fsAgent = new FirestoreAgent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridview =findViewById(R.id.gridview);
        fsAgent.getCategoryCollection("g2x3irLzu1DTJXbymPXw",this);
        gridview.setAdapter(gridAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Toast.makeText(getApplicationContext(), "Your Category ;D", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });
    }


    @Override
    public void updateObject(String fieldName, Object requestedObj) {

    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

    }
    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

        ArrayList<String> catList = new ArrayList<String>();

        for (Category cat : collectionContent){
            catList.add(cat.getCategoryTitle());
        }

        gridAdapter = new CategoryAdapter(this,catList);
        gridview.setAdapter(gridAdapter);
    }

    @Override
    public void firebaseSuccess(String message_title, String message_content) {

    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content) {

    }
}
