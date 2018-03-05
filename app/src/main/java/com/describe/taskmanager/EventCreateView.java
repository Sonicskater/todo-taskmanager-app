package com.describe.taskmanager;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class EventCreateView extends AppCompatActivity implements UIInterface
{
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
        //set the onclick listener
        dateField.setOnClickListener(new View.OnClickListener() {
            //onclick implementation of datePicker diaglog
            @Override
            public void onClick(View view) {

                //sets content view to the popup
                datePickerDialog.setContentView(R.layout.activity_date_picker_popup);

                //onclick event for the datepicker dialog cancel button
                Button cancelButton = datePickerDialog.findViewById(R.id.btnDatePickerCancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if cancel is clicked, remove popup
                        datePickerDialog.dismiss();
                    }
                });

                //onlclick event for the datepicker dialog save button
                Button okButton = datePickerDialog.findViewById(R.id.btnDatePickerOk);
                okButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        DatePicker datePicker = datePickerDialog.findViewById(R.id.datePicker);

                        //on the save/ok button, sets the date chosen
                        chosenDate = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth())
                                .getTime();

                        //formats the date into a string to be placed in the textfield on the main window
                        showEventDate();
                        //if save is clicked, remove popup
                        datePickerDialog.dismiss();
                    }
                });

                //dialog is shown after all of the onclicks are defined
                datePickerDialog.show();
            }
        });

        //the textbox in the main window (outside of popup)
        TextView timeField = findViewById(R.id.timeText);
        //sets the onclick listener to pop up the time picker
        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets the content view to the popup
                timePickerDialog.setContentView(R.layout.activity_time_picker_popup);

                //cancel button and onclick listener
                Button cancelButton = timePickerDialog.findViewById(R.id.btnTimePickerCancel);
                cancelButton.setOnClickListener (new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        //dismisses if cancel is clicked
                        timePickerDialog.dismiss();
                    }
                });

                //save button and onclikc listener
                Button okButton  = timePickerDialog.findViewById(R.id.btnTimePickerOk);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePicker timePicker = timePickerDialog.findViewById(R.id.timePicker);

                        //note time is also stored in a GregorianCalender object
                        GregorianCalendar cal = new GregorianCalendar();
                        //sets the time and feeds in the chosen date (not really necessary to feed date in but easier to format)
                        cal.setTime(chosenDate);
                        cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());


                        chosenDate = cal.getTime();
                        //formats the time into a string to be placed in the textfield on the main window
                        showEventDate();

                        //dismisses is save is pressed
                        timePickerDialog.dismiss();
                    }
                });

                //dialog is shown after all of the onclicks are defined
                timePickerDialog.show();
            }
        });
    }

    public void onCreateEvent(View view) throws ParseException
    {
        //creates new event
        TaskEvent newEvent = setProperties(new TaskEvent());
        //saves the text in the field after the submit button pressed

        //Uploads task to Firestore
        fbAgent.addTask(debug_user,"category",newEvent,this);

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
    private TaskEvent setProperties(TaskEvent event)
    {
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
    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content)
    {
        Toast toast = Toast.makeText(EventCreateView.this,message_title, Toast.LENGTH_SHORT);
        toast.show();
    }
}
