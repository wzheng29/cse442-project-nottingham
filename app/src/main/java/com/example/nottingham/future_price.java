package com.example.nottingham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class future_price extends AppCompatActivity {

    private Button backHome2;
    private Button backReal;
    TextView futureTimePrice;
    ImageView futurePriceTrend;
    private NumberPicker picker1;
    private String[] pickerVals;
    TextView stockName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_price);

        //Get stock name and symbol
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String symbol = bundle.getString("symbol");

        //set stock name
        stockName2 = (TextView)findViewById(R.id.stockName2);
        stockName2.setText(name);

        pickerVals = new String[] {"1", "2", "3", "4", "5"};
        picker1 = findViewById(R.id.numberPicker_main);
        picker1.setMaxValue(4);
        picker1.setMinValue(0);
        picker1.setDisplayedValues(pickerVals);
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("futureTrend");
        //date picker log here
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", valuePicker1 + "");
                // Display Stock price
                futureTimePrice = (TextView) findViewById(R.id.futureTimePrice);
                //Using chaquopy and script
                //APPLE HAS BEEN HARDCODED HERE
                PyObject helloWorldString = pythonFile.callAttr("predict",symbol,"2010-02-20",valuePicker1);
                futureTimePrice.setText("$"+helloWorldString.toString());


                // Display Stock Trend

                futurePriceTrend = (ImageView) findViewById(R.id.futurePriceTrend);
                //APPLE HAS BEEN HARDCODED HERE
                PyObject frame = pythonFile.callAttr("futurePlot",symbol,"2010-02-20",valuePicker1);
                byte[] frameData = python.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
                Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
                Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 1500, 1500, true);
                futurePriceTrend.setImageBitmap(bMapScaled);
            }
        });

        // Back Button implementation
        backHome2 = (Button)findViewById(R.id.backHome2);
        backHome2.setBackgroundColor(Color.WHITE);
        backHome2.setOnClickListener(v -> openHome2());

        // Back to Real time page implementation
        backReal = (Button)findViewById(R.id.backReal);
        backReal.setBackgroundColor(Color.WHITE);
        backReal.setOnClickListener(v -> openReal());
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