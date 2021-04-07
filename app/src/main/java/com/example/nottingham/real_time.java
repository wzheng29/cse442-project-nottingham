package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class real_time extends AppCompatActivity {
    private Button backHome;
    TextView realTimePrice;
    TextView stockName;
    ImageView currentPriceTrend;
    private ImageButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);
        if(QuickAccessData.contains("Apple")) saveButton.setBackgroundResource(R.drawable.save_btn_selector);

        //Get stock name and symbol
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String symbol = bundle.getString("symbol");

        //Set page name
        stockName = (TextView)findViewById(R.id.stockName);
        stockName.setText(name);

        // Back Button implementation
        backHome = (Button)findViewById(R.id.backHome);
        backHome.setBackgroundColor(Color.WHITE);
        backHome.setOnClickListener(v -> openHome());

        // Save Button Implementation
        saveButton = (ImageButton)findViewById(R.id.heartSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QuickAccessData.contains("Apple")){
                    QuickAccessData.remove("Apple");
                    saveButton.setBackgroundResource(R.drawable.unsaved_btn_selector);
                }
                else{
                    QuickAccessData.insert("Apple", "AAPL");
                    saveButton.setBackgroundResource(R.drawable.save_btn_selector);
                }
            }
        });

        // Display Stock price
        realTimePrice = (TextView) findViewById(R.id.realTimePrice);
            //Using chaquopy and script
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("RealTimePython");
        PyObject helloWorldString = pythonFile.callAttr("getPrice",symbol);
        realTimePrice.setText(helloWorldString.toString());



        // Display Stock Trend
        currentPriceTrend = (ImageView) findViewById(R.id.currentPriceTrend);
       PyObject frame = pythonFile.callAttr("Plotter",symbol,"2021-02-20");
        byte[] frameData = python.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 1500, 1500, true);
        currentPriceTrend.setImageBitmap(bMapScaled);

    }

    public void saveStock(){
        if(QuickAccessData.contains("Apple")){
            QuickAccessData.remove("Apple");
            Toast.makeText(getApplicationContext(), "Apple removed from QuickAccess", Toast.LENGTH_LONG).show();
        }
        else{
            QuickAccessData.insert("Apple", "AAPL");
            Toast.makeText(getApplicationContext(), "Apple added to QuickAccess", Toast.LENGTH_SHORT).show();
        }
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