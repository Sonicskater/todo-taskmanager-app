package com.describe.taskmanager;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TaskEventView extends AppCompatActivity implements UIInterface,DateTimeInterface {
    //initialized instance varibles
    private final String TAG = "TaskEventView";
    private TaskEvent currentEvent;
    Dialog datePickerDialog;
    Date chosenDate = new Date();
    Dialog timePickerDialog;
    //firebase database agent
    FirestoreAgent fbAgent = new FirestoreAgent();
    String debug_user = "g2x3irLzu1DTJXbymPXw";

    //initialize everything that has to do with the screen (like a constructor for the screen)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final TaskEventView self = this;

        //base Android onCreate functionality
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_event_view);

        //
        this.currentEvent = (TaskEvent)getIntent().getSerializableExtra("taskEvent");
        //log to make sure that the object is passed in
        Log.d(TAG, "got the title " + this.currentEvent.getTitle());

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        this.chosenDate = currentEvent.getDate();

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        TextView date   = findViewById(R.id.dateText);
        date.setText(dateFormat.format(chosenDate));

        TextView time = findViewById(R.id.timeText);
        time.setText(timeFormat.format(chosenDate));
    }

    private TaskEvent setProperties(TaskEvent event) {
        EditText title   = findViewById(R.id.titleText);
        event.setTitle(title.getText().toString());

        EditText description   = findViewById(R.id.descriptionText);
        event.setDescription(description.getText().toString());

        event.setDate(this.chosenDate);

        TextView time = findViewById(R.id.timeText);
        event.setTime(time.getText().toString());

        return event;
    }

    public void onDelete(View view) {
        Log.d("onDelete", "onDelete: ");


        fbAgent.deleteTask("category", currentEvent,this);
    }

    public void onSave(View view) {
        Log.d("onSave", "onSave: ");
        this.setProperties();

        fbAgent.updateTask("category", currentEvent,this);
    }

    private void setProperties() {
        TaskEvent event = this.currentEvent;

        EditText title   = findViewById(R.id.titleText);
        event.setTitle(title.getText().toString());

        EditText description   = findViewById(R.id.descriptionText);
        event.setDescription(description.getText().toString());

        event.setDate(this.chosenDate);

        TextView time = findViewById(R.id.timeText);
        event.setTime(time.getText().toString());
    }

    @Override
    public void updateObject(String fieldName, Object requestedObj) {

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
}
