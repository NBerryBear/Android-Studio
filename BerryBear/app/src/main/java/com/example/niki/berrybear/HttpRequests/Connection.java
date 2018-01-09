package com.example.niki.berrybear.HttpRequests;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection extends AsyncTask<String, String, HttpURLConnection> {
    private String content = "";

    @Override
    protected HttpURLConnection doInBackground(String... params) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(params[1]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(params[0]);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }


    static void send(HttpURLConnection connection, String json){
        try {
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(json);
            out.flush();
            out.close();

            int res = connection.getResponseCode();

            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = br.readLine() ) != null) {
                System.out.println(line);
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}