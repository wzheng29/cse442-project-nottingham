package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.HashMap;

public class quick_access extends AppCompatActivity {


    TextView tvName;
    TextView tvChange;
    Button buttName;
    ImageButton buttHeart;
    LinearLayout vl;
    Button backHome;
    LinearLayout divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_access);

        vl = findViewById(R.id.saved_holder);
        HashMap<String, String> savedStocks = QuickAccessData.getStorageList();

        // Back Button implementation
        backHome = (Button)findViewById(R.id.backHome_QuickAccess);
        backHome.setBackgroundColor(Color.WHITE);
        backHome.setOnClickListener(v -> returnHome());

        //Setup Python Object
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        PyObject pythonFile = python.getModule("stockGetter");

        //itterate through HashMap and add name & percent change to vertical layout via horizontal layout
        for(String name : savedStocks.keySet()){
            LinearLayout hl = new LinearLayout(this);
            hl.setOrientation(LinearLayout.HORIZONTAL);


            LinearLayout vl_mini = new LinearLayout((this));
            vl_mini.setOrientation(LinearLayout.VERTICAL);

            //PyObject stockPrice = pythonFile.callAttr("getPrice", savedStocks.get(name));
            PyObject stockChange = pythonFile.callAttr("getChange",savedStocks.get(name));
            //Initialize text View holding % change
            tvChange = new TextView(this);
            tvChange.setText(stockChange.toString() +"%");
            setColor(tvChange, stockChange.toString());
            tvChange.setTextSize(25);

            //Initialize button holding button to real time
            buttName = new Button(this);
            buttName.setOnClickListener(v -> openRealTime(name, savedStocks.get(name)));
            buttName.setTextSize(25);
            buttName.setText(name);
            buttName.setTextColor(Color.WHITE);
            buttName.setGravity(Gravity.LEFT);
            buttName.setBackgroundColor(Color.parseColor("#333333"));
            buttName.setPadding(0,0,50, 0);
            LinearLayout.LayoutParams buttParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            buttParams.weight = 1;
            //buttParams.gravity = Gravity.LEFT;
            buttName.setLayoutParams(buttParams);

            //Initialize button to unsave
            buttHeart = new ImageButton(this);
            buttHeart.setBackgroundResource(R.drawable.save_btn_selector);
            buttHeart.setOnClickListener(v -> removeQuickAccess(vl_mini, name));
            buttHeart.setLayoutParams(new LinearLayout.LayoutParams(115,115));

            divider = new LinearLayout(this);
            divider.setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(750, 8);
            dividerParams.gravity = Gravity.CENTER;
            divider.setLayoutParams(dividerParams);


            hl.addView(buttHeart);
            hl.addView(buttName);
            hl.addView(tvChange);
            vl_mini.addView(hl);
            vl_mini.addView(divider);
            vl.addView(vl_mini);
        }

    }
    public void setColor(TextView tv, String s){
        if(s.contains("+")){
            tv.setTextColor(Color.parseColor("#008000"));
        }
        else if(s.contains("-")){
            tv.setTextColor(Color.parseColor("#ff0000"));
        }
        else {
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
    }
    public void openRealTime(String stock_name, String stock_symbol){
        Intent intent  = new Intent(this, real_time.class);
        intent.putExtra("name",stock_name);
        intent.putExtra("symbol",stock_symbol);
        startActivity(intent);
    }

    public void removeQuickAccess(View v, String name){
        vl.removeViewInLayout(v);
        vl.setGravity(Gravity.TOP);
        QuickAccessData.remove(name);
    }
    public void returnHome(){
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}