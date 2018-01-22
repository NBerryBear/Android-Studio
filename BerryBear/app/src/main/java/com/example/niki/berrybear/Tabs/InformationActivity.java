package com.example.niki.berrybear.Tabs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.R;

import java.util.Objects;

public class InformationActivity extends Activity {

    public void onBackPressed(){
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        final LinearLayout ipLayout = (LinearLayout) findViewById(R.id.ipLayout);
        final TextView IP = new TextView(this);
        IP.setTextSize(20);
        final EditText editIP = new EditText(this);
        editIP.setHint("IP...");
        editIP.setTextSize(20);


        IP.setText(URLS.getIP());
        ipLayout.addView(IP);

        final Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.e("Button", String.valueOf(button.getText()));
                if(Objects.equals(String.valueOf(button.getText()), "Edit")) {
                    editIP.setText(IP.getText());
                    ipLayout.removeView(IP);
                    ipLayout.addView(editIP);
                    button.setText("Save");
                    button.setTextColor(Color.parseColor("#4FBF40"));
                }else {

                    IP.setText(editIP.getText());
                    ipLayout.removeView(editIP);
                    ipLayout.addView(IP);
                    button.setText("Edit");
                    button.setTextColor(Color.parseColor("#FC4040"));
                    URLS.setIP(String.valueOf(editIP.getText()));
                }
            }

        });
    }
}
