package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button quick_access;
    private Button real_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quick_access = (Button)findViewById(R.id.button);
        quick_access.setBackgroundColor(Color.WHITE);
        quick_access.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openQuickAccess();
            }
        });
        real_time = (Button)findViewById(R.id.apple);
        real_time.setBackgroundColor(Color.WHITE);
        real_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openRealTime();
            }
        });
    }

    public void openRealTime(){
        Intent intent  = new Intent(this, real_time.class);
        startActivity(intent);
    }

    public void openQuickAccess(){
        Intent intent  = new Intent(this, quick_access.class);
        startActivity(intent);
    }
}