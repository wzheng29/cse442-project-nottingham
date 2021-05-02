package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;

import android.widget.LinearLayout;
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

        


        //Setup Python Object
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("stockGetter");
        String padding = "                               ";

        TextView megaTicker1 = findViewById(R.id.ticker_mega1);
        megaTicker1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        PyObject NasdaqChange = pyobj.callAttr("getChange", "^IXIC");
        Spannable nasdaqWord = new SpannableString("IXIC " + NasdaqChange.toString() + "%  ");
        nasdaqWord.setSpan(new ForegroundColorSpan(getColor(NasdaqChange.toString())), 0, nasdaqWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker1.setText(nasdaqWord);
        PyObject AppleChange = pyobj.callAttr("getChange", "AAPL");
        Spannable appleWord = new SpannableString("AAPL " + AppleChange.toString() + "%  ");
        appleWord.setSpan(new ForegroundColorSpan(getColor(AppleChange.toString())), 0, appleWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker1.append(appleWord);
        PyObject MicrosoftChange = pyobj.callAttr("getChange", "MSFT");
        Spannable microsoftWord = new SpannableString("MSFT " + MicrosoftChange.toString() + "%        ");
        microsoftWord.setSpan(new ForegroundColorSpan(getColor(MicrosoftChange.toString())), 0, microsoftWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker1.append(microsoftWord);
        megaTicker1.setSelected(true);
        /*PyObject AmazonChange = pyobj.callAttr("getChange", "AMZN");
        Spannable amazonWord = new SpannableString("AMZN " + AmazonChange.toString() + "%  ");
        amazonWord.setSpan(new ForegroundColorSpan(getColor(AmazonChange.toString())), 0, amazonWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker1.append(amazonWord);
        PyObject walmartChange = pyobj.callAttr("getChange", "WMT");
        Spannable walmartWord = new SpannableString("WMT " + walmartChange.toString() + "%  ");
        walmartWord.setSpan(new ForegroundColorSpan(getColor(walmartChange.toString())), 0, walmartWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker1.append(walmartWord);
*/
        TextView megaTicker2 = findViewById(R.id.ticker_mega2);
        megaTicker2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        PyObject SnPChange = pyobj.callAttr("getChange", "^GSPC");
        Spannable snpWord = new SpannableString("GSPC " + SnPChange.toString() + "%  ");
        snpWord.setSpan(new ForegroundColorSpan(getColor(SnPChange.toString())), 0, snpWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker2.setText(snpWord);
        PyObject GOOGLChange = pyobj.callAttr("getChange", "GOOGL");
        Spannable googleWord = new SpannableString("GOGL " + GOOGLChange.toString() + "%  ");
        googleWord.setSpan(new ForegroundColorSpan(getColor(GOOGLChange.toString())), 0, googleWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker2.append(googleWord);
        PyObject BABAChange = pyobj.callAttr("getChange", "BABA");
        Spannable babaWord = new SpannableString("BABA " + BABAChange.toString() + "%     ");
        babaWord.setSpan(new ForegroundColorSpan(getColor(BABAChange.toString())), 0, babaWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker2.append(babaWord);
        megaTicker2.setSelected(true);
        /*PyObject FBChange = pyobj.callAttr("getChange", "FB");
        Spannable fbWord = new SpannableString("FB " + FBChange.toString() + "%  ");
        fbWord.setSpan(new ForegroundColorSpan(getColor(FBChange.toString())), 0, fbWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker2.append(fbWord);
        PyObject disneyChange = pyobj.callAttr("getChange", "DIS");
        Spannable disWord = new SpannableString("DIS " + disneyChange.toString() + "%  ");
        disWord.setSpan(new ForegroundColorSpan(getColor(disneyChange.toString())), 0, disWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker2.append(disWord);
*/
        TextView megaTicker3 = findViewById(R.id.ticker_mega3);
        megaTicker3.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        PyObject DowChange = pyobj.callAttr("getChange", "^DJI");
        Spannable dowWord = new SpannableString("DJI " + DowChange.toString() + "%  ");
        dowWord.setSpan(new ForegroundColorSpan(getColor(DowChange.toString())), 0, dowWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker3.setText(dowWord);
        PyObject TSLAChange = pyobj.callAttr("getChange", "TSLA");
        Spannable tslaWord = new SpannableString("TSLA " + TSLAChange.toString() + "%  ");
        tslaWord.setSpan(new ForegroundColorSpan(getColor(TSLAChange.toString())), 0, tslaWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker3.append(tslaWord);
        PyObject JPMChange = pyobj.callAttr("getChange", "JPM");
        Spannable jpmWord = new SpannableString("JPM " + JPMChange.toString() + "%          ");
        jpmWord.setSpan(new ForegroundColorSpan(getColor(JPMChange.toString())), 0, jpmWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker3.append(jpmWord);
        megaTicker3.setSelected(true);
        /*PyObject VISAChange = pyobj.callAttr("getChange", "V");
        Spannable visaWord = new SpannableString("V " + VISAChange.toString() + "%  ");
        visaWord.setSpan(new ForegroundColorSpan(getColor(VISAChange.toString())), 0, visaWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker3.append(visaWord);
        PyObject paypalChange = pyobj.callAttr("getChange", "PYPL");
        Spannable paypalWord = new SpannableString("PYPL " + paypalChange.toString() + "%  ");
        paypalWord.setSpan(new ForegroundColorSpan(getColor(paypalChange.toString())), 0, paypalWord.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        megaTicker3.append(paypalWord);
        */





        quick_access = (Button)findViewById(R.id.button);
        quick_access.setBackgroundColor(Color.WHITE);
        quick_access.setOnClickListener(v -> openQuickAccess());

        searchList.setOnItemClickListener((parent, view, position, id) -> {
                    String name = arrayAdapter.getItem(position);
                    openRealTime(name, getSymbol(name));
                });
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
    public int getColor(String s){
        if(s.contains("+")){
            return Color.parseColor("#008000");
        }
        else if(s.contains("-")){
            return Color.parseColor("#ff0000");
        }
        else {
            return Color.parseColor("#ffffff");
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