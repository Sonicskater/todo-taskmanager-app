package com.describe.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TaskEventView extends AppCompatActivity {
    private final String TAG = "TaskEventView";
    private TaskEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_event_view);

        this.event = (TaskEvent)getIntent().getSerializableExtra("taskEvent");
        Log.d(TAG, "got the task " + this.event.getTitle());
    }

    public void onDelete(View view) {
        Log.d("onDelete", "onDelete: ");
    }

    public void onSave(View view) {
        Log.d("onSave", "onSave: ");
    }
}
