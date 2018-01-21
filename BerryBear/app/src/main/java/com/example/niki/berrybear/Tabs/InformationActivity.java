package com.example.niki.berrybear.Tabs;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.R;

import java.util.Objects;

public class InformationActivity extends Activity {
    private SharedPreferences prefs;

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editPrefs = prefs.edit();
        editPrefs.putString("ip", URLS.getIP());
        editPrefs.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        URLS.setIP(prefs.getString("ip", "192..."));

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
                    button.setBackgroundResource(R.drawable.green_round_button);
                }else {

                    IP.setText(editIP.getText());
                    ipLayout.removeView(editIP);
                    ipLayout.addView(IP);
                    button.setText("Edit");
                    button.setBackgroundResource(R.drawable.red_round_button);
                    URLS.setIP(String.valueOf(editIP.getText()));
                }
            }

        });
    }
}
