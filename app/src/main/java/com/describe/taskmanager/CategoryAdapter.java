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
            // get layout from mobile.xml
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

//Old code, not sure whether i can delete or if Camila needs for reference -Devon

/*public class CategoryAdapter extends BaseAdapter
{
    private Context mContext;

    public CategoryAdapter(Context c)
    {
        mContext = c;
    }

    public int getCount()
    {
        return categories.length;
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView textView;
        if (convertView == null)
        {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView.setPadding(8, 8, 8, 8);
        }
        else
        {
            textView = (TextView) convertView;
        }
       // TextView helloTextView = (TextView) findViewById(R.id.text_view_id);
       // helloTextView.setText(R.string.user_greeting);
        return textView;
    }

    private String[] categories = {"My friends Category", "Second Category", "Yoho"};

}*/
