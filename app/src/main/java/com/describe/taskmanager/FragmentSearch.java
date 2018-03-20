package com.describe.taskmanager;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentSearch extends DialogFragment implements View.OnClickListener {

    private List<String> listCategoryHeader;
    private HashMap<String, List<String>> taskList;
    private SearchExpandableListAdapter listAdapter;
    TextView searchText;


    public FragmentSearch() {
        // Required empty public constructor
        listCategoryHeader = new ArrayList<String>();
        taskList = new HashMap<String, List<String>>();

        FillData();
    }

    private void FillData() {
        listCategoryHeader.add("Category 1");
        listCategoryHeader.add("Category 2");
        listCategoryHeader.add("Category 3");

        List<String> task1Items = new ArrayList<>();
        task1Items.add("Task 1");
        task1Items.add("Task 2");

        List<String> task2Items = new ArrayList<>();
        task2Items.add("Buy Milk");


        List<String> task3Items = new ArrayList<>();
        task3Items.add("Task a");
        task3Items.add("Task b");
        task3Items.add("Task c");


        taskList.put(listCategoryHeader.get(0), task1Items);
        taskList.put(listCategoryHeader.get(1), task2Items);
        taskList.put(listCategoryHeader.get(2), task3Items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ExpandableListView elv = view.findViewById(R.id.list);
        listAdapter = new SearchExpandableListAdapter(this.getActivity().getBaseContext(), listCategoryHeader, taskList);
        elv.setAdapter(listAdapter);

        Button search = view.findViewById(R.id.searchButton);
        search.setOnClickListener(this);

        searchText =  view.findViewById(R.id.searchText);
        return view;
    }

    public static FragmentSearch newInstance() {
        FragmentSearch fragment = new FragmentSearch();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {

        String searchString = this.searchText.getText().toString();
        if (searchString.length() == 0) return;

        Toast toast = Toast.makeText(this.getActivity(),searchString, Toast.LENGTH_SHORT);
        toast.show();


    }
}
