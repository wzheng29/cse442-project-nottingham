package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button home;
        home = (Button)findViewById(R.id.home_button);
        home.setBackgroundColor(Color.WHITE);
        home.setOnClickListener(v -> openHome());

        Button signup;
        signup = (Button)findViewById(R.id.register_button);
        signup.setBackgroundColor(Color.WHITE);
        signup.setOnClickListener(v -> openSignUp());

    }
    public void openHome(){
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSignUp(){
        Intent intent  = new Intent(this, register.class);
        startActivity(intent);
    }
}