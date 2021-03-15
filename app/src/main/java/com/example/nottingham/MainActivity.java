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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView ticker_tape1 = findViewById(R.id.ticker_tape1);
        ticker_tape1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_tape1.setSelected(true);

        quick_access = (Button)findViewById(R.id.button);
        quick_access.setBackgroundColor(Color.WHITE);
        quick_access.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openQuickAccess();
            }
        });
    }

    public void openQuickAccess(){
        Intent intent  = new Intent(this, quick_access.class);
        startActivity(intent);
    }
}