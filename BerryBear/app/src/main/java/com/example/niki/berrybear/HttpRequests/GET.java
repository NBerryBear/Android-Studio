package com.example.niki.berrybear.HttpRequests;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GET extends AsyncTask<String, Integer, String> {
    private String content = "";

    protected String doInBackground(String... params) {
        URL url = null;
        try {
            HttpURLConnection connection = new Connection().doInBackground("GET", params[0]);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}