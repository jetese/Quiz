package com.example.jetes.quiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class CustomListView extends ArrayAdapter<String> {
    private int layout;
    private List<String> mObjects;
    public CustomListView(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
        mObjects = objects;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewholder = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_image);
            viewHolder.thumbnail.setImageResource(R.drawable.civil_war_suit);
            viewHolder.button = (Button) convertView.findViewById(R.id.list_button);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (ViewHolder) convertView.getTag();
        mainViewholder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    public class ViewHolder {

        ImageView thumbnail;
        Button button;
    }
}


