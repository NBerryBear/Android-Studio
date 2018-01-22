package com.example.niki.berrybear;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.niki.berrybear.HttpRequests.GET;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.Tabs.HomeActivity;
import com.example.niki.berrybear.Tabs.InformationActivity;
import com.example.niki.berrybear.Tabs.ListProgramsActivity;
import com.example.niki.berrybear.Tabs.ServerErrorActivity;

import java.util.concurrent.ExecutionException;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;
import static com.example.niki.berrybear.HttpRequests.URLS.getURL;

@SuppressWarnings("depreciation")
public class MainActivity extends TabActivity {
    public static boolean openTab2;
    private SharedPreferences prefs;


    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editPrefs = prefs.edit();
        editPrefs.putString("ip", URLS.getIP());
        editPrefs.commit();

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        URLS.setIP(prefs.getString("ip", "192..."));

        TabHost tabHost = getTabHost();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("One");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Two");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Three");

        tab1.setIndicator("Home");;
        tab2.setIndicator("Programs");


        TextView error = (TextView) findViewById(R.id.serverError);

        if(connectionAvailable()){
            tab1.setContent(new Intent(this, HomeActivity.class));
            tab2.setContent(new Intent(this, ListProgramsActivity.class));
        } else{
            tab1.setContent(new Intent(this, ServerErrorActivity.class));
            tab2.setContent(new Intent(this, ServerErrorActivity.class));

        }


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




    public static boolean connectionAvailable() {
        try {
            String content = new GET().execute(getURL()).get();
            if(content.length() == 0) return false;
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void ifConnectionExist(Context context){
        if(!connectionAvailable()){
            Intent intent = new Intent(context , MainActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent);
        }

    }


}
