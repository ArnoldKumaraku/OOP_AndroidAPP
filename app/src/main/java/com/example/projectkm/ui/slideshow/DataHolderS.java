package com.example.projectkm.ui.slideshow;

import java.util.ArrayList;

public class DataHolderS {
    public final ArrayList<Slideshow> array = new ArrayList<>();
    private DataHolderS(){}
    public static DataHolderS getInstance(){
        if(instance==null){
            instance= new DataHolderS();
        }
        return instance;
    }
    private static DataHolderS instance;
}