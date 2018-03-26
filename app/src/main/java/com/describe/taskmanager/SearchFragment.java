package com.describe.taskmanager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static com.describe.taskmanager.QueryResult.searchPriority.DEFAULT;

public class SearchFragment extends DialogFragment implements View.OnClickListener,TextWatcher {

    private View view;
    private List<Category> listCategoryHeader;
    private HashMap<Category, List<TaskEvent>> taskList;
    private SearchExpandableListAdapter listAdapter;
    TextView searchText;
    private QueryAgent qAgent;

    public SearchFragment() {
        qAgent = QueryAgent.getInstance();
        // Required empty public constructor
        listCategoryHeader = new ArrayList<>();
        taskList = new HashMap<>();


    }

    private void FillData(HashMap<Category,ArrayList<TaskEvent>> data) {

        if (this.getActivity()!=null) {
            listCategoryHeader = new ArrayList<>();
            taskList = new HashMap<>();
            Log.d("searchFrag", data.toString());
            for (Category cat : data.keySet()) {

                List<TaskEvent> tempList = new ArrayList<>();
                tempList.addAll(data.get(cat));
                if (tempList.size() != 0) {
                    listCategoryHeader.add(cat);
                    taskList.put(cat, tempList);
                }
            }
            ExpandableListView elv = view.findViewById(R.id.list);
            listAdapter = new SearchExpandableListAdapter(this.getActivity().getBaseContext(), listCategoryHeader, taskList);
            elv.setAdapter(listAdapter);
            int count = listAdapter.getGroupCount();
            for (int i = 0; i < count; i++)
                elv.expandGroup(i);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ExpandableListView elv = view.findViewById(R.id.list);
        if (this.getActivity()!=null) {
            listAdapter = new SearchExpandableListAdapter(this.getActivity().getBaseContext(), listCategoryHeader, taskList);
            elv.setAdapter(listAdapter);
        }

        Button search = view.findViewById(R.id.searchButton);
        search.setOnClickListener(this);

        searchText =  view.findViewById(R.id.searchText);
        searchText.addTextChangedListener(this);
        return view;
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {

        String searchString = this.searchText.getText().toString();
        if (searchString.length() != 0) {
            ArrayList<QueryTerm> qTerms = new ArrayList<>();
            qTerms.add(new StringTerm(searchString, true, true));
            QueryResult qResult = qAgent.Query(qTerms);
            HashMap<Category, ArrayList<TaskEvent>> result = qResult.getResultWithCategories(DEFAULT);
            FillData(result);
        }
        else{
            FillData(new HashMap<Category, ArrayList<TaskEvent>>());
        }


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            this.onClick(this.view);


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
