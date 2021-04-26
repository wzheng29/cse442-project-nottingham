package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button home;
        home = (Button)findViewById(R.id.signup_home_button);
        home.setBackgroundColor(Color.WHITE);
        home.setOnClickListener(v -> openHome());

    }
    public void openHome(){
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}