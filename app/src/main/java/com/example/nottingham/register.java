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

public class register extends AppCompatActivity {

    private EditText firstName, lastName, username, password, repassword;
    DBHelper dbHelper;
    private HashMap<String,String> dataTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DBHelper(this);

        Button view_data, signup;
        view_data = (Button)findViewById(R.id.view_data);
        view_data.setBackgroundColor(Color.WHITE);

        signup = (Button)findViewById(R.id.signup_register_button);
        signup.setBackgroundColor(Color.WHITE);

        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        username = (EditText)findViewById(R.id.userName_signup);
        password = (EditText)findViewById(R.id.password_signup);
        repassword = (EditText)findViewById(R.id.repassword_signup);

        signup.setOnClickListener(v -> add());
        view_data.setOnClickListener(v -> showToast(dbHelper.showData()));

    }

    //add user
    private void add(){
        String uname = username.getText().toString();
        String pw1 = password.getText().toString();
        String pw2 = repassword.getText().toString();

        //check if user exists and if passwords match
        if(validUser(uname) && validPassword(pw1,pw2)){
            long id = dbHelper.addUser(uname,pw1);
            if(id < 0){
                showToast("INSERTION FAILED");
            }else{
                showToast("INSERTION DONE");
            }
        }
    }

    //------------------------------------HELPER FUNCTIONS------------------------------
    private void showToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    //Check if the retyped password match the original
    private boolean validPassword(String pw1, String pw2){
        if(pw1.equals(pw2)){
            showToast("PASSWORDS MATCH");
            return true;
        }
        showToast("PASSWORDS DID NOT MATCH");
        return false;
    }

    //Check if the username is already exist
    private boolean validUser(String uname){
        dataTable = dbHelper.getData();
        for(String key : dataTable.keySet()){
            if(key.equals(uname)){
                showToast("USERNAME TAKEN");
                return false;
            }
        }
        showToast("VALID USERNAME");
        return true;
    }

}