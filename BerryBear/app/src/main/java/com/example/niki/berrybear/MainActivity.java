package com.example.niki.berrybear;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.niki.berrybear.Tabs.HomeActivity;
import com.example.niki.berrybear.Tabs.ListProgramsActivity;

@SuppressWarnings("depreciation")
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();


        TabHost.TabSpec tab1 = tabHost.newTabSpec("One");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Two");

        tab1.setIndicator("Home");
        tab1.setContent(new Intent(this, HomeActivity.class));

        tab2.setIndicator("Programs");
        tab2.setContent(new Intent(this, ListProgramsActivity.class));


        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

    }

}
