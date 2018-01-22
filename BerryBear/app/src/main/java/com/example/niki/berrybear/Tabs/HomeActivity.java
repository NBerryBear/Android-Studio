package com.example.niki.berrybear.Tabs;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.niki.berrybear.HttpRequests.POST;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.niki.berrybear.MainActivity.ifConnectionExist;


public class HomeActivity extends Activity {

    boolean light_on = false;

    @Override
    public void onBackPressed() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ImageButton Up = (ImageButton) findViewById(R.id.Up);
        final ImageButton Down = (ImageButton) findViewById(R.id.Down);
        final ImageButton Right = (ImageButton) findViewById(R.id.Right);
        final ImageButton Left = (ImageButton) findViewById(R.id.Left);
        final ImageButton Light = (ImageButton) findViewById(R.id.light);

        Up.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Up.setImageResource(R.mipmap.ic_up_click);
                    start("up");
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Up.setImageResource(R.mipmap.ic_up);
                    stop();
                    return true;
                }
                return false;
            }
        });


        Down.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Down.setImageResource(R.mipmap.ic_down_click);
                    start("down");
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Down.setImageResource(R.mipmap.ic_down);
                    stop();
                    return true;
                }
                return false;
            }
        });

        Left.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Left.setImageResource(R.mipmap.ic_left_click);
                    start("left");
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Left.setImageResource(R.mipmap.ic_left);
                    stop();
                    return true;
                }
                return false;
            }
        });

        Right.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Right.setImageResource(R.mipmap.ic_right_click);
                    start("right");
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Right.setImageResource(R.mipmap.ic_right);
                    stop();
                    return true;
                }
                return false;
            }
        });

        Light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable;
                JSONObject json = new JSONObject();
                if(light_on){
                    myDrawable = getResources().getDrawable(R.mipmap.ic_light_off);
                    light_on = false;
                }
                else{
                    myDrawable = getResources().getDrawable(R.mipmap.ic_light_on);
                    light_on = true;
                }

                Light.setImageDrawable(myDrawable);

                try {
                    json.put("name" , "light");
                    json.put("settings" , light_on);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ifConnectionExist(getBaseContext());
                new POST().execute(URLS.getRobotURl(), json.toString());
            }

        });

    }

    void start(String direction){
        JSONObject json = new JSONObject();
        try {
            json.put("name" , "moving");
            json.put("settings" , direction);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ifConnectionExist(getBaseContext());
        new POST().execute(URLS.getRobotURl(), json.toString());
    }

    void stop(){
        JSONObject json = new JSONObject();
        try {
            json.put("name" , "stop");
            json.put("settings" , true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ifConnectionExist(getBaseContext());
        new POST().execute(URLS.getRobotURl(), json.toString());
    }



}
