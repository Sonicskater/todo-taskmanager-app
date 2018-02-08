package com.describe.taskmanager;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class emptyScreen extends AppCompatActivity {
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_empty_screen);

        // Locate the button in activity_main.xml
        button = (Button) findViewById(R.id.newActivityButton);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(emptyScreen.this,
                        EventCreateView.class);
                startActivity(myIntent);
            }
        });
    }

}