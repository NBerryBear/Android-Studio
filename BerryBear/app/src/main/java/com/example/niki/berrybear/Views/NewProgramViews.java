package com.example.niki.berrybear.Views;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.niki.berrybear.Adapters.SimpleImageArrayAdapter;
import com.example.niki.berrybear.Programming.NewProgramActivity;
import com.example.niki.berrybear.R;

public class NewProgramViews {

    private static NewProgramActivity thiss;

    public NewProgramViews(NewProgramActivity thiss){
        this.thiss = thiss;
    }


    public static View getImageButton(View view) {
        view.setPadding(30, 5, 30, 5);
        return view;
    }

    public static View getMovingView(View view) {
        LinearLayout layout = new LinearLayout(thiss);
        Spinner spinner = new Spinner(thiss);
        SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(thiss,
                new Integer[]{R.mipmap.ic_up, R.mipmap.ic_down, R.mipmap.ic_left, R.mipmap.ic_right});
        spinner.setAdapter(adapter);
        spinner.setLayoutParams(new Spinner.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT));
        view.setLongClickable(false);
        view.setClickable(false);
        layout.addView(view);
        layout.addView(spinner);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return layout;
    }


    public static View getWaitView(View view){
        LinearLayout layout = new LinearLayout(thiss);
        EditText text = new EditText(thiss);
        text.setHint("1s");
        view.setLongClickable(false);
        view.setClickable(false);
        layout.addView(view);
        layout.addView(text);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return layout;
    }
}
