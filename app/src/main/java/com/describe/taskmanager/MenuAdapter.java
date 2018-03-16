package com.describe.taskmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by bencook on 2018-03-15.
 */

public class MenuAdapter {
    private Context applicationContext;
    private AppCompatActivity parentActivity;

    public MenuAdapter(Context applicationContext, AppCompatActivity parentActivity) {

        this.applicationContext = applicationContext;
        this.parentActivity = parentActivity;
    }

    public boolean onOptionSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this.applicationContext, SettingsActivity.class);
                parentActivity.startActivity(i);
                break;
            case R.id.action_search:
                SearchFragment searchFragment =  SearchFragment.newInstance();
                searchFragment.show(parentActivity.getFragmentManager(), "search");

               // Toast toast = Toast.makeText(this.parentActivity,"hello", Toast.LENGTH_SHORT);
                //toast.show();
                break;
            default:
                break;
        }
        return true;
    }
}
