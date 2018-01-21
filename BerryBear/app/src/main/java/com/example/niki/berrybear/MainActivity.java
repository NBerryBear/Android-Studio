package com.example.niki.berrybear;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.example.niki.berrybear.Tabs.HomeActivity;
import com.example.niki.berrybear.Tabs.InformationActivity;
import com.example.niki.berrybear.Tabs.ListProgramsActivity;

@SuppressWarnings("depreciation")
public class MainActivity extends TabActivity {
    public static boolean openTab2;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("One");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Two");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Three");

        tab1.setIndicator("Home");
        tab1.setContent(new Intent(this, HomeActivity.class));

        tab2.setIndicator("Programs");
        tab2.setContent(new Intent(this, ListProgramsActivity.class));


        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.ic_info);
        image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tab3.setIndicator(image);
        tab3.setContent(new Intent(this, InformationActivity.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        if(openTab2){
            openTab2 = false;
            tabHost.setCurrentTabByTag("Two");
        }

    }

}
