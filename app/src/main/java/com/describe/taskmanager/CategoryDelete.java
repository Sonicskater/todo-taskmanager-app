package com.describe.taskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by camila on 2018-03-20.
 */

public class CategoryDelete extends AppCompatActivity implements UIInterface
{
    Button deleteBtn;
    Button cancelBtn;
    EditText categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category_popup);
        categoryName = (EditText) findViewById(R.id.categoryDeleteNameText);
        deleteBtn = (Button) findViewById(R.id.buttonNowDeleteCategory);
        deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Category c = new Category(new Random().nextInt(),categoryName.getText().toString());
                //dont know how to check if category already exists
                deleteCategory(c);
            }
        }
        );

        cancelBtn = (Button) findViewById(R.id.buttonCancelDeleteCategory);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void deleteCategory(Category c)
    {
        FirestoreAgent fs = FirestoreAgent.getInstance();
        fs.deleteCategory(c, this);
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
