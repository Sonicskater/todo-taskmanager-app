package com.describe.taskmanager;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.describe.taskmanager.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EventView extends AppCompatActivity {
    Dialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datePickerDialog = new Dialog(this);
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
        datePickerDialog.show();
    }

    private void ShowToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private Event setProperties(Event event) {
        EditText title   = findViewById(R.id.titleText);
        event.setTitle(title.getText().toString());

        EditText description   = findViewById(R.id.descriptionText);
        event.setDescription(description.getText().toString());

        try {
            TextView date   = findViewById(R.id.eventDate);
            event.setDate(parseDate(date.getText().toString()));
        } catch (ParseException ex) {
            System.out.println("error parsing the date");
            ShowToast("Invalid Date.  dd-MM-yyyy expected");
        }

        EditText time = findViewById(R.id.timeText);
        event.setTime(time.getText().toString());

        return event;
    }

    private Date parseDate(String value) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.parse(value);
    }
}
