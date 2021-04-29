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