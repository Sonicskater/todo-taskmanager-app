package com.describe.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.describe.taskmanager.category;

/**
 * Created by camila on 2018-02-07.
 */

/*public class ContentAdapter extends BaseAdapter
{
    private Context mContext;

    public ContentAdapter(Context c)
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

    private String[] categories = {"My friends category", "Second Category", "Yoho"};

}*/

public class ContentAdapter extends BaseAdapter
{
    private Context context;
    private final String[] textViewValues;

    public ContentAdapter(Context context, String[] textViewValues)
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

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.grid_item_label, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(textViewValues[position]);
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
        return textViewValues.length;
    }

    @Override
    public Object getItem(int position)
    {
        return textViewValues[position];
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }
}
