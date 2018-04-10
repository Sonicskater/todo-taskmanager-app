package com.describe.taskmanager;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.Locale;


public class
 TaskEventView extends AppCompatActivity implements FSNotificationInterface,DateTimeInterface,NotifyInterface {
    //initialized instance variables

    private TaskEvent currentEvent;
    private String category;
    Dialog datePickerDialog;
    Date chosenDate = new Date();
    Dialog timePickerDialog;
    //firebase database agent
    FirestoreAgent fbAgent = FirestoreAgent.getInstance();


    //initialize everything that has to do with the screen (like a constructor for the screen)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final TaskEventView self = this;

        //base Android onCreate functionality
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_event_view);

        //
        this.currentEvent = (TaskEvent)getIntent().getSerializableExtra("taskEvent");
        this.category = (String)getIntent().getSerializableExtra("category");


        //create the new date and time picker dialogs
        datePickerDialog = new Dialog(this);
        timePickerDialog = new Dialog(this);
        //the textbox in the main window, (outside of the popup)
        TextView dateField = findViewById(R.id.dateText);
        //sets the onclick listener
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment timePickerFragment = DatePickerFragment.newInstance(chosenDate, self);
                timePickerFragment.show(getSupportFragmentManager(), "Frag");
            }
        });
        TextView timeField = findViewById(R.id.timeText);
        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(chosenDate,self);
                timePickerFragment.show(getSupportFragmentManager(),"Frag");
            }
        });

        this.updateUIFields(currentEvent);
    }

    private void updateUIFields(TaskEvent currentEvent) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());

        this.chosenDate = currentEvent.getAlarmDate();

        setText(currentEvent.getTitle(), R.id.titleText);
        setText(currentEvent.getDescription(), R.id.descriptionText);
        setText(dateFormat.format(chosenDate), R.id.dateText);
        setText(timeFormat.format(chosenDate), R.id.timeText);
    }

    private void setText(String value, int id) {
        TextView aTextView = findViewById(id);
        aTextView.setText(value);
    }

    private void showEventDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());

        TextView date   = findViewById(R.id.dateText);
        date.setText(dateFormat.format(chosenDate));

        TextView time = findViewById(R.id.timeText);
        time.setText(timeFormat.format(chosenDate));
    }

    public void onDelete(View view) {
        Log.d("onDelete", "onDelete: ");

        CalendarAgent.getInstance().deleteEvent(this,currentEvent,getApplicationContext());
        if(currentEvent.hasImage()){
            FBStorageAgent.getInstance().deleteImage(currentEvent);
        }

        fbAgent.deleteTask(this.category, currentEvent,this);
    }

    public void onSave(View view) {
        Log.d("onSave", "onSave: ");
        this.setProperties();
        CalendarAgent.getInstance().updateEvent(this,currentEvent,getApplicationContext());

        fbAgent.updateTask(this.category, currentEvent,this);
    }

    private void setProperties() {
        TaskEvent event = this.currentEvent;


        event.setTitle(getTextValue(R.id.titleText));
        
        event.setDescription(getTextValue(R.id.descriptionText));

        event.setCreateTime(getTextValue(R.id.timeText));

        event.setAlarmDate(this.chosenDate);
    }

    //function to simply extract the text from a textfield turn it into a string
    private String getTextValue(int fieldId) {
        TextView field = findViewById(fieldId);
        return field.getText().toString();
    }


    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent) {

    }

    @Override
    public void firebaseSuccess(String message_title, String message_content) {
        Toast toast = Toast.makeText(TaskEventView.this,message_title, Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content) {
        Toast toast = Toast.makeText(TaskEventView.this,message_title, Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public void passDateTime(Date passedDate) {
        this.chosenDate = passedDate;
        this.showEventDate();
    }

    @Override
    public void NotifyUser(String out) {
    }
}
