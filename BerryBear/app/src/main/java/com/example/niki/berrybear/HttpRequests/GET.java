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
            url = new URL(params[0]);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader rd = null;
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

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