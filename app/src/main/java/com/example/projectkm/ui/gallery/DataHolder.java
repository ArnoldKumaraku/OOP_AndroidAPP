package com.example.projectkm.ui.gallery;

import java.util.ArrayList;

public class DataHolder {
    public final ArrayList<Gallery> array = new ArrayList<>();
    private DataHolder(){}
    public static DataHolder getInstance(){
        if(instance==null){
            instance=new DataHolder();
        }
        return instance;
    }
    private static DataHolder instance;
}