package com.describe.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by camila on 2018-03-20.
 */

public class CategoryEditView extends AppCompatActivity implements UIInterface
{
    Button createBtn;
    Button deleteBtn;
    Button cancelBtn;
    private static final int CATEGORY_EDIT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category_popup);

        createBtn = (Button) findViewById(R.id.buttonCreateCategory);
        createBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CategoryCreateView.class);
                startActivityForResult(intent, CATEGORY_EDIT);
            }
        });

        cancelBtn = (Button) findViewById(R.id.buttonCancelCategory);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        deleteBtn = (Button) findViewById(R.id.buttonDeleteCategory);
        deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), CategoryDelete.class);
                startActivityForResult(intent, CATEGORY_EDIT);
            }
        });
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
    public void firebaseSuccess(String message_title, String message_content)
    {
        this.finish();
    }

    @Override
    public void firebaseFailure(String error_code, String message_title, String extra_content)
    {
        this.finish();
    }
}
