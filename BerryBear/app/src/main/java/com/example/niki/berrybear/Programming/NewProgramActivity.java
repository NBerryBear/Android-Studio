package com.example.niki.berrybear.Programming;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niki.berrybear.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.niki.berrybear.R.id.textView;

public class NewProgramActivity extends Activity {

    LinearLayout targetLayout;
    ListView listSource, listTarget;

    MyDragEventListener myDragEventListener = new MyDragEventListener();

    String[] comands ={
            "Up",
            "Down",
            "Left",
            "Right"
    };

    public static int[] imageId  = new int[]{
            R.mipmap.ic_up,
            R.mipmap.ic_down,
            R.mipmap.ic_left,
            R.mipmap.ic_up

    };

    List<String> droppedList;
    ArrayAdapter<String> droppedAdapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_program);
        targetLayout = (LinearLayout)findViewById(R.id.targetlayout);
        listSource = (ListView)findViewById(R.id.sourcelist);
        listTarget = (ListView)findViewById(R.id.targetlist);
        EditText name = (EditText) findViewById(R.id.title);
        name.setText("Program");

        listSource.setAdapter(new ArrayAdapter<String>(
                this, R.layout.left_new_commands_list_design, textView, comands));
        /*String[] str = new String[]{"5s", "1s", "10s", "1s"};
        listSource.setAdapter(new CustomList(this, str, imageId));*/


        listSource.setOnItemLongClickListener(listSourceItemLongClickListener);
        listSource.setOnItemLongClickListener(listSourceItemLongClickListener);


        droppedList = new ArrayList<String>();

        //TODO: Send program name to database
        //TODO: Get commands from database

        droppedAdapter = new ArrayAdapter<String>(
                this, R.layout.right_new_commands_list_design, textView, droppedList);
        listTarget.setAdapter(droppedAdapter);

        listSource.setOnDragListener(myDragEventListener);
        targetLayout.setOnDragListener(myDragEventListener);

    }

    AdapterView.OnItemLongClickListener listSourceItemLongClickListener
            = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> l, View v,
                                       int position, long id) {

            //Selected item is passed as item in dragData
            ClipData.Item item = new ClipData.Item(comands[position]);

            String[] clipDescription = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData dragData = new ClipData((CharSequence)v.getTag(),
                    clipDescription,
                    item);
            View.DragShadowBuilder myShadow = new MyDragShadowBuilder(v);

            v.startDrag(dragData, //ClipData
                    myShadow,  //View.DragShadowBuilder
                    comands[position],  //Object myLocalState
                    0);    //flags


            return true;
        }};

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
        private static Drawable shadow;

        public MyDragShadowBuilder(View v) {
            super(v);
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics (Point size, Point touch){
            int width = getView().getWidth();
            int height = getView().getHeight();

            shadow.setBounds(0, 0, width, height);
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }

    }

    protected class MyDragEventListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();

            switch(action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //All involved view accept ACTION_DRAG_STARTED for MIMETYPE_TEXT_PLAIN
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                        return true; //Accept
                    else return false; //reject
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DROP:
                    // Gets the item containing the dragged data
                    ClipData.Item item = event.getClipData().getItemAt(0);

                    //If apply only if drop on buttonTarget
                    if(v == targetLayout){
                        String droppedItem = item.getText().toString();
                        droppedList.add(droppedItem);
                        droppedAdapter.notifyDataSetChanged();

                        return true;
                    }else{
                        return false;
                    }


                case DragEvent.ACTION_DRAG_ENDED:
                    return true;
                default: //unknown case
                    return false;

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return true;
    }

    public void onSaveClickListener(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ProgramActivity.class));
    }
}
