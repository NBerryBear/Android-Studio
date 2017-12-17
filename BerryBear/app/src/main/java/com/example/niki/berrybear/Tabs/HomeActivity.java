package com.example.niki.berrybear.Tabs;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.niki.berrybear.HttpRequests.POST;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.R;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends Activity {

    boolean light_on = false;
    /*private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //android.os.Process.killProcess(android.os.Process.myPid());
        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click twice to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton Up = (ImageButton) findViewById(R.id.Up);
        ImageButton Down = (ImageButton) findViewById(R.id.Down);
        ImageButton Right = (ImageButton) findViewById(R.id.Right);
        ImageButton Left = (ImageButton) findViewById(R.id.Left);
        final ImageButton Light = (ImageButton) findViewById(R.id.light);

        Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Up", Toast.LENGTH_SHORT).show();
                send("up");

            }

        });

        Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT).show();
                send("down");
            }
        });

        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();
                send("right");
            }
        });

        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();
                send("left");
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
                    json.put("name" , "direction");
                    json.put("settings" , light_on);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    void send(String direction){
        JSONObject json = new JSONObject();
        try {
            json.put("name" , "direction");
            json.put("settings" , direction);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("JSon", json.toString());
        new POST().execute(URLS.getRobotURl(), json.toString());
    }
}
