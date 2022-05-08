package com.example.playware_final_project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyListView extends BaseAdapter {
    private final Context context;
    private final String[] username;
    private final Integer[] score;
    private final Integer[] index;

    public MyListView(Context context, String[] username,Integer[] score, Integer[] index) {
        this.context=context;
        this.username=username;
        this.score=score;
        this.index=index;
    }


    @Override
    public int getCount() {
        return username.length;
    }

    @Override
    public Object getItem(int i) {
        return username[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.ranking_layout, parent,false);
        TextView indexText = (TextView) rowView.findViewById(R.id.rank_number);
        if(position == 0){
            indexText.setBackgroundColor(Color.parseColor("#E5B80B"));
        }else if(position == 1){
            indexText.setBackgroundColor(Color.parseColor("#B1B8BA"));
        }else if(position == 2){
            indexText.setBackgroundColor(Color.parseColor("#CF943A"));
        }
        indexText.setText(String.valueOf(this.index[position]));
        TextView username = (TextView) rowView.findViewById(R.id.username);
        TextView score = (TextView) rowView.findViewById(R.id.score);
        username.setText(this.username[position]);
        score.setText(String.valueOf(this.score[position]));

        return rowView;

    }
}
