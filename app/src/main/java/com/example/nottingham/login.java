package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class login extends AppCompatActivity {

    private EditText username, password;
    DBHelper dbHelper;
    private HashMap<String,String> dataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username_input);
        password = (EditText)findViewById(R.id.password_input);
        dbHelper = new DBHelper(this);

        Button signup, signin;

        signup = (Button)findViewById(R.id.register_button);
        signup.setBackgroundColor(Color.WHITE);
        signup.setOnClickListener(v -> openSignUp());

        signin = (Button)findViewById(R.id.login_button);
        signin.setBackgroundColor(Color.WHITE);
        signin.setOnClickListener(v -> {
            String uname = username.getText().toString();
            String pw = password.getText().toString();
            dataTable = dbHelper.getData();
            String check = dataTable.get(uname);

            //Add admin if it doesn't already exist
            if(uname.equals("Admin") && dbHelper.validUser("Admin")){
                dbHelper.addUser("Admin","1234");
                openAdmin();
            }else if(check == null){
                showToast("USER DOES NOT EXISTS");
            }else if(check.equals(pw)){
                showToast("VALID USER");
                if(uname.equals("Admin")){ openAdmin(); }
                else{ openHome(); }
            }else{
                showToast("WRONG PASSWORD");
            }
        });

    }
    public void openHome(){
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSignUp(){
        Intent intent  = new Intent(this, register.class);
        startActivity(intent);
    }

    private void openAdmin(){
        Intent intent = new Intent(this,admin.class);
        startActivity(intent);
    }

    private void showToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}