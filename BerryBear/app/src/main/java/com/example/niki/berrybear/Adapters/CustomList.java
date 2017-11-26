package com.example.niki.berrybear.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.niki.berrybear.R;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final int[] imageId;
    public CustomList(Activity context, String[] web, int[] imageId) {
        super(context, R.layout.commands_list_design, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.commands_list_design, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.command);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}