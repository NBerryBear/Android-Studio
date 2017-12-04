package com.example.niki.berrybear.Programming;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niki.berrybear.Adapters.CustomList;
import com.example.niki.berrybear.HttpRequests.GET;
import com.example.niki.berrybear.HttpRequests.URL;
import com.example.niki.berrybear.R;
import com.example.niki.berrybear.Tabs.ListProgramsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProgramActivity extends ActionBarActivity {

    ListView icons ;
    Adapter adapter ;
    String name = "";
    public static int[] imageId  = new int[]{
            R.mipmap.ic_up,
            R.mipmap.ic_down,
            R.mipmap.ic_left,
            R.mipmap.ic_up

    };

    String[] comands ={
            "Up",
            "Down",
            "Left",
            "Right"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        setTitle(name);
        setContentView(R.layout.activity_program);

        //TODO: Get information and change title by name

        String program = "";
        int[] commands = null;
        int id = get_id(ListProgramsActivity.jsonarray);
        String uri = idUri(id);
        Log.e("uri", uri);
        try {
            program = new GET().execute(uri).get();
            Log.e("JSON", program);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            commands = commands(program);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String[] str = new String[]{"5s", "1s", "10s"};
        ListView list = (ListView) findViewById(R.id.commandList);
        CustomList adapter = new CustomList(this, str, commands);
        list.setAdapter(adapter);

        /*ListView list = (ListView) findViewById(R.id.commandList);
        list.setAdapter(new ArrayAdapter<String>(
                this, R.layout.commands_list_design, textView, comands));*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    public void onRunClickListener(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Run", Toast.LENGTH_SHORT).show();
        //TODO: Send program name to database
        //TODO: Get commands from database
    }

    public void onEditClickListener(MenuItem item) {
        startActivity(new Intent(this, NewProgramActivity.class));
    }

    public void onDeleteClickListener(MenuItem item) {
        //TODO: send to database which to delete
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int[] commands(String json) throws JSONException {
        String commands = "";
        List<Integer> picturs = new ArrayList<Integer>();

        Log.e("Commands", json);
        JSONObject jsonobj = null;
        try {
            jsonobj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        commands = jsonobj.getString("commands");
        String[] arrayOfString = commands.split(" ");
        for(String c : arrayOfString ){
            switch (c) {
                case "left":
                    picturs.add(R.mipmap.ic_left);
                    break;
                case "right":
                    picturs.add(R.mipmap.ic_right);
                    break;
                case "up":
                    picturs.add(R.mipmap.ic_up);
                    break;
                case "down":
                    picturs.add(R.mipmap.ic_down);
                    break;
            }
        }


        return toIntArray(picturs);
    }

    String idUri(int id){
        return URL.getProgramURl() + String.valueOf(id);
    }

    int[] toIntArray(List<Integer> picturs){
        int[] ret = new int[picturs.size()];
        for(int i = 0;i < ret.length;i++)
            ret[i] = picturs.get(i);
        return ret;
    }

    int get_id(JSONArray json){
        int id = 0;
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonobject = null;
            try {
                jsonobject = json.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (name.equals(jsonobject.getString("name"))) {
                    id = jsonobject.getInt("id");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

            return id;
    }
}
