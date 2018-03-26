package com.describe.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by camila on 2018-02-07.
 */


public class CategoryAdapter extends BaseAdapter
{
    private Context context;
    private final ArrayList<String> textViewValues;

    public CategoryAdapter(Context context, ArrayList<String> textViewValues)
    {
        this.context = context;
        this.textViewValues = textViewValues;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null)
        {
            // get reminder_notification from mobile.xml
            gridView = inflater.inflate(R.layout.grid_item_label, null);

            // set value into textview
            TextView textView =gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(textViewValues.get(position));
        }
        else
        {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount()
    {
        return textViewValues.size();
    }

    @Override
    public Object getItem(int position)
    {
        return textViewValues.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }
}