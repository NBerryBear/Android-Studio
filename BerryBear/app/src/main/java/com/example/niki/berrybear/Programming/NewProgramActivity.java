package com.example.niki.berrybear.Programming;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.niki.berrybear.Adapters.SimpleImageArrayAdapter;
import com.example.niki.berrybear.HttpRequests.POST;
import com.example.niki.berrybear.HttpRequests.PUT;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProgramActivity extends ActionBarActivity {

    LinearLayout area1, area2;
    public static boolean openTab2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_new_program);
        EditText name = (EditText) findViewById(R.id.title);
        name.setText(getIntent().getStringExtra("name"));

        area1 = (LinearLayout) findViewById(R.id.area1);
        area2 = (LinearLayout) findViewById(R.id.area2);
        ScrollView scroll  = (ScrollView) findViewById(R.id.scrollView1);

         if(name.length() > 3){
            area2.addView(ProgramActivity.list);
        }

        TypedArray arrayResources = getResources().obtainTypedArray(
                R.array.resicon);

        addElements();

        arrayResources.recycle();

        area1.setOnDragListener(myOnDragListener);
        area2.setOnDragListener(myOnDragListener);
        scroll.setOnDragListener(myOnDragListener);

    }

    OnLongClickListener myOnLongClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }

    };

    OnDragListener myOnDragListener = new OnDragListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED: break;
                case DragEvent.ACTION_DRAG_ENTERED: break;
                case DragEvent.ACTION_DRAG_EXITED: break;
                case DragEvent.ACTION_DROP:
                    if(v == area1){
                        View view = (View) event.getLocalState();
                        LinearLayout oldParent = (LinearLayout) view.getParent();
                        if(oldParent != area1) oldParent.removeView(view);
                    }else {
                        View view = (View) event.getLocalState();
                        LinearLayout oldParent = (LinearLayout) view.getParent();
                        oldParent.removeAllViews();

                        if(view instanceof ImageView){
                            view.setPadding(30, 5, 30, 5);
                        }
                        if((view instanceof Button) && ((Button) view).getText() != "Stop"){
                            LinearLayout layout = new LinearLayout(NewProgramActivity.this);
                            if(((Button) view).getText() == "Forward") {
                                Spinner spinner = new Spinner(NewProgramActivity.this);
                                SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(NewProgramActivity.this,
                                        new Integer[]{R.mipmap.ic_up, R.mipmap.ic_down, R.mipmap.ic_left, R.mipmap.ic_right});
                                spinner.setAdapter(adapter);
                                spinner.setLayoutParams(new Spinner.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT));
                                layout.addView(view);
                                layout.addView(spinner);

                            }else if (((Button) view).getText() == "Wait"){
                                EditText text = new EditText(NewProgramActivity.this);
                                text.setHint("1s");
                                layout.addView(view);
                                layout.addView(text);
                            }
                            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            area2.addView(layout);
                        }else area2.addView(view);


                        addElements();
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }

            return true;

        }

    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void addElements(){
        List<Integer> imageId  = new ArrayList<Integer>();
        imageId.add(R.mipmap.ic_up);
        imageId.add(R.mipmap.ic_down);
        imageId.add(R.mipmap.ic_left);
        imageId.add(R.mipmap.ic_right);
        imageId.add(R.mipmap.ic_light_on);
        imageId.add(R.mipmap.ic_light_off);
        for(int i : imageId) {
            ImageView image = new ImageView(this);
            Drawable myDrawable = getResources().getDrawable(i);
            image.setImageDrawable(myDrawable);
            image.setOnLongClickListener(myOnLongClickListener);
            area1.addView(image);
        }


        Button button1 = new Button(this);
        button1.setText("Forward");
        button1.setBackgroundResource(R.drawable.purple_round_button);
        button1.setTextColor(Color.rgb(225, 225, 225));
        button1.setOnLongClickListener(myOnLongClickListener);
        area1.addView(button1);

        Button button2 = new Button(this);
        button2.setText("Stop");
        button2.setTextColor(Color.rgb(225, 225, 225));
        button2.setBackgroundResource(R.drawable.red_round_button);
        button2.setOnLongClickListener(myOnLongClickListener);
        area1.addView(button2);

        Button button3 = new Button(this);
        button3.setText("Wait");
        button3.setTextColor(Color.rgb(225, 225, 225));
        button3.setBackgroundResource(R.drawable.yellow_round_button);
        button3.setOnLongClickListener(myOnLongClickListener);
        area1.addView(button3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return true;
    }

    public void onSaveClickListener(MenuItem item) {
        int itemsCount = area2.getChildCount();
        Log.e("Items", String.valueOf(itemsCount));
        List<JSONObject> commands = new ArrayList<>();
        try {
            for (int i = 0; i < itemsCount; i++) {
                JSONObject json = new JSONObject();
                View view = area2.getChildAt(i);
                if(view instanceof ImageView) {
                    if (getResources().getDrawable(R.mipmap.ic_up).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                        json.put("name" , "direction");
                        json.put("settings" , "up");
                    }else if (getResources().getDrawable(R.mipmap.ic_down).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                        json.put("name" , "direction");
                        json.put("settings" , "down");
                    }else if (getResources().getDrawable(R.mipmap.ic_left).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                        json.put("name" , "direction");
                        json.put("settings" , "left");
                    }else if (getResources().getDrawable(R.mipmap.ic_right).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                        json.put("name" , "direction");
                        json.put("settings" , "right");
                    }else if (getResources().getDrawable(R.mipmap.ic_light_on).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                        json.put("name" , "light");
                        json.put("settings" , true);
                    }else if (getResources().getDrawable(R.mipmap.ic_light_off).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                        json.put("name", "light");
                        json.put("settings", false);
                    }
                }else if(view instanceof LinearLayout){
                    View command =((LinearLayout) view).getChildAt(0);
                    if(((Button) command).getText() == "Forward") {
                        json.put("name", "moving");
                        View spinner = ((LinearLayout) view).getChildAt(1);
                        Integer number = ((Spinner)spinner).getSelectedItemPosition();
                        String direction = null;
                        if(number == 0) {
                            direction = "up";
                        }else if(number == 1) {
                            direction = "down";
                        }else if(number == 2) {
                            direction = "left";
                        }else if(number == 3) {
                            direction = "right";
                        }

                        json.put("settings" , direction);

                    }else if(((Button) command).getText() == "Wait") {
                        View editText = ((LinearLayout) view).getChildAt(1);
                        String text = ((EditText) editText).getText().toString();
                        Integer seconds = Integer.valueOf(text);
                        if(seconds < 1){
                            seconds = 1;
                        }
                        json.put("name", "wait");
                        json.put("settings", seconds);
                    }
                }else if(view instanceof Button){
                    json.put("name", "stop");
                    json.put("settings", true);
                }
                commands.add(json);

            }
            String name = (((EditText) findViewById(R.id.title)).getText().toString());
            sendJSON(name, commands);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void sendJSON(String ProgramName, List<JSONObject> commands){
        JSONObject json = new JSONObject();
        if (ProgramName.length() != 0 && commands.size() != 0) {
            try {
                json.put("name", ProgramName);
                json.put("commands", commands);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("JSOН", json.toString());

            Intent intent = null;
            String name = getIntent().getStringExtra("name");
            if(name.length() > 0){
                int id = ProgramActivity.id;
                new PUT().execute(URLS.getIdURl(id), json.toString());
                intent = new Intent(getBaseContext(), ProgramActivity.class);
                intent.putExtra("name", ProgramName);
                intent.putExtra("id", id);
            }else{
                openTab2 = true;
                new POST().execute(URLS.getProgramURl(), json.toString());
                intent = new Intent(getBaseContext(),MainActivity.class);
            }

            startActivity(intent);

        }else  Toast.makeText(getApplicationContext(), "Fill all " , Toast.LENGTH_SHORT).show();
    }

}