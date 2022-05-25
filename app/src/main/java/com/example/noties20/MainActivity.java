package com.example.noties20;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    MyDatabaseHelper myDB;
    ArrayList<String> note_id, note_title, note_text, note_time,note_id_search, note_title_search, note_text_search, note_time_search;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView1);
        add_button = findViewById(R.id.add_button2);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);

//                startActivity(new Intent(MainActivity.this,AddTask.class));
//                finish();
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        note_id = new ArrayList<>();
        note_title = new ArrayList<>();
        note_text = new ArrayList<>();
        note_time = new ArrayList<>();



        customAdapter = new CustomAdapter(MainActivity.this,this, note_id, note_title, note_text, note_time);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        storeDataInArrays();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_text.add(cursor.getString(2));
                note_time.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.delete_all);
        MenuItem menudeleteun = menu.findItem(R.id.delete_all_unfinished);
        MenuItem menudeletefin = menu.findItem(R.id.delete_all_finished);

//        MenuItem menuSearch = menu.findItem(R.id.menu_search);
//        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuSearch);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//
//                Cursor cursor = myDB.searchNote(s,s);
//                if(cursor.getCount() == 0){
//                    empty_imageview.setVisibility(View.VISIBLE);
//                    no_data.setVisibility(View.VISIBLE);
//                }else{
//                    while (cursor.moveToNext()){
//                        note_id.add(cursor.getString(0));
//                        note_title.add(cursor.getString(1));
//                        note_text.add(cursor.getString(2));
//                        note_time.add(cursor.getString(3));
//                    }
//                    empty_imageview.setVisibility(View.GONE);
//                    no_data.setVisibility(View.GONE);
//                }
//
//
//                customAdapter = new CustomAdapter(MainActivity.this,getApplicationContext(), note_id, note_title, note_text, note_time);
//                recyclerView.setAdapter(customAdapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//                return true;
//            }
//        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
