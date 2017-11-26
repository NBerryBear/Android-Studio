package com.example.niki.berrybear.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.niki.berrybear.Programming.ProgramActivity;

public class ImageListAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public int[] mThumbIds = ProgramActivity.imageId;

    // Constructor
    public ImageListAdapter(Context c, int commands_list_design, int command, int[] imageId){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ListView.LayoutParams(70, 70));
        return imageView;
    }

}