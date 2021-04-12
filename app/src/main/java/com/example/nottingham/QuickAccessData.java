package com.example.nottingham;
import java.util.HashMap;
import java.util.ArrayList;

public class QuickAccessData {

    static HashMap<String, String> storageList = new HashMap<String, String>();

    public static HashMap<String, String> getStorageList(){return storageList;}

    public static void insert(String name, String tag){
        storageList.put(name, tag);
    }
    public static void remove(String name){
        storageList.remove(name);
    }
    public static Boolean contains(String name){
        if(storageList.get(name) != null) return true;
        else return false;
    }
}

