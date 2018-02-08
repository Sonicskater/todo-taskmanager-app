package com.describe.taskmanager;


import android.app.Dialog;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class EventCreateView extends AppCompatActivity implements UIInterface{
    Dialog datePickerDialog;
    Date chosenDate = new Date();
    Dialog timePickerDialog;
    firestoreAgent fbAgent = new firestoreAgent();
    String debug_user = "g2x3irLzu1DTJXbymPXw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datePickerDialog = new Dialog(this);
        timePickerDialog = new Dialog(this);
        TextView dateField = findViewById(R.id.eventDate);
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.setContentView(R.layout.activity_date_picker_popup);

                Button cancelButton = (Button)datePickerDialog.findViewById(R.id.btnDatePickerCancel);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.dismiss();
                    }
                });

                Button okButton = (Button)datePickerDialog.findViewById(R.id.btnDatePickerOk);
                okButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        DatePicker datePicker = datePickerDialog.findViewById(R.id.datePicker);

                        chosenDate = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth())
                                .getTime();

                        showEventDate();
                        datePickerDialog.dismiss();
                    }
                });

                datePickerDialog.show();
            }
        });
        TextView timeField = findViewById(R.id.timeText);
        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.setContentView(R.layout.activity_time_picker_popup);

                Button cancelButton = timePickerDialog.findViewById(R.id.btnTimePickerCancel);
                cancelButton.setOnClickListener (new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        timePickerDialog.dismiss();
                    }
                });

                Button okButton  = timePickerDialog.findViewById(R.id.btnTimePickerOk);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePicker timePicker = timePickerDialog.findViewById(R.id.timePicker);

                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(chosenDate);
                        cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                        chosenDate = cal.getTime();
                        showEventDate();

                        timePickerDialog.dismiss();
                    }
                });

                timePickerDialog.show();
            }
        });
    }

    public void onCreateEvent(View view) throws ParseException {
        //creates new event
        taskEvent newEvent = setProperties(new taskEvent());
        //saves the text in the field after the submit button pressed

        System.out.println("Title - " + newEvent.getTitle());
        System.out.println("Description " + newEvent.getDescription());
        System.out.println("Date - " + newEvent.getDate());
        System.out.println("Time " + newEvent.getTime());

        fbAgent.addTask(debug_user,"category",newEvent,this);



    }
    /*

    This is jank as fuck and not standard. Replace with Fragments
    Relocated to setOnClickListener(s) to resolve an xml error i can't sort out -Devon
*/
    protected void onClickEventDate(View view) {
        datePickerDialog.setContentView(R.layout.activity_date_picker_popup);

        Button cancelButton = (Button)datePickerDialog.findViewById(R.id.btnDatePickerCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.dismiss();
            }
        });

        Button okButton = (Button)datePickerDialog.findViewById(R.id.btnDatePickerOk);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePicker datePicker = datePickerDialog.findViewById(R.id.datePicker);

                chosenDate = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth())
                                      .getTime();

                showEventDate();
                datePickerDialog.dismiss();
            }
        });

        datePickerDialog.show();
    }

    protected void onClickEventTime(View view){
        timePickerDialog.setContentView(R.layout.activity_time_picker_popup);

        Button cancelButton = timePickerDialog.findViewById(R.id.btnTimePickerCancel);
        cancelButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v){
                timePickerDialog.dismiss();
            }
        });

        Button okButton  = timePickerDialog.findViewById(R.id.btnTimePickerOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = timePickerDialog.findViewById(R.id.timePicker);

                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(chosenDate);
                cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                chosenDate = cal.getTime();
                showEventDate();

                timePickerDialog.dismiss();
            }
        });

        timePickerDialog.show();
    }



    private void showEventDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        TextView date   = findViewById(R.id.eventDate);
        date.setText(dateFormat.format(chosenDate));

        TextView time = findViewById(R.id.timeText);
        time.setText(timeFormat.format(chosenDate));
    }

    private taskEvent setProperties(taskEvent event) {
        EditText title   = findViewById(R.id.titleText);
        event.setTitle(title.getText().toString());

        EditText description   = findViewById(R.id.descriptionText);
        event.setDescription(description.getText().toString());

        event.setDate(this.chosenDate);

        TextView time = findViewById(R.id.timeText);
        event.setTime(time.getText().toString());

        return event;
    }

    @Override
    public void updateField(String fieldName, String fieldContent) {

    }

    @Override
    public void updateCollection(String collectionName, String collectionContent) {

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
}
