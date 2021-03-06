package com.example.niki.berrybear.Programming;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.niki.berrybear.HttpRequests.POST;
import com.example.niki.berrybear.HttpRequests.PUT;
import com.example.niki.berrybear.HttpRequests.URLS;
import com.example.niki.berrybear.MainActivity;
import com.example.niki.berrybear.R;
import com.example.niki.berrybear.Views.NewProgramViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.niki.berrybear.MainActivity.ifConnectionExist;
import static com.example.niki.berrybear.Views.NewProgramViews.getDirectionButton;
import static com.example.niki.berrybear.Views.NewProgramViews.getLightButton;
import static com.example.niki.berrybear.Views.NewProgramViews.getMovingView;
import static com.example.niki.berrybear.Views.NewProgramViews.getView;
import static com.example.niki.berrybear.Views.NewProgramViews.getWaitView;

public class NewProgramActivity extends ActionBarActivity {

    LinearLayout area1, area2;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_new_program);
        EditText name = (EditText) findViewById(R.id.title);
        name.setText(getIntent().getStringExtra("name"));
        new NewProgramViews(this);


        area1 = (LinearLayout) findViewById(R.id.area1);
        area2 = (LinearLayout) findViewById(R.id.area2);
        scroll  = (ScrollView) findViewById(R.id.scrollView1);

        TypedArray arrayResources = getResources().obtainTypedArray(
                R.array.resicon);

        addElements();

        if(name.length() > 0){
            LinearLayout list = ProgramActivity.list;
            for(int i =0; i < list.getChildCount(); i = 0){
                View view = list.getChildAt(i);
                list.removeView(view);
                area2.addView(editView(view));
            }
        }
        arrayResources.recycle();

        area1.setOnDragListener(myOnDragListener);
        area2.setOnDragListener(myOnDragListener);
        scroll.setOnDragListener(myOnDragListener);

    }

    View editView(View view){
        View viewToAdd = null;
        if(view instanceof LinearLayout){
            View command =((LinearLayout) view).getChildAt(0);
            if(command instanceof ImageView){
                View textView = ((LinearLayout) view).getChildAt(1);
                String text = ((TextView) textView).getText().toString();
                ((LinearLayout) view).removeAllViews();
                viewToAdd = getDirectionButton(command, text);
            }else if(command instanceof Button){
                if(((Button) command).getText() == "Moving") {;
                    View spinner = ((LinearLayout) view).getChildAt(1);
                    int position = -1;
                    if((Integer)((ImageView) spinner).getTag() == R.mipmap.ic_up){
                        position = 0;
                    } else if((Integer)((ImageView) spinner).getTag() == R.mipmap.ic_down){
                        position = 1;
                    }else if((Integer)((ImageView) spinner).getTag() == R.mipmap.ic_left){
                        position = 2;
                    }else if((Integer)((ImageView) spinner).getTag() == R.mipmap.ic_right){
                        position = 3;
                    }
                    ((LinearLayout) view).removeAllViews();
                    viewToAdd = getMovingView(command, position);

                }else if(((Button) command).getText() == "Wait") {
                    View textView = ((LinearLayout) view).getChildAt(1);
                    String text = ((TextView) textView).getText().toString();
                    ((LinearLayout) view).removeAllViews();
                    viewToAdd = getWaitView(command, text);
                }
            }
        }else viewToAdd = getView(view);


        viewToAdd.setOnLongClickListener(myOnLongClickListener);
        return viewToAdd;
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
                        try {
                            View view = (View) event.getLocalState();
                            LinearLayout oldParent = (LinearLayout) view.getParent();

                            if(oldParent != area1) throw new NullPointerException();
                            oldParent.removeAllViews();
                            View viewToAdd = null;
                            view.setLongClickable(false);
                            view.setClickable(false);

                            if (view instanceof ImageView) {
                                if ((Integer) ((ImageView) view).getTag() == R.mipmap.ic_light_on ||
                                        (Integer) ((ImageView) view).getTag() == R.mipmap.ic_light_off) {
                                    viewToAdd = getLightButton(view);
                                } else viewToAdd = getDirectionButton(view, "");
                            } else if ((view instanceof Button)) {
                                if (((Button) view).getText() == "Moving") {
                                    viewToAdd = getMovingView(view, -1);
                                } else if (((Button) view).getText() == "Wait") {
                                    viewToAdd = getWaitView(view, "");

                                } else if (((Button) view).getText() == "Stop") {
                                    viewToAdd = getView(view);
                                }
                            }

                            viewToAdd.setOnLongClickListener(myOnLongClickListener);
                            area2.addView(viewToAdd);

                            scroll.post(new Runnable() {

                                @Override
                                public void run() {
                                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });

                            addElements();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
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
            image.setImageResource(i);
            image.setTag(i);
            image.setOnLongClickListener(myOnLongClickListener);
            area1.addView(image);
        }


        Button button1 = new Button(this);
        button1.setText("Moving");
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
        String name = (((EditText) findViewById(R.id.title)).getText().toString());
        if(itemsCount > 0 && name.length() > 0) {
            List<JSONObject> commands = new ArrayList<>();
            try {
                for (int i = 0; i < itemsCount; i++) {
                    JSONObject json = new JSONObject();
                    View view = area2.getChildAt(i);
                    if (view instanceof ImageView) {
                        if ((Integer) ((ImageView) view).getTag() == R.mipmap.ic_light_on) {
                            json.put("name", "light");
                            json.put("settings", true);
                        } else if ((Integer) ((ImageView) view).getTag() == R.mipmap.ic_light_off) {
                            json.put("name", "light");
                            json.put("settings", false);
                        }
                    } else if (view instanceof LinearLayout) {
                        View command = ((LinearLayout) view).getChildAt(0);
                        if (command instanceof ImageView) {
                            if ((Integer) ((ImageView) command).getTag() == R.mipmap.ic_up) {
                                json.put("name", "up");
                            } else if ((Integer) ((ImageView) command).getTag() == R.mipmap.ic_down) {
                                json.put("name", "down");
                            } else if ((Integer) ((ImageView) command).getTag() == R.mipmap.ic_right) {
                                json.put("name", "right");
                            } else if ((Integer) ((ImageView) command).getTag() == R.mipmap.ic_left) {
                                json.put("name", "left");
                            }
                            json.put("settings", getSeconds(view));

                        } else if (command instanceof Button) {
                            if (((Button) command).getText() == "Moving") {
                                json.put("name", "moving");
                                View spinner = ((LinearLayout) view).getChildAt(1);
                                Integer number = ((Spinner) spinner).getSelectedItemPosition();
                                String direction = null;
                                if (number == 0) {
                                    direction = "up";
                                } else if (number == 1) {
                                    direction = "down";
                                } else if (number == 2) {
                                    direction = "left";
                                } else if (number == 3) {
                                    direction = "right";
                                }
                                json.put("settings", direction);

                            } else if (((Button) command).getText() == "Wait") {
                                json.put("name", "wait");
                                json.put("settings", getSeconds(view));
                            }
                        }
                    } else if (view instanceof Button) {
                        if (((Button) view).getText() == "Stop") {
                            json.put("name", "stop");
                            json.put("settings", true);
                        }
                    }
                    commands.add(json);


                }
                sendJSON(name, commands);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else Toast.makeText(getApplicationContext(), "You should fill all the fields." , Toast.LENGTH_SHORT).show();
    }

    private Double getSeconds(View view) {
        View editText = ((LinearLayout) view).getChildAt(1);
        String text = ((EditText) editText).getText().toString();
        text = text.replace(",", ".");
        text = text.replaceAll("[^\\d.]", "");
        Double seconds = null;
        if(text.length() == 0) {
            seconds = 1.0;
        } else seconds = Double.valueOf(text);

        return seconds;
    }

    void sendJSON(String ProgramName, List<JSONObject> commands){
        JSONObject json = new JSONObject();

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
                ifConnectionExist(getBaseContext());
                new PUT().execute(URLS.getIdURl(id), json.toString());
                intent = new Intent(getBaseContext(), ProgramActivity.class);
                intent.putExtra("name", ProgramName);
                intent.putExtra("id", id);
            }else{
                MainActivity.openTab2 = true;
                ifConnectionExist(getBaseContext());
                new POST().execute(URLS.getProgramURl(), json.toString());
                intent = new Intent(getBaseContext(),MainActivity.class);
            }

            startActivity(intent);
    }

    public void onBackPressed(){
        Intent intent;
        if(getIntent().getStringExtra("name").length() > 0) {
            intent = new Intent(getBaseContext(), ProgramActivity.class);
            intent.putExtra("name", getIntent().getStringExtra("name"));
            intent.putExtra("id", ProgramActivity.id);
        }else{
            MainActivity.openTab2 = true;
            intent = new Intent(getBaseContext(),MainActivity.class);
        }

        startActivity(intent);
    }


}