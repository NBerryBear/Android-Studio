package com.example.niki.berrybear.HttpRequests;


public class URLS {
    public static String ip;

    public static String getURL(){
        return "http://" + ip + ":8000";
    }


    public static String getRobotURl(){
        return "http://" + ip + ":8000" + "/robots/1/";
    }

    public static String getProgramURl(){
        return "http://" + ip + ":8000" + "/programs/";
    }

    public static String getIdURl(int id){ return URLS.getProgramURl() + String.valueOf(id) + "/"; }

    public static void setIP(String ip){
        URLS.ip = ip;
    }

    public static String getIP(){
        return URLS.ip;
    }


}
