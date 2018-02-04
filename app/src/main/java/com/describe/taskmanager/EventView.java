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
import android.widget.Toast;

import com.describe.taskmanager.domain.Event;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;


public class EventView extends AppCompatActivity {
    Dialog datePickerDialog;
    Date chosenDate ;
    Dialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datePickerDialog = new Dialog(this);
        timePickerDialog = new Dialog(this);
    }

    public void onCreateEvent(View view) throws ParseException {
        //creates new event
        Event newEvent = setProperties(new Event());
        //saves the text in the field after the submit button pressed

        System.out.println("Title - " + newEvent.getTitle());
        System.out.println("Description " + newEvent.getDescription());
        System.out.println("Date - " + newEvent.getDate());
        System.out.println("Time " + newEvent.getTime());
    }

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
                timePickerDialog.dismiss();
            }
        });

        timePickerDialog.show();
    }

    private Event setProperties(Event event) {
        EditText title   = findViewById(R.id.titleText);
        event.setTitle(title.getText().toString());

        EditText description   = findViewById(R.id.descriptionText);
        event.setDescription(description.getText().toString());

        TextView date   = findViewById(R.id.eventDate);
        event.setDate(this.chosenDate);

        TextView time = findViewById(R.id.timeText);
        event.setTime(time.getText().toString());

        return event;
    }
}
