package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class real_time extends AppCompatActivity {
    private Button backHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);

        backHome = (Button)findViewById(R.id.button);
        backHome.setBackgroundColor(Color.WHITE);
        backHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openHome();
            }
        });
    }

    public void openHome(){
            Intent intent  = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
}