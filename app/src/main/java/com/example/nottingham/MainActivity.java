package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button quick_access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quick_access = (Button)findViewById(R.id.button);
        quick_access.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openQuickAccess();
            }
        });
    }

    public void openQuickAccess(){
        Intent intent  = new Intent(this, temp.class);
        startActivity(intent);
    }
}