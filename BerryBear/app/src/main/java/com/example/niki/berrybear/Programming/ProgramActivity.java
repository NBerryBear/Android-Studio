package com.example.niki.berrybear.Programming;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niki.berrybear.HttpRequests.GET;
import com.example.niki.berrybear.HttpRequests.POST;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ProgramActivity extends ActionBarActivity {

    String name = "";
    public static int[] commands;
    LinearLayout list;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        setTitle(name);
        setContentView(R.layout.activity_program);

        //TODO: Get information and change title by name

        id = getIntent().getIntExtra("id", 0);

        list = (LinearLayout) findViewById(R.id.commandList);
        show_commads();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    public void onRunClickListener(MenuItem item) {
        Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
        new POST().execute(idUri(id), "");

    }

    public void onEditClickListener(MenuItem item) {
        Intent intent = new Intent(getBaseContext(), NewProgramActivity.class);
        intent.putExtra("name", id);
        startActivity(intent);
    }

    public void onDeleteClickListener(MenuItem item) {
        //TODO: send to database which to delete
    }


    /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        arrayOfString = commands.split(" ");
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
    }*/

    String idUri(int id){
        return URLS.getProgramURl() + String.valueOf(id) + "/";
    }

    void show_commads() {
        String program = "";
        int[] commands = null;
        String uri = idUri(id);
        Log.e("uri", uri);
        try {

            program = new GET().execute(uri).get();
            Log.e("JSON", program);
            program = program.replace("\"","'");
            JSONArray json = new JSONArray((new JSONObject(program)).get("commands").toString());
            add_commands(json);

        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    void add_commands(JSONArray json) throws JSONException {
        Log.e("Here", "Here");
        for (int i = 0; i < json.length(); i++)
        {
            JSONObject jsonObj = json.getJSONObject(i);
            if(jsonObj.getString("name").equals("direction")){
                ImageView img = imageButton(jsonObj.getString("settings"));
                img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                img.setPadding(30, 5, 30, 5);
                list.addView(img);
            }else if(jsonObj.getString("name").equals("light")) {
                Log.e("light", jsonObj.getString("settings") );
                Integer picture = null;
                if(jsonObj.getString("settings").equals("true")){
                    picture = R.mipmap.ic_light_on;
                } else if(jsonObj.getString("settings").equals("false")){
                    picture = R.mipmap.ic_light_off;
                }
                if(picture != null){
                    ImageView image = new ImageView(this);
                    Drawable myDrawable = getResources().getDrawable(picture);
                    image.setImageDrawable(myDrawable);
                    image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    image.setPadding(30, 5, 30, 5);
                    list.addView(image);
                }
            }else if(jsonObj.getString("name").equals("moving")){
                LinearLayout layout = new LinearLayout(this);
                Button button1 = new Button(this);
                button1.setText("Forward");
                button1.setBackgroundResource(R.drawable.purple_round_button);
                button1.setTextColor(Color.rgb(225, 225, 225));
                layout.addView(button1);

                ImageView img = imageButton(jsonObj.getString("settings"));
                layout.addView(img);

                list.addView(layout);
            }else if(jsonObj.getString("name").equals("stop")){
                Button button1 = new Button(this);
                button1.setText("Stop");
                button1.setBackgroundResource(R.drawable.red_round_button);
                button1.setTextColor(Color.rgb(225, 225, 225));
                button1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                list.addView(button1);
            }else if(jsonObj.getString("name").equals("wait")){
                LinearLayout layout = new LinearLayout(this);
                Button button1 = new Button(this);
                button1.setText("Wait");
                button1.setBackgroundResource(R.drawable.yellow_round_button);
                button1.setTextColor(Color.rgb(225, 225, 225));
                layout.addView(button1);

                TextView seconds = new TextView(this);
                seconds.setText(jsonObj.getString("settings"));
                //seconds.setText(jsonObj.getString(" " + "settings" + "s"));
                layout.addView(seconds);


                list.addView(layout);
            }
        }

    }


    ImageView imageButton( String direction){
        Log.e("JSON", "direction");
        Integer picture = null;
        if(direction.equals("up")){
            picture = R.mipmap.ic_up;
        } else if(direction.equals("down")){
            picture = R.mipmap.ic_down;
        } else if(direction.equals("left")){
            picture = R.mipmap.ic_left;
        }else if(direction.equals("right")){
            picture = R.mipmap.ic_right;
        }
        if(picture != null){
            ImageView image = new ImageView(this);
            Drawable myDrawable = getResources().getDrawable(picture);
            image.setImageDrawable(myDrawable);
            return image;
        }
        return null;
    }

    /*int[] toIntArray(List<Integer> picturs){
        int[] ret = new int[picturs.size()];
        for(int i = 0;i < ret.length;i++)
            ret[i] = picturs.get(i);
        return ret;
    }

    int get_id(JSONArray json){
        int id = 0;
        for (int i = 0; i < json.length(); i++) {

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
    }*/


}
