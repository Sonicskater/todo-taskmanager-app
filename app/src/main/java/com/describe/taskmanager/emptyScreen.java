package com.describe.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class emptyScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_screen);
    }

    public void goToCategoryList(View v)
    {
        Intent i = new Intent(getApplicationContext(), CategoryList.class);
        startActivity(i);

    }
}
