package com.describe.taskmanager;

/**
 * Created by bencook on 2018-03-18.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class SearchExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Category> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Category, List<TaskEvent>> _listDataChild;

    SearchExpandableListAdapter(Context context, List<Category> listDataHeader,
                                       HashMap<Category, List<TaskEvent>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final TaskEvent childText = (TaskEvent) getChild(groupPosition, childPosition);
        final Category category = getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.layout_tasklist_item, null);
            }
        }

        if (convertView != null){
            TextView txtListChild = convertView
                    .findViewById(R.id.lblListItem);

        txtListChild.setText(childText.getTitle());

        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(parent.getContext(), TaskEventView.class);
                i.putExtra("taskEvent", childText);
                i.putExtra("category", category.getCategoryTitle());

                parent.getContext().startActivity(i);
            }
        });
    }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Category getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Category headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (infalInflater != null) {
                convertView = infalInflater.inflate(R.layout.layout_category_group, null);
            }
        }

        if (convertView!=null) {
            TextView lblListHeader = convertView
                    .findViewById(R.id.lblListHeader);

            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getCategoryTitle());
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

