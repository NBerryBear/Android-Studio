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
import android.text.TextUtils;
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
import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProgramActivity extends ActionBarActivity {

    LinearLayout area1, area2;
    ScrollView scrow;
    //View view = null;
    boolean drag = false;
    Bundle tempBundle;
    public static boolean openTab2 = false;
    NewProgramActivity forNewViews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempBundle = savedInstanceState;
        setTitle("");
        setContentView(R.layout.activity_new_program);
        EditText name = (EditText) findViewById(R.id.title);
        name.setText(getIntent().getStringExtra("name"));

        area1 = (LinearLayout) findViewById(R.id.area1);
        area2 = (LinearLayout) findViewById(R.id.area2);
        scrow = (ScrollView) findViewById(R.id.scrollView1);

        forNewViews = this;
        TypedArray arrayResources = getResources().obtainTypedArray(
                R.array.resicon);

        addImg();

        arrayResources.recycle();

        area1.setOnDragListener(myOnDragListener);
        area2.setOnDragListener(myOnDragListener);
        scrow.setOnDragListener(myOnDragListener);

    }

    OnLongClickListener myOnLongClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            //v.setVisibility(View.INVISIBLE);
            return true;
        }

    };

    OnDragListener myOnDragListener = new OnDragListener() {

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
                        //LinearLayout newParent = (LinearLayout) v;

                        if(view instanceof ImageView){
                            view.setPadding(30, 5, 30, 5);
                        }
                        if((view instanceof Button) && ((Button) view).getText() != "Stop"){
                            LinearLayout layout = new LinearLayout(forNewViews);
                            if(((Button) view).getText() == "Forward") {
                                Spinner spinner = new Spinner(forNewViews);
                                SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(forNewViews,
                                        new Integer[]{R.mipmap.ic_up, R.mipmap.ic_down, R.mipmap.ic_left, R.mipmap.ic_right});
                                spinner.setAdapter(adapter);
                                spinner.setLayoutParams(new Spinner.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT));
                                layout.addView(view);
                                layout.addView(spinner);

                            }else if (((Button) view).getText() == "Wait"){
                                EditText text = new EditText(forNewViews);
                                text.setHint("1s");
                                layout.addView(view);
                                layout.addView(text);
                            }
                            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            area2.addView(layout);
                        }else area2.addView(view);

                        addImg();
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
    void addImg(){
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

    void sendJSON(String ProgramName, String commands){
        JSONObject json = new JSONObject();
        if (ProgramName.length() != 0 && commands.length() != 0) {
            try {
                json.put("name", ProgramName);
                json.put("commands", commands);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("JSOН", json.toString());

            //new POST().execute(json.toString());

            Intent intent = null;
            String name = getIntent().getStringExtra("name");
            if(name.length() > 0){
                intent = new Intent(getBaseContext(), ProgramActivity.class);
            }else{
                openTab2 = true;
                intent = new Intent(getBaseContext(),MainActivity.class);
            }

            startActivity(intent);

        }else  Toast.makeText(getApplicationContext(), "Fill all " , Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return true;
    }

    public void onSaveClickListener(MenuItem item) {
        List<String> newCommands = new ArrayList<String>();
        int itemsCount = area2.getChildCount();
        Log.e("Items", String.valueOf(itemsCount));
        for (int i = 0; i < itemsCount; i++) {
            View view = area2.getChildAt(i);
            if(view instanceof ImageView) {
                if (getResources().getDrawable(R.mipmap.ic_up).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                    newCommands.add("up");
                }else if (getResources().getDrawable(R.mipmap.ic_down).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                    newCommands.add("down");
                }else if (getResources().getDrawable(R.mipmap.ic_left).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                    newCommands.add("left");
                }else if (getResources().getDrawable(R.mipmap.ic_right).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                    newCommands.add("right");
                }else if (getResources().getDrawable(R.mipmap.ic_light_on).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                    newCommands.add("light_on");
                }else if (getResources().getDrawable(R.mipmap.ic_light_off).getConstantState() == ((ImageView) view).getDrawable().getConstantState()) {
                    newCommands.add("light_off");
                }
            }else if(view instanceof Button){

            }else Log.e("View", "Not Image");

        }
        String commands = TextUtils.join(" ", newCommands).toLowerCase();
        Log.e("Command", commands);
        String name = (((EditText) findViewById(R.id.title)).getText().toString());
        sendJSON(name, commands);

    }
}