package com.example.niki.berrybear.Tabs;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.niki.berrybear.R;


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
                //TODO: Post request {direction:up}
            }

        });

        Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT).show();
                //TODO: Post request {direction:down}
            }
        });

        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();
                //TODO: Post request {direction:right}
            }
        });

        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();
                //TODO: Post request {direction:left}
            }
        });

        Light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable;
                if(light_on){
                    myDrawable = getResources().getDrawable(R.mipmap.ic_light_off);
                    light_on = false;
                }
                else{
                    myDrawable = getResources().getDrawable(R.mipmap.ic_light_on);
                    light_on = true;
                }

                Light.setImageDrawable(myDrawable);
                //TODO: Post request {direction:up}
            }

        });

        /*final EditText display1=(EditText) findViewById(R.id.ip);


        Button one= (Button) findViewById(R.id.button);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), display1.getText().toString() , Toast.LENGTH_SHORT).show();
                URLS.url = "http://" + display1.getText().toString() + "/programs/";
            }
        });*/



    }
}
