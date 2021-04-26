package com.example.nottingham;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Nottingham.db";
    private static final String TABLE_NAME = "users";
    private static final String UID = "_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY, "+USERNAME+" TEXT, "+PASSWORD+" TEXT)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //READ FROM DB
    public HashMap<String,String> getData (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String,String> data = new HashMap<>();
        String [] columns = {UID,USERNAME,PASSWORD};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
            String pw = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD));
            data.put(name,pw);
        }
        cursor.close();
        return data;
    }

    //ADD USER - returns new row number; -1 if there is an error
    public long addData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME,username);
        contentValues.put(PASSWORD,password);
        long id = db.insert(TABLE_NAME, null, contentValues);
        return id;
    }

    //DELETE USER - return number of rows removed
    public int deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {username};
        int num = db.delete(TABLE_NAME,USERNAME+" LIKE ?",args);
        return num;
    }
}
