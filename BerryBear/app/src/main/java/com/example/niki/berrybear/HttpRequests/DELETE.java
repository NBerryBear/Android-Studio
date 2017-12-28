package com.example.niki.berrybear.HttpRequests;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DELETE extends AsyncTask<String, Integer, String> {
    private String content = "";

    protected String doInBackground(String... params) {
        URL url = null;
        HttpURLConnection connection = new Connection().doInBackground("DELETE", params[0]);
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}