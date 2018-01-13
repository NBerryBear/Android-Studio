package com.example.niki.berrybear.HttpRequests;


public class URLS {
    public static String url;

    public static String getProgramURl(){
    }

    public static String getRobotURl(){

    }

    public static String getIdURl(int id){
        return URLS.getProgramURl() + String.valueOf(id) + "/";
    }
}
