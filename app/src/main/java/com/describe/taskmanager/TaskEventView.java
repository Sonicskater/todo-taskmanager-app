package com.describe.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TaskEventView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_event_view);
    }

    public void onDelete(View view) {
        Log.d("onDelete", "onDelete: ");
    }

    public void onSave(View view) {
        Log.d("onSave", "onSave: ");
    }
}
