package com.example.noties20;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;

public class AddActivity extends AppCompatActivity {

    EditText title_input, text_input;
    Button add_button;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title_input = findViewById(R.id.title_input);
        text_input = findViewById(R.id.text_input);
        add_button = findViewById(R.id.add_button1);
        add_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //long createdTime = System.currentTimeMillis();
                calendar = Calendar.getInstance();
                dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                date = dateFormat.format(calendar.getTime());
                MyDatabaseHelper myDB = new com.example.noties20.MyDatabaseHelper(AddActivity.this);
                myDB.addBook(title_input.getText().toString().trim(),
                        text_input.getText().toString().trim(),
                        String.valueOf(date));
            }
        });
    }

}
