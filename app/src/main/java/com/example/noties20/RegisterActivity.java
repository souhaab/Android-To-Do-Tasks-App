package com.example.noties20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
private EditText pass,conpass,user;
private String password,password2,username;
private Button signin,signup;
private Session session;
private MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user=findViewById(R.id.username);
        conpass=findViewById(R.id.confpass);
        pass=findViewById(R.id.password);
        signin=findViewById(R.id.signin);
        signup=findViewById(R.id.signup);
        session=new Session(this);
        db=new MyDatabaseHelper(this);
        if(session.loggedin()==true){
            startActivity(new Intent(RegisterActivity.this,ActivitiesChoose.class));
            finish();
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                password=pass.getText().toString().trim();
                username=user.getText().toString().trim();
                password2=conpass.getText().toString().trim();
                if(TextUtils.isEmpty(password)||TextUtils.isEmpty(password2)||TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this, "please enter all the informations", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(password.equals(password2)){
                        Boolean checkuser=db.checkusername(username);
                        if(checkuser.equals(false)){
                            db.addUser(username,password);
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "User already Registered", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "passwords not matching", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}