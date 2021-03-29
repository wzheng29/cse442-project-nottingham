package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {
    private Button quick_access;
    private Button real_time;
    private ArrayAdapter<String> arrayAdapter;
    private ListView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set ListView to GONE when app launches
        searchList = (ListView)findViewById(R.id.searchList);
        searchList.setVisibility(View.GONE);
        searchList.setBackgroundColor(Color.WHITE);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.stocks));
        searchList.setAdapter(arrayAdapter);

        //Instantiate Ticker text boxes
        TextView ticker_Dow = findViewById(R.id.ticker_Dow);
        ticker_Dow.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_Dow.setSelected(true);

        TextView ticker_SnP500 = findViewById(R.id.ticker_SnP);
        ticker_SnP500.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_SnP500.setSelected(true);

        TextView ticker_Nasdaq = findViewById(R.id.ticker_Nasdaq);
        ticker_Nasdaq.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        ticker_Nasdaq.setSelected(true);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("stockGetter");
        String padding = "                               ";

        PyObject DowChange = pyobj.callAttr("getChange", "^DJI");
        ticker_Dow.setText(padding+"DJI " + DowChange.toString() + "%"+padding);
        setColor(ticker_Dow, DowChange.toString());

        PyObject SnPChange = pyobj.callAttr("getChange", "^GSPC");
        ticker_SnP500.setText(padding+"GSPC " + SnPChange.toString() + "%"+padding);
        setColor(ticker_SnP500, SnPChange.toString());

        PyObject NasdaqChange = pyobj.callAttr("getChange", "^IXIC");
        ticker_Nasdaq.setText(padding+"IXIC " + NasdaqChange.toString() + "%"+padding);
        setColor(ticker_Nasdaq, NasdaqChange.toString());

        quick_access = (Button)findViewById(R.id.button);
        quick_access.setBackgroundColor(Color.WHITE);
        quick_access.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openQuickAccess();
            }
        });

        real_time = (Button)findViewById(R.id.apple);
        real_time.setVisibility(View.GONE);
        real_time.setBackgroundColor(Color.WHITE);
        real_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openRealTime();
            }
        });
    }

    public void openRealTime(){
        Intent intent  = new Intent(this, real_time.class);
        startActivity(intent);
    }

    public void openQuickAccess(){
        Intent intent  = new Intent(this, quick_access.class);
        startActivity(intent);
    }

    //Search bar setup
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);

        MenuItem menuItem = menu.findItem(R.id.search_home);
        //Associate searchable config with SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search...");

        //Display ListView only when the search bar is expanded
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                searchList.setVisibility(View.VISIBLE);
                searchList.bringToFront();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchList.setVisibility(View.GONE);
                return true;
            }
        });

        //Filtering search bar text
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
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

}