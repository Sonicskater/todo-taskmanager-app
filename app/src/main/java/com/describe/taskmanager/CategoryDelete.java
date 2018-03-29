package com.describe.taskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Random;


public class CategoryDelete extends AppCompatActivity implements FSNotificationInterface
{
    Button deleteBtn;
    Button cancelBtn;
    EditText categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category_popup);
        categoryName =  findViewById(R.id.categoryDeleteNameText);
        deleteBtn =  findViewById(R.id.buttonNowDeleteCategory);
        deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Category c = new Category(new Random().nextInt(),categoryName.getText().toString());
                deleteCategory(c);
            }
        }
        );

        cancelBtn =  findViewById(R.id.buttonCancelDeleteCategory);
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
