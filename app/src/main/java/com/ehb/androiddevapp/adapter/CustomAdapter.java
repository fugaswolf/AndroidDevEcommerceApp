package com.ehb.androiddevapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehb.androiddevapp.R;

//customer adapter that populate the language spinner data
public class CustomAdapter extends BaseAdapter {
    //variables for spinner adapter
    Activity context;
    int flags[];
    String[] countryNames;
    LayoutInflater inflater;

    //constructor called when this will get the life
    public CustomAdapter (Activity applicationContext, int[] flags, String[] countryNames) {
        this.context = applicationContext;
        this.flags = flags;
        this.countryNames = countryNames;
        inflater = (LayoutInflater.from(applicationContext));
    }

    //total item in spinner count
    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //attaching the custom view of spinner which have flag image and name
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(flags[i]);
        names.setText(countryNames[i]);
        return view;
    }
}
