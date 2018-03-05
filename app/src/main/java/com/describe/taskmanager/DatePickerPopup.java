package com.describe.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

@Deprecated
public class DatePickerPopup extends AppCompatActivity {

    //overridden method, initializes the date picker popup activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker_popup);
    }
}
