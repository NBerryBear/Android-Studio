package com.example.niki.berrybear.Tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niki.berrybear.HttpRequests.GET;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.Programming.NewProgramActivity;
import com.example.niki.berrybear.Programming.ProgramActivity;
import com.example.niki.berrybear.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.niki.berrybear.R.id.items;

public class ListProgramsActivity extends Activity {
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private View convertView;
    public String typeMap;
    public static JSONArray jsonarray = null;

    @Override
    public void onBackPressed() {
        super.onDestroy();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_programs);



        //List<String> names = getNames();
        String[] names = {"Program 1", "Program 2", "Program 3", "Program 4",
                "Program 5", "Program 6", "Program 7", "Program 8",
                "Program 9", "Program 10", "Program 11", "Program 12",
                "Program 13", "Program 14"};
        ListView list = (ListView) findViewById(R.id.list);
            list.setAdapter(new ArrayAdapter<String>(
                this, R.layout.list_design,
                items, names));

        Button add= (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NewProgramActivity.class);
                intent.putExtra("name", "");
                startActivity(intent);
            }
        });

    }


    public void onItemClickListener(View v) {
        typeMap=((TextView) v).getText().toString();
        Intent intent = new Intent(getBaseContext(), ProgramActivity.class);
        intent.putExtra("name", typeMap);
        startActivity(intent);
    }

    public void onRunButtonClickListener(View v) {
        Toast.makeText(getApplicationContext(), "Run", Toast.LENGTH_SHORT).show();
        //TODO: Send program name to database
        //TODO: Get commands from database
    }


    List<String> getNames(){
        String programs = "";
        GET request = new GET();
        try {
            programs = request.execute(URLS.getProgramURl()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<String> names = new ArrayList<String>();
        try {
            jsonarray = new JSONArray(programs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = null;
            try {
                jsonobject = jsonarray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String name = jsonobject.getString("name");
                names.add(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return names;
    }
};