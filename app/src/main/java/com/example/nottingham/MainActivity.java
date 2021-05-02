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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {
    private Button quick_access, logout;
    private ArrayAdapter<String> arrayAdapter;
    private ListView searchList;
    private static String[] stock_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set ListView to GONE when app launches
        searchList = (ListView)findViewById(R.id.searchList);
        searchList.setVisibility(View.GONE);
        searchList.setBackgroundColor(Color.WHITE);

        //Filter search
        String [] stock_names;
        stock_list = getResources().getStringArray(R.array.all_stocks);
        stock_names = getStockNames();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stock_names);
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
        quick_access.setOnClickListener(v -> openQuickAccess());

        searchList.setOnItemClickListener((parent, view, position, id) -> {
            String name = arrayAdapter.getItem(position);
            openRealTime(name,getSymbol(name));
        logout = (Button)findViewById(R.id.logout);
        logout.setBackgroundColor(Color.WHITE);
        logout.setOnClickListener(v -> openLogin());

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = arrayAdapter.getItem(position);
                openRealTime(name,getSymbol(name));
            }
        });
    }

    public void openRealTime(String stock_name, String stock_symbol){
        Intent intent  = new Intent(this, real_time.class);
        intent.putExtra("name",stock_name);
        intent.putExtra("symbol",stock_symbol);
        startActivity(intent);
    }

    public void openQuickAccess(){
        Intent intent  = new Intent(this, quick_access.class);
        startActivity(intent);
    }

    private void openLogin(){
        Intent intent = new Intent(this,login.class);
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
                quick_access.setVisibility(View.GONE);
                logout.setVisibility(View.GONE);
                searchList.bringToFront();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchList.setVisibility(View.GONE);
                quick_access.setVisibility(View.VISIBLE);
                logout.setVisibility(View.VISIBLE);
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

    //Get name of all stocks from list in values/strings.xml
    public String[] getStockNames(){
        String[] names = new String[stock_list.length];
        for(int i = 0; i < stock_list.length; i++){
            String[] separated = stock_list[i].split(",");
            names[i] = separated[0];
        }
        return names;
    }

    //Find symbol for the given stock name (Apple -> AAPL)
    public static String getSymbol (String stockName){
        for (String s : stock_list) {
            String[] separated = s.split(",");
            if (separated[0].equals(stockName)) {
                return separated[1];
            }
        }
        //Stock not found. GameStop as default
        return "GME";
    }

}