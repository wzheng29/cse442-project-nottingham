package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class quick_access extends AppCompatActivity {


    TextView tvName;
    TextView tvChange;
    Button buttName;
    ImageButton buttHeart;
    LinearLayout vl;
    Button backHome;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> stock_names;
    private ListView searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_access);

        stock_names = new ArrayList<String>();
        //Set search bar ListView to GONE when app launches
        searchList = (ListView)findViewById(R.id.searchList);
        searchList.setVisibility(View.GONE);
        searchList.setBackgroundColor(Color.WHITE);

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

        //iterate through HashMap and add name & percent change to vertical layout via horizontal layout
        for(String name : savedStocks.keySet()){
            LinearLayout hl = new LinearLayout(this);
            hl.setOrientation(LinearLayout.HORIZONTAL);

            //PyObject stockPrice = pythonFile.callAttr("getPrice", savedStocks.get(name));
            PyObject stockChange = pythonFile.callAttr("getChange",savedStocks.get(name));
            //Initialize text View holding % change
            tvChange = new TextView(this);
            tvChange.setText(stockChange.toString());
            setColor(tvChange, stockChange.toString());
            tvChange.setTextSize(25);

            //Initialize button holding button to real time
            buttName = new Button(this);
            buttName.setOnClickListener(v -> openRealTime(name, savedStocks.get(name)));
            buttName.setTextSize(25);
            buttName.setText(name);
            buttName.setTextColor(Color.WHITE);
            buttName.setBackgroundColor(Color.parseColor("#333333"));
            buttName.setPadding(0,0,50, 0);

            stock_names.add(name);

            //Initialize button to unsave
            buttHeart = new ImageButton(this);
            buttHeart.setBackgroundResource(R.drawable.save_btn_selector);
            buttHeart.setOnClickListener(v -> removeQuickAccess(hl, name));
            buttHeart.setLayoutParams(new LinearLayout.LayoutParams(118,118));

            hl.addView(buttHeart);
            hl.addView(buttName);
            hl.addView(tvChange);
            vl.addView(hl);

        }

        //Filter search list
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stock_names);
        searchList.setAdapter(arrayAdapter);
        searchList.setOnItemClickListener((parent, view, position, id) -> {
            String name = arrayAdapter.getItem(position);
            openRealTime(name,MainActivity.getSymbol(name));
        });
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
        arrayAdapter.remove(name);
    }
    public void returnHome(){
        Intent intent  = new Intent(this, MainActivity.class);
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

        //Display search ListView only when the search bar is expanded
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                searchList.setVisibility(View.VISIBLE);
                backHome.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchList.setVisibility(View.GONE);
                backHome.setVisibility(View.VISIBLE);
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
}