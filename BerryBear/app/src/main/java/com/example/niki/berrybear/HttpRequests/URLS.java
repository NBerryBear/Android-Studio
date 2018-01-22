package com.example.niki.berrybear.HttpRequests;


public class URLS {
    public static String ip;

    public static String getProgramURl(){
        return "http://" + ip + "/programs/";
    }

    public static String getURL(){
        return "http://" + ip;
    }


    public static String getRobotURl(){
        return "http://" + ip + "/robots/1";
    }

    public static String getIdURl(int id){
        return URLS.getProgramURl() + String.valueOf(id) + "/";
    }

    public static void setIP(String ip){
        URLS.ip = ip;
    }

    public static String getIP(){
        return URLS.ip;
    }


}
