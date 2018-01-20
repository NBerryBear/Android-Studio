package com.example.niki.berrybear.Programming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.niki.berrybear.HttpRequests.DELETE;
import com.example.niki.berrybear.HttpRequests.GET;
import com.example.niki.berrybear.HttpRequests.POST;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.R;
import com.example.niki.berrybear.Views.ProgramViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ProgramActivity extends ActionBarActivity {

    static String name;
    static LinearLayout list;
    static int id;

    public void onBackPressed(){
        MainActivity.openTab2 = true;
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    public void onRunClickListener(MenuItem item) {
        new POST().execute(URLS.getIdURl(id), "");

    }

    public void onEditClickListener(MenuItem item) {
        Intent intent = new Intent(getBaseContext(), NewProgramActivity.class);
        intent.putExtra("name", super.getTitle());
        startActivity(intent);
    }

    public void onDeleteClickListener(MenuItem item) {
        new DELETE().execute(URLS.getIdURl(id), "");
        MainActivity.openTab2 = true;
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        setTitle(name);
        setContentView(R.layout.activity_program);
        id = getIntent().getIntExtra("id", 0);
        list = (LinearLayout) findViewById(R.id.commandList);
        show_commands();

    }

    void show_commands() {
        String program = "";
        String uri = URLS.getIdURl(id);
        try {
            program = new GET().execute(uri).get();
            program = program.replace("\"", "'");
            JSONArray json = new JSONArray((new JSONObject(program)).get("commands").toString());
            add_commands(json);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }


    void add_commands(JSONArray json) throws JSONException {
        new ProgramViews(this);
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObj = json.getJSONObject(i);
            String commands = jsonObj.getString("name");
            String settings = jsonObj.getString("settings");
            View view = null;

            switch (commands) {
                case "direction":
                    view = ProgramViews.getImageButton(settings);
                    break;
                case "light":
                    view = ProgramViews.getLightButton(settings);
                    break;
                case "moving":
                    view = ProgramViews.getMovingView(settings);
                    break;
                case "stop":
                    view = ProgramViews.getStopView();
                    break;
                case "wait":
                    view = ProgramViews.getWaitView(settings);
            }

            if(view != null) {
                list.addView(view);
            }
        }

    }

}
