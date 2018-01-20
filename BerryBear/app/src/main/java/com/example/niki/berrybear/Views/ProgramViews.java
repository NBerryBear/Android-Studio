package com.example.niki.berrybear.Views;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.niki.berrybear.Programming.ProgramActivity;
import com.example.niki.berrybear.R;

public class ProgramViews {
    private static ProgramActivity thiss;

    public ProgramViews(ProgramActivity thiss){
        this.thiss = thiss;
    }


    public static View getImageButton(String direction) {
        Integer picture = null;
        if (direction.equals("up")) {
            picture = R.mipmap.ic_up;
        } else if (direction.equals("down")) {
            picture = R.mipmap.ic_down;
        } else if (direction.equals("left")) {
            picture = R.mipmap.ic_left;
        } else if (direction.equals("right")) {
            picture = R.mipmap.ic_right;
        }
        if (picture != null) {
            ImageView image = new ImageView(thiss);
            image.setImageResource(picture);
            image.setTag(picture);
            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            image.setPadding(30, 5, 30, 5);
            return  image;
        }

        return null;

    }


    public static View getLightButton(String on) {
        Integer picture = null;
        if (on.equals("true")) {
            picture = R.mipmap.ic_light_on;
        } else if (on.equals("false")) {
            picture = R.mipmap.ic_light_off;
        }

        if (picture != null) {
            ImageView image = new ImageView(thiss);
            image.setImageResource(picture);
            image.setTag(picture);
            image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            image.setPadding(30, 5, 30, 5);
            return image;
        }

        return null;
    }


    public static View getMovingView(String direction) {
        LinearLayout layout = new LinearLayout(thiss);
        Button button1 = new Button(thiss);
        button1.setText("Moving");
        button1.setBackgroundResource(R.drawable.purple_round_button);
        button1.setTextColor(Color.rgb(225, 225, 225));
        layout.addView(button1);

        ImageView img = (ImageView) getImageButton(direction);
        layout.addView(img);

        return  layout;
    }

    public static View getStopView(){
        Button button = new Button(thiss);
        button.setText("Stop");
        button.setBackgroundResource(R.drawable.red_round_button);
        button.setTextColor(Color.rgb(225, 225, 225));
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return button;
    }


    public static View getWaitView(String time){
        LinearLayout layout = new LinearLayout(thiss);
        Button button1 = new Button(thiss);
        button1.setText("Wait");
        button1.setBackgroundResource(R.drawable.yellow_round_button);
        button1.setTextColor(Color.rgb(225, 225, 225));
        layout.addView(button1);
        TextView seconds = new TextView(thiss);
        seconds.setText(time);
        layout.addView(seconds);

        return layout;
    }
}
