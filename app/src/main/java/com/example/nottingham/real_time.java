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
    private  Button openFuture;
    private Button openPrevious;
    TextView realTimePrice;
    TextView stockName;
    ImageView currentPriceTrend;
    private ImageButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);


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

        // Go to the future trend page implementation
        openFuture = (Button)findViewById(R.id.openFuture);
        openFuture.setBackgroundColor(Color.WHITE);
        openFuture.setOnClickListener(v -> openFuture(name, symbol));

        // Go to the  previous trend page implementation
        openPrevious = (Button)findViewById(R.id.openPrevious);
        openPrevious.setBackgroundColor(Color.WHITE);
        openPrevious.setOnClickListener(v -> openPrevious(name, symbol));

        // Save Button Implementation
        saveButton = (ImageButton)findViewById(R.id.heartSave);
        saveButton.setOnClickListener(v -> saveStock(name, symbol));
        //pre set state of save button
        if(QuickAccessData.contains(name)) saveButton.setBackgroundResource(R.drawable.save_btn_selector);

        // Display Stock price
        realTimePrice = (TextView) findViewById(R.id.realTimePrice);
            //Using chaquopy and script
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("RealTimePython");
        PyObject helloWorldString = pythonFile.callAttr("getPrice",symbol);
        realTimePrice.setText("$"+helloWorldString.toString());



        // Display Stock Trend
        currentPriceTrend = (ImageView) findViewById(R.id.currentPriceTrend);
       PyObject frame = pythonFile.callAttr("Plotter",symbol,"2021-02-20");
        byte[] frameData = python.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 1500, 1500, true);
        currentPriceTrend.setImageBitmap(bMapScaled);

    }
    //Acts when Heart is clicked, adds or removes to quick access data
    public void saveStock(String stockName, String stockSymbol){
        if(QuickAccessData.contains(stockName)){
            QuickAccessData.remove(stockName);
            saveButton.setBackgroundResource(R.drawable.unsaved_btn_selector);
        }
        else{
            QuickAccessData.insert(stockName, stockSymbol);
            saveButton.setBackgroundResource(R.drawable.save_btn_selector);
        }
    }

    public void openHome(){
            Intent intent  = new Intent(this, MainActivity.class);
            startActivity(intent);
    }
    public void openFuture(String stock_name, String stock_symbol){
        Intent intent  = new Intent(this, future_price.class);
        intent.putExtra("name",stock_name);
        intent.putExtra("symbol",stock_symbol);
        startActivity(intent);



    // Get the price from API and show it from the TextView "realTimePrice"


    }

    public void openPrevious(String stock_name, String stock_symbol){
        Intent intent  = new Intent(this, previous_trend.class);
        intent.putExtra("name",stock_name);
        intent.putExtra("symbol",stock_symbol);
        startActivity(intent);

    }


}
