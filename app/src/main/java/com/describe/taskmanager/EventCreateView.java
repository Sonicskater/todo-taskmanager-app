package com.describe.taskmanager;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class EventCreateView extends AppCompatActivity implements UIInterface,DateTimeInterface{
    //initialized instance varibles
    Dialog datePickerDialog;
    Date chosenDate = new Date();
    Dialog timePickerDialog;
    //firebase database agent
    FirestoreAgent fbAgent = new FirestoreAgent();
    String debug_user = "g2x3irLzu1DTJXbymPXw";

    //initialize everything that has to do with the screen (like a constructor for the screen)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final FragmentManager fragManager = getFragmentManager();

        //base Android onCreate functionality
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        //initalizes the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create the new date and time picker dialogs
        datePickerDialog = new Dialog(this);
        timePickerDialog = new Dialog(this);
        //the textbox in the main window, (outside of the popup)
        TextView dateField = findViewById(R.id.dateText);
        //set the onclick listener
        final DateTimeInterface self = this;
        dateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(chosenDate,self);
                datePickerFragment.show(getSupportFragmentManager(),"Frag");

            }
        });

        //the textbox in the main window (outside of popup)
        TextView timeField = findViewById(R.id.timeText);
        //sets the onclick listener to pop up the time picker fragment

        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(chosenDate,self);
                timePickerFragment.show(getSupportFragmentManager(),"Frag");

            }
        });
    }

    public void onCreateEvent(View view) throws ParseException {
        //creates new event
        TaskEvent newEvent = setProperties(new TaskEvent());
        //saves the text in the field after the submit button pressed

        //Uploads task to Firestore
        fbAgent.addTask(debug_user,"category",newEvent,this);

    }

    //this method just formats the date and times into readable strings
    private void showEventDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        TextView date   = findViewById(R.id.dateText);
        date.setText(dateFormat.format(chosenDate));

        TextView time = findViewById(R.id.timeText);
        time.setText(timeFormat.format(chosenDate));
    }

    //sets the object properties based on what the user typed/picked on the screen
    private TaskEvent setProperties(TaskEvent event) {
        EditText title   = findViewById(R.id.titleText);
        event.setTitle(title.getText().toString());

        EditText description   = findViewById(R.id.descriptionText);
        event.setDescription(description.getText().toString());

        event.setDate(this.chosenDate);

        TextView time = findViewById(R.id.timeText);
        event.setTime(time.getText().toString());

        //returns the event with filled out fields
        return event;
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
        Toast toast = Toast.makeText(EventCreateView.this,message_title, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content) {
        Toast toast = Toast.makeText(EventCreateView.this,message_title, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void passDateTime(Date passedDate) {
        this.chosenDate = passedDate;
        this.showEventDate();
    }
}
