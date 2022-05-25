package com.example.noties20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksActivity  extends AppCompatActivity{
private FloatingActionButton addbtn;
private MyDatabaseHelper myDB;
ArrayList<String> task_id, task_text;
ArrayList<Integer> task_status;
private TaskAdapter adapter;
private RecyclerView mRecyclerview;
ImageView empty_imageview;
TextView no_data;
private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        addbtn=findViewById(R.id.fab);
//        View addlayout= LayoutInflater.from(this).inflate(R.layout.add_task_layout,null);
//        EditText task= addlayout.findViewById(R.id.newtaskinput);
        mRecyclerview=findViewById(R.id.recyclerviewtasks);
        myDB = new MyDatabaseHelper(this);
        myDB = new MyDatabaseHelper(TasksActivity.this);
        task_id = new ArrayList<>();
        task_text = new ArrayList<>();
        task_status = new ArrayList<>();

//        SharedPreferences sp=getApplicationContext().getSharedPreferences("userprefs",Context.MODE_PRIVATE);
//        String name=sp.getString("username","");
//        Toast.makeText(activity, name, Toast.LENGTH_SHORT).show();



        adapter = new TaskAdapter(TasksActivity.this,this, task_id, task_text, task_status);
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(TasksActivity.this));

        storeDataInArrays();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TasksActivity.this,AddTasks.class));
                finish();
           }
       });




    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllTask();
            while (cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_text.add(cursor.getString(1));
                task_status.add(cursor.getInt(2));
            }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu3, menu);
        MenuItem menuItem = menu.findItem(R.id.delete_all);
        MenuItem menudeleteun = menu.findItem(R.id.delete_all_unfinished);
        MenuItem menudeletefin = menu.findItem(R.id.delete_all_finished);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        else if(item.getItemId() == R.id.delete_all_finished)
        {
            confirmDialog2();
        }
        else if(item.getItemId() == R.id.delete_all_unfinished)
        {
            confirmDialog3();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(TasksActivity.this);
                myDB.deleteAllTask();

                //Refresh Activity
                Intent intent = new Intent(TasksActivity.this, TasksActivity.class);
                startActivity(intent);
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

    void confirmDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all finished tasks?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(TasksActivity.this);
                myDB.deleteFinishedTask();
                //Refresh Activity
                Intent intent = new Intent(TasksActivity.this, TasksActivity.class);
                startActivity(intent);
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

    void confirmDialog3(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all unfinished tasks?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(TasksActivity.this);
                myDB.deleteUnfinishedTask();
                //Refresh Activity
                Intent intent = new Intent(TasksActivity.this, TasksActivity.class);
                startActivity(intent);
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

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,ActivitiesChoose.class));
        finish();
    }

}