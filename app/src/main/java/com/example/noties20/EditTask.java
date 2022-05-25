package com.example.noties20;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class EditTask extends AppCompatActivity {
    EditText task_input;
    Button update_button, delete_button;
    String id, status, text;
    Switch completed;
    String bitcheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        task_input = findViewById(R.id.title_inputtsk);
        update_button = findViewById(R.id.update_buttontsk);
        delete_button = findViewById(R.id.delete_buttontsk);
        com.example.noties20.MyDatabaseHelper myDB = new com.example.noties20.MyDatabaseHelper(EditTask.this);
        //First we call this
        getAndSetIntentData();
        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(text);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                myDB.updateTask(id,task_input.getText().toString().trim());
                finish();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("text") &&
                getIntent().hasExtra("status")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            status = getIntent().getStringExtra("status");
            text = getIntent().getStringExtra("text");

            //Setting Intent Data
            task_input.setText(text);
            bitcheck=status;

        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + text + " ?");
        builder.setMessage("Are you sure you want to delete " + text + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                com.example.noties20.MyDatabaseHelper myDB = new com.example.noties20.MyDatabaseHelper(EditTask.this);
                myDB.deleteTask(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}