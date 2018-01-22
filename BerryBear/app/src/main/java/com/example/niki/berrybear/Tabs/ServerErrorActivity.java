package com.example.niki.berrybear.Tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.R;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;
import static com.example.niki.berrybear.MainActivity.connectionAvailable;

public class ServerErrorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_error);

        final TextView reload = (TextView) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reload.setText("Connecting...");

                reload.postDelayed(new Runnable() {
                    public void run() {
                        if(connectionAvailable()){
                            Intent intent = new Intent(ServerErrorActivity.this, MainActivity.class);
                            intent.addFlags(FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        }
                    }
                }, 1000);

                reload.postDelayed(new Runnable() {
                    public void run() {
                        reload.setText(getString(R.string.serverError));
                    }
                },1500);
            }

        });
    }
}
