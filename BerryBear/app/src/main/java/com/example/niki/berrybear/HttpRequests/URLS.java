package com.example.niki.berrybear.HttpRequests;


public class URLS {
    public static String url;

    public static String getProgramURl(){
        if (url != null) return url;
        return "http://192.168.1.2:8000/programs/";
    }
}
