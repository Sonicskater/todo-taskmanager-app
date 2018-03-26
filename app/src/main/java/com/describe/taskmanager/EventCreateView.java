package com.describe.taskmanager;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class EventCreateView extends AppCompatActivity implements UIInterface,DateTimeInterface{
    //initialized instance variables
    Date chosenDate = new Date();


    Spinner categoriesSpinner;
    FirestoreAgent fbAgent = FirestoreAgent.getInstance();
    String debug_user = "";

    //initialize everything that has to do with the screen (like a constructor for the screen)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        //base Android onCreate functionality
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        //initalizes the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create the new date and time picker dialogs

        categoriesSpinner = findViewById(R.id.categoriesSpinner);
        fbAgent.getCategoryCollection("",this);

        //the textbox in the main window, (outside of the popup)
        final TextView dateField = findViewById(R.id.dateText);
        final TextView dateTitle = findViewById(R.id.textView7);
        final TextView timeTitle = findViewById(R.id.textView8);
        //set the onclick listener for date picker
        final DateTimeInterface self = this;
        dateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox check = findViewById(R.id.setAlarm);
                if (check.isChecked()) {
                    DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(chosenDate, self);
                    datePickerFragment.show(getSupportFragmentManager(), "Frag");
                }
            }
        });

        //the textbox in the main window (outside of popup)
        final TextView timeField = findViewById(R.id.timeText);
        //sets the onclick listener to pop up the time picker fragment

        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckBox check = findViewById(R.id.setAlarm);
                if(check.isChecked()) {
                    TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(chosenDate, self);
                    timePickerFragment.show(getSupportFragmentManager(), "Frag");
                }

            }
        });
        timeField.setAlpha(0.2f);
        timeTitle.setAlpha(0.2f);
        dateField.setAlpha(0.2f);
        dateTitle.setAlpha(0.2f);
        final CheckBox check = findViewById(R.id.setAlarm);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check.isChecked()){
                    timeField.setAlpha(0.2f);
                    timeTitle.setAlpha(0.2f);
                    dateField.setAlpha(0.2f);
                    dateTitle.setAlpha(0.2f);
                }
                else{
                    timeField.setAlpha(1f);
                    timeTitle.setAlpha(1f);
                    dateField.setAlpha(1f);
                    dateTitle.setAlpha(1f);
                }
            }
        });

        //Handle share intents from system.
        if (getIntent().getExtras()!=null) {
            @NonNull
            Intent intent = getIntent();

            String action = intent.getAction();
            //Handle text share
            //Added null checks
            if (action!=null && action.equals(Intent.ACTION_SEND) && intent.getType()!= null && intent.getType().equals("text/plain")) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());

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
        CheckBox check = findViewById(R.id.setAlarm);
        if (check.isChecked()) {
            return new TaskEvent(title, description, Calendar.getInstance().getTime(), time,this.chosenDate);
        }

        return new TaskEvent(title, description, Calendar.getInstance().getTime(), time);
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

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent)
    {
        String[] items = new String[collectionContent.size()];
        for (int i = 0; i< collectionContent.size();i++){
            items[i] = collectionContent.get(i).getCategoryTitle();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,items);
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
