package com.describe.taskmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class CategoryCreateView extends AppCompatActivity implements UIInterface
{
    Button createBtn;
    Button cancelBtn;
    EditText categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category_popup);
        categoryName = (EditText) findViewById(R.id.categoryNameText);
        createBtn = (Button) findViewById(R.id.buttonCreateCategory);
        createBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(categoryName.getText().length()>0)
                {
                    Category c = new Category(new Random().nextInt(),categoryName.getText().toString());
                    createCategory(c);
                }
            }
        }
        );

        cancelBtn = (Button) findViewById(R.id.buttonCancelCreateCategory);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void createCategory(Category c)
    {
        FirestoreAgent fs = FirestoreAgent.getInstance();
        fs.addCategory("",c,this);
    }

    @Override
    public void updateObject(String fieldName, Object requestedObj)
    {

    }

    @Override
    public void updateTaskCollection(String collectionName, ArrayList<TaskEvent> collectionContent)
    {

    }

    @Override
    public void updateCategoryCollection(String collectionName, ArrayList<Category> collectionContent)
    {

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

