package com.describe.taskmanager;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EventCreateView extends AppCompatActivity implements UIInterface,DateTimeInterface{
    //initialized instance varibles
    Dialog datePickerDialog;
    Date chosenDate = new Date();
    Dialog timePickerDialog;
    //firebase database agent
    Spinner categoriesSpinner;
    FirestoreAgent fbAgent = new FirestoreAgent();
    String debug_user = "g2x3irLzu1DTJXbymPXw";

    //initialize everything that has to do with the screen (like a constructor for the screen)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        categoriesSpinner = findViewById(R.id.categoriesSpinner);
        fbAgent.getCategoryCollection("",this);
        //the textbox in the main window, (outside of the popup)
        TextView dateField = findViewById(R.id.dateText);
        //set the onclick listener for date picker
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

        //Handle share intents from system.
        if (getIntent().getExtras()!=null) {
            Intent intent = getIntent();
            String action = intent.getAction();
            //Handle text share
            if (action.equals(Intent.ACTION_SEND) && intent.getType().equals("text/plain")) {
                EditText desc = findViewById(R.id.descriptionText);
                desc.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
            }
        }
    }

    //NOTE: this is wired to the save button in the event_view xml file, not manually with code
    public void onCreateEvent(View view) throws ParseException
    {
        //creates new event
        TaskEvent newEvent = buildTask();
        //saves the text in the field after the submit button pressed

        //Uploads task to Firestore
        fbAgent.addTask(debug_user,categoriesSpinner.getSelectedItem().toString(),newEvent,this);


    }

    //this method just formats the date and times into readable strings
    private void showEventDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        TextView date   = findViewById(R.id.dateText);
        date.setText(dateFormat.format(chosenDate));

        TextView time = findViewById(R.id.timeText);
        time.setText(timeFormat.format(chosenDate));
    }

    //sets the object properties based on what the user typed/picked on the screen
    private TaskEvent buildTask()
    {
        String title = getTextValue(R.id.titleText);
        String description = getTextValue(R.id.descriptionText);
        String time = getTextValue(R.id.timeText);

        //should update this so it updates the task with it's category from the drop down menu

        //returns the task with filled out fields
        return new TaskEvent(title, description, this.chosenDate, time);
    }

    //function to simply extract the text from a textfield turn it into a string
    private String getTextValue(int fieldId) {
        TextView field = findViewById(fieldId);
        return field.getText().toString();
    }

    //dont need this, an event doesn't need to know its category
    /*
    //to get the category as a string out from the drop down menu
    //IT WORKS :D String tho?
    private String getCategoryFromSpinner(Spinner mySpinner){

        String text = mySpinner.getSelectedItem().toString();
        //System.out.println(text);
        return text;
    }
    */

    @Override
    public void updateObject(String fieldName, Object requestedObj) {

    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent) {

    }

    //what is this method??
    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent)
    {
        String[] items = new String[collectionContent.size()];
        for (int i = 0; i< collectionContent.size();i++){
            items[i] = collectionContent.get(i).getCategoryTitle();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,items);
        categoriesSpinner.setAdapter(adapter);
    }

    @Override
    public void firebaseSuccess(String message_title, String message_content)
    {
        Toast toast = Toast.makeText(EventCreateView.this,message_title, Toast.LENGTH_SHORT);
        toast.show();
        finish();
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
