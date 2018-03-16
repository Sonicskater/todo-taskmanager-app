package com.describe.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryList extends AppCompatActivity implements UIInterface
{
    private static final int CATEGORY_CREATE = 2;
    CategoryAdapter gridAdapter;
    GridView gridview;
    FirestoreAgent fsAgent;
    MenuAdapter menuAdapter;

    //initialization of android activity
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.fsAgent = new FirestoreAgent();
        gridview =findViewById(R.id.gridview);
        //gets the category collection from firebase
        fsAgent.getCategoryCollection("",this);
        gridview.setAdapter(gridAdapter);

        //onclick listener
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String categoryName = (String)gridview.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(),TaskList.class);
                intent.putExtra("categoryName", categoryName);

                startActivity(intent);

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(),CategoryCreateView.class);
                startActivityForResult(intent,CATEGORY_CREATE);
            }

        });

        this.menuAdapter = new MenuAdapter(getApplicationContext(), this);
    }
    //onResume is triggered when this activity is brought back into focus
    @Override
    protected void onResume(){
        super.onResume();
        fsAgent.getCategoryCollection("",this);
    }

    //opens the settings/preferences menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return this.menuAdapter.onOptionSelected(item);
    }


    @Override
    public void updateObject(String fieldName, Object requestedObj) {

    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {
    }

    //
    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent)
    {
        ArrayList<String> catList = new ArrayList<>();

        for (Category cat : collectionContent)
        {
            catList.add(cat.getCategoryTitle());
        }

        gridAdapter = new CategoryAdapter(this,catList);
        gridview.setAdapter(gridAdapter);
    }

    @Override
    public void firebaseSuccess(String message_title, String message_content)
    {
        FirestoreAgent fsAgent = new FirestoreAgent();
        fsAgent.getCategoryCollection("g2x3irLzu1DTJXbymPXw",this);
    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content) {

    }
    //Add menu to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    //Receive sign-in status
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CATEGORY_CREATE)
        {
            FirestoreAgent fsAgent = new FirestoreAgent();
            fsAgent.getCategoryCollection("g2x3irLzu1DTJXbymPXw",this);
        }
    }

}
