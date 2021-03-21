package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

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
            //Using chaquopy and script
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("RealTimePython");
        PyObject helloWorldString = pythonFile.callAttr("getPrice","AAPL");
        realTimePrice.setText(helloWorldString.toString());



        // Display Stock Trend
        currentPriceTrend = (ImageView) findViewById(R.id.currentPriceTrend);
       PyObject frame = pythonFile.callAttr("Plotter","AAPL","2021-02-20");
        byte[] frameData = python.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 1500, 1500, true);
        currentPriceTrend.setImageBitmap(bMapScaled);


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