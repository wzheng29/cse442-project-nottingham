package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class quick_access extends AppCompatActivity {
    QuickAccessData data = new QuickAccessData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_access);
    }
}