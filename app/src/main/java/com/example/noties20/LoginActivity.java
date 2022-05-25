package com.example.noties20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.google.mlkit.common.sdkinternal.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {
    private EditText pass,user;
    private String password,username;
    private Button signin;
    private Session session;
    private MyDatabaseHelper db;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session=new Session(this);
        pass=findViewById(R.id.passwordlog);
        user=findViewById(R.id.usernamelog);
        signin=findViewById(R.id.signinlog);
//        sp= getSharedPreferences("userprefs", Context.MODE_PRIVATE);
        db= new MyDatabaseHelper(this);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean var = db.checkUserPass(user.getText().toString() , pass.getText().toString());
                if (var){
                    //create sharedprefs to save the username to make the tasks and notes show depends on the user
//                    SharedPreferences.Editor editor=sp.edit();
//                    editor.putString("username",user.getText().toString());
//                    editor.commit();


                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                   session.setLoggedin(true);
                    startActivity(new Intent(LoginActivity.this , ActivitiesChoose.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}