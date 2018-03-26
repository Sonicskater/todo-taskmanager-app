package com.describe.taskmanager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class CategoryList extends Fragment implements UIInterface, SwipeRefreshLayout.OnRefreshListener
{
    private static final int CATEGORY_EDIT = 2;
    CategoryAdapter gridAdapter;
    GridView gridview;
    FirestoreAgent fsAgent;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup, Bundle savedState)
    {
        View view = inflater.inflate(R.layout.activity_category_list,viewGroup,false);

        refreshLayout = view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);

        this.fsAgent = FirestoreAgent.getInstance();
        gridview = view.findViewById(R.id.gridview);
        //gets the category collection from firebase

        fsAgent.getCategoryCollection("",this);
        this.onRefresh();

        gridview.setAdapter(gridAdapter);

        //onclick listener
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String categoryName = (String)gridview.getItemAtPosition(position);
                if (getActivity()!=null) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), TaskList.class);
                    intent.putExtra("categoryName", categoryName);

                    startActivity(intent);
                }

            }
        });



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (getActivity()!=null)
                {
                    Intent intent = new Intent(getActivity().getApplicationContext(), CategoryEditView.class);
                    startActivityForResult(intent, CATEGORY_EDIT);
                }
            }

        });
        return view;
    }


    //onResume is triggered when this activity is brought back into focus
    @Override
    public void onResume(){
        super.onResume();
        this.onRefresh();
    }

    //opens the settings/preferences menu



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
        if (getActivity()!=null) {
            gridAdapter = new CategoryAdapter(getActivity().getApplicationContext(), catList);
            gridview.setAdapter(gridAdapter);
        }
        //end the refresh animation
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void firebaseSuccess(String message_title, String message_content)
    {
        FirestoreAgent fsAgent = FirestoreAgent.getInstance();
        fsAgent.getCategoryCollection("",this);
    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content) {

    }
    //Add menu to action bar


    //Called when refreshed via gesture
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        fsAgent.getCategoryCollection("",this);
    }

    public static CategoryList newInstance()
    {
        return new CategoryList();
    }
}
