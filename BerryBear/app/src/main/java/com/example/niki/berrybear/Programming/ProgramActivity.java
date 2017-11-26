package com.example.niki.berrybear.Programming;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niki.berrybear.Adapters.CustomList;
import com.example.niki.berrybear.R;

import java.util.ArrayList;

public class ProgramActivity extends ActionBarActivity {

    ListView icons ;
    ArrayList <Drawable> items ;
    Adapter adapter ;
    public static int[] imageId  = new int[]{
            R.mipmap.ic_up,
            R.mipmap.ic_down,
            R.mipmap.ic_left,
            R.mipmap.ic_up

    };

    String[] comands ={
            "Up",
            "Down",
            "Left",
            "Right"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("name");
        setTitle(name);
        setContentView(R.layout.activity_program);

        //TODO: Get information and change title by name


        String[] str = new String[]{"5s", "1s", "10s", "1s"};
        ListView list = (ListView) findViewById(R.id.commandList);
        CustomList adapter = new CustomList(this, str, imageId);
        list.setAdapter(adapter);

        /*ListView list = (ListView) findViewById(R.id.commandList);
        list.setAdapter(new ArrayAdapter<String>(
                this, R.layout.commands_list_design, textView, comands));*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    public void onRunClickListener(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Run", Toast.LENGTH_SHORT).show();
        //TODO: Send program name to database
        //TODO: Get commands from database
    }

    public void onEditClickListener(MenuItem item) {
        startActivity(new Intent(this, NewProgramActivity.class));
    }

    public void onDeleteClickListener(MenuItem item) {
        //TODO: send to database which to delete
    }
}
