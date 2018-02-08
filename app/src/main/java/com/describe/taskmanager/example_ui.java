package com.describe.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.core.CrashlyticsCore;
/*
This file is meant as an example of how to implement the UIInterface interface and for tesing the database, NOT MEANT FOR PRODUCTION USE
 */

public class example_ui extends AppCompatActivity implements UIInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Run on the creation of this activity
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_screen);
        final UIInterface self = this;
        final firestoreAgent fbAgent = new firestoreAgent();
        final Button getUserButton = findViewById(R.id.getUserButton);
        getUserButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                fbAgent.getUserDocument("g2x3irLzu1DTJXbymPXw",self,"user_field");
                fbAgent.getCategoryDocument("g2x3irLzu1DTJXbymPXw",self,"category_field");
                fbAgent.getTaskDocument("g2x3irLzu1DTJXbymPXw",self,"task_field");
                fbAgent.getCategoryCollection("g2x3irLzu1DTJXbymPXw",self);
                fbAgent.getTaskCollection("g2x3irLzu1DTJXbymPXw",self,"category");
                task taskObj = new task("g2x3irLzu1DTJXbymPXw","test_task_001","task for testing firebase system",1);
                //fbAgent.addTask("g2x3irLzu1DTJXbymPXw","category",taskObj,self);
                category categoryObj = new category(001,"debug_category_001");
                fbAgent.addCategory("g2x3irLzu1DTJXbymPXw",categoryObj,self);
            }
        });
    }

    @Override
    public void updateField(String fieldName, String fieldContent) {
        Log.d("Interface_TEST",(fieldName.toString()+" "+fieldContent.toString()));
    }
    @Override
    public void updateCollection(String address, String content){
        Log.d("Interface_TEST",address);
        Log.d("Interface_TEST",content);
        

    }

    @Override
    public void firebaseSuccess(String message_title, String message_content) {
        Log.d(message_title,message_content);
    }
    @Override
    public void firebaseFailure(String error_code,String message_title, String message_content) {
        Log.d(message_title,message_content);
    }
}
