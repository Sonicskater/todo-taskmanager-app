package com.describe.taskmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class CategoryCreateView extends AppCompatActivity implements FSNotificationInterface
{
    Button createBtn;
    Button cancelBtn;
    EditText categoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category_popup);
        categoryName = findViewById(R.id.categoryCreateNameText);
        createBtn = findViewById(R.id.buttonCreateNewCategory);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Makes sure that the category tittle is a reasonable length.
                if (categoryName.getText().length() >= 21)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Name of your category is too long";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if (categoryName.getText().length() <= 0)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter a name for your category";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                {
                    Category c = new Category(new Random().nextInt(),categoryName.getText().toString());
                    createCategory(c);
                }
            }}
        );

        cancelBtn = findViewById(R.id.buttonCancelCreateCategory);
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
        fs.addCategory(c,this);
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
