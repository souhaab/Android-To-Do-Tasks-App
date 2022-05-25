package com.example.noties20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTasks extends AppCompatActivity {
    EditText task_input;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        task_input = findViewById(R.id.newtaskinput);
        add_button = findViewById(R.id.buttonsave24);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new com.example.noties20.MyDatabaseHelper(AddTasks.this);
                myDB.addTask(task_input.getText().toString());
            }
        });
    }


    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,TasksActivity.class));
        finish();
    }
}