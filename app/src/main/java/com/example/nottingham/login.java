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

        Button view_data, signup, signin;

        view_data = (Button)findViewById(R.id.view_data_button);
        view_data.setBackgroundColor(Color.WHITE);
        view_data.setOnClickListener(v -> showToast(dbHelper.showData()));

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
            if(check == null){
                showToast("USER DOES NOT EXISTS");
            }else if(check.equals(pw)){
                showToast("VALID USER");
                openHome();
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
    private void showToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}