package com.example.niki.berrybear.Tabs;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.Programming.ProgramActivity;
import com.example.niki.berrybear.R;

import java.util.List;

import static com.example.niki.berrybear.R.id.items;

public class ListProgramsActivity extends ListActivity {
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private View convertView;
    public String typeMap;

    @Override
    public void onBackPressed() {
        super.onDestroy();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listview = getListView();

        String[] values = {"Program 1", "Program 2", "Program 3", "Program 4",
                "Program 5", "Program 6", "Program 7", "Program 8",
                "Program 9", "Program 10", "Program 11", "Program 12",
                "Program 13", "Program 14"};

        this.setListAdapter(new ArrayAdapter<String>(
                this, R.layout.list_design,
                items, values));

    }


    public void onItemClickListener(View v) {
        typeMap=((TextView) v).getText().toString();
        Toast.makeText(getApplicationContext(), typeMap, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), ProgramActivity.class);
        intent.putExtra("name", typeMap);
        startActivity(intent);
    }

    public void onRunButtonClickListener(View v) {
        Toast.makeText(getApplicationContext(), "Run", Toast.LENGTH_SHORT).show();
        //TODO: Send program name to database
        //TODO: Get commands from database
    }

};