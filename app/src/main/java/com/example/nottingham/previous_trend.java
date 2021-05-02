package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class previous_trend extends AppCompatActivity {

    private Button backHome3;
    private Button backReal2;
    TextView oldprice;
    ImageView previousTrend;
    TextView stockName3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_trend);

        //Get stock name and symbol
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String symbol = bundle.getString("symbol");

        //set stock name
        stockName3 = (TextView)findViewById(R.id.stockName3);
        stockName3.setText(name);


        // Back Button implementation
        backHome3 = (Button)findViewById(R.id.backHome3);
        backHome3.setBackgroundColor(Color.WHITE);
        backHome3.setOnClickListener(v -> openHome3());

        // Back to Real time page implementation
        backReal2 = (Button)findViewById(R.id.backReal2);
        backReal2.setBackgroundColor(Color.WHITE);
        backReal2.setOnClickListener(v -> openReal2(name, symbol));

        oldprice = (TextView) findViewById(R.id.oldPrice2);
        //Using chaquopy and script
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("oldTrend");
        PyObject helloWorldString = pythonFile.callAttr("getPrice",symbol);
        oldprice.setText("Price 5 Years ago is $"+helloWorldString.toString());

        // Display Stock Trend
        previousTrend = (ImageView) findViewById(R.id.previousTrend);
        PyObject frame = pythonFile.callAttr("Plotter",symbol,"2021-02-20");
        byte[] frameData = python.getBuiltins().callAttr("bytes", frame).toJava(byte[].class);
        Bitmap bitmap = BitmapFactory.decodeByteArray(frameData, 0, frameData.length);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 1500, 1500, true);
        previousTrend.setImageBitmap(bMapScaled);
    }
    public void openHome3(){
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openReal2(String stock_name, String stock_symbol){
        Intent intent  = new Intent(this, real_time.class);
        intent.putExtra("name",stock_name);
        intent.putExtra("symbol",stock_symbol);
        startActivity(intent);
    }


}