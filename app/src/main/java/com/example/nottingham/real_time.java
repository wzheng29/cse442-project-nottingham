package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class real_time extends AppCompatActivity {
    private Button backHome;
    TextView realTimePrice;
    ImageView currentPriceTrend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);
        // Back Button implementation
        backHome = (Button)findViewById(R.id.backHome);
        backHome.setBackgroundColor(Color.WHITE);
        backHome.setOnClickListener(v -> openHome());

        // Display Stock price
        realTimePrice = (TextView) findViewById(R.id.realTimePrice);

        // Display Stock Trend
        currentPriceTrend = (ImageView) findViewById(R.id.currentPriceTrend);
    }

    public void openHome(){
            Intent intent  = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

    // Get the price from API and show it from the TextView "realTimePrice"
    public void showPrice(){
        String price = "$0.0";
        realTimePrice.setText(price);

    }

    //Get the price list from API and return it as a image to the real page
    public void insertTrend() {

    }


}