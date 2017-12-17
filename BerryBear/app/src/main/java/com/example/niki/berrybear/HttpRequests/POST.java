package com.example.niki.berrybear.HttpRequests;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class POST extends AsyncTask<String, Void, JSONObject> {
    public static String content;

    public JSONObject doInBackground(String... string) {
        URL url = null;
        try {
            url = new URL(string[0]);;
            String json = string[1];
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            //connection.connect();

            //BufferedReader rd = null;
            //rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            connection.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            Log.e("Json", json);
            out.write(json);
            out.flush();
            out.close();

            int res = connection.getResponseCode();

            System.out.println(res);


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

        return null;
    }

}