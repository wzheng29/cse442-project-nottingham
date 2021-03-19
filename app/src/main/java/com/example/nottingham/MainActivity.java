package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {
    private Button quick_access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Instantiate Ticker text boxes
        TextView ticker_Dow = findViewById(R.id.ticker_Dow);
        ticker_Dow.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_Dow.setSelected(true);

        TextView ticker_SnP500 = findViewById(R.id.ticker_SnP);
        ticker_SnP500.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_Dow.setSelected(true);

        TextView ticker_Nasdaq = findViewById(R.id.ticker_Nasdaq);
        ticker_Nasdaq.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_Nasdaq.setSelected(true);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("stockGetter");

        PyObject DowChange = pyobj.callAttr("getChange", "^DJI");
        ticker_Dow.setText("DJI " + DowChange.toString() + "%");

        PyObject SnPChange = pyobj.callAttr("getChange", "^GSPC");
        ticker_SnP500.setText("GSPC " + SnPChange.toString() + "%");

        PyObject NasdaqChange = pyobj.callAttr("getChange", "^IXIC");
        ticker_Nasdaq.setText("IXIC " + NasdaqChange.toString() + "%");

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