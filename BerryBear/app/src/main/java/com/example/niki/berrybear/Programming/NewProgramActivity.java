package com.example.niki.berrybear.Programming;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProgramActivity extends ActionBarActivity {

    String[] comands ={
            "Up",
            "Down",
            "Left",
            "Right"
    };

    public static List<Integer> imageId  = new ArrayList<Integer>();

    LinearLayout area1, area2;
    View view = null;
    boolean drag = false;
    Bundle tempBundle;
    public static boolean openTab2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tempBundle = savedInstanceState;
        setContentView(R.layout.activity_new_program);
        EditText name = (EditText) findViewById(R.id.title);
        name.setText(getIntent().getStringExtra("name"));

        imageId.add(R.mipmap.ic_up);
        imageId.add(R.mipmap.ic_down);
        imageId.add(R.mipmap.ic_left);
        imageId.add(R.mipmap.ic_up);

        area1 = (LinearLayout) findViewById(R.id.area1);
        area2 = (LinearLayout) findViewById(R.id.area2);


        TypedArray arrayResources = getResources().obtainTypedArray(
                R.array.resicon);

        addImg();

        arrayResources.recycle();

        area1.setOnDragListener(myOnDragListener);
        area2.setOnDragListener(myOnDragListener);

        if(drag){
            Log.e("Drag", "Drag'");
        }

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
                    if(v == area1) break;
                    View view = (View)event.getLocalState();
                    LinearLayout oldParent = (LinearLayout)view.getParent();
                    oldParent.removeAllViews();
                    LinearLayout newParent = (LinearLayout)v;
                    newParent.addView(view);
                    addImg();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }

            return true;

        }

    };


    void addImg(){
        for(int i : imageId) {
            ImageView imageView = new ImageView(this);
            Drawable myDrawable = getResources().getDrawable(i);
            imageView.setImageDrawable(myDrawable);
            imageView.setOnLongClickListener(myOnLongClickListener);
            area1.addView(imageView);
        }
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
                }
            }else Log.e("View", "Not Image");

        }
        String commands = TextUtils.join(" ", newCommands).toLowerCase();
        Log.e("Command", commands);
        String name = (((EditText) findViewById(R.id.title)).getText().toString());
        sendJSON(name, commands);

    }
}