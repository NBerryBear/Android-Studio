package com.example.niki.berrybear.HttpRequests;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;


public class POST extends AsyncTask<String, Void, JSONObject> {

    public JSONObject doInBackground(String... params) {
        URL url = null;
        HttpURLConnection connection = new Connection().doInBackground("POST", params[0]);
        Connection.send(connection, params[1]);

        return null;
    }


}