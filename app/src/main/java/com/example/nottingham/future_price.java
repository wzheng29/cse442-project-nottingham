package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class future_price extends AppCompatActivity {

    private Button backHome2;
    private Button backReal;
    TextView futureTimePrice;
    ImageView futurePriceTrend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_price);


        // Back Button implementation
        backHome2 = (Button)findViewById(R.id.backHome2);
        backHome2.setBackgroundColor(Color.WHITE);
        backHome2.setOnClickListener(v -> openHome2());

        // Back to Real time page implementation
        backReal = (Button)findViewById(R.id.backReal);
        backReal.setBackgroundColor(Color.WHITE);
        backReal.setOnClickListener(v -> openReal());

        // Display Stock price
        futureTimePrice = (TextView) findViewById(R.id.futureTimePrice);


        // Display Stock Trend
        futurePriceTrend = (ImageView) findViewById(R.id.futurePriceTrend);
        
    }
    public void openHome2(){
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openReal(){
        Intent intent  = new Intent(this, real_time.class);
        startActivity(intent);
    }
}