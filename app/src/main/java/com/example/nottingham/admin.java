package com.example.nottingham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class admin extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dbHelper = new DBHelper(this);

        Button delete_button, view_data, login_button;
        delete_button = (Button)findViewById(R.id.delete_button);
        view_data = (Button)findViewById(R.id.view_data_button);
        login_button = (Button)findViewById(R.id.login_button);
        delete_button.setBackgroundColor(Color.WHITE);
        view_data.setBackgroundColor(Color.WHITE);
        login_button.setBackgroundColor(Color.WHITE);

        EditText delete_uname;
        delete_uname = (EditText)findViewById(R.id.delete_user);

        TextView dbDisplay;
        dbDisplay = (TextView)findViewById(R.id.dbDisplay);

        view_data.setOnClickListener(v -> dbDisplay.setText(dbHelper.showData()));
        delete_button.setOnClickListener(v -> {
            String uname = delete_uname.getText().toString();
            dbHelper.deleteUser(uname);
            dbDisplay.setText(dbHelper.showData());
        });

        login_button.setOnClickListener(v -> openLogin());
    }

    private void openLogin(){
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }
}