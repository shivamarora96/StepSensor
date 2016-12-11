/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Adapters_Customs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shivamarora.stepsensor.R;

import java.util.ArrayList;

public class Settings_Adapter extends ArrayAdapter<String> {

    Context context ;
    ArrayList<String> arrayList ;

    public Settings_Adapter(Context context , ArrayList<String> arrayList) {
        super(context, 0);
        this.arrayList = arrayList ;
        this.context = context ;

    }


    @Override
    public int getCount() {
        return arrayList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = convertView ;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.custom_child_setting_listview , parent , false) ;
        }


        TextView textView = (TextView)view.findViewById(R.id.Setting_child_ListView_TextView) ;
        textView.setText(arrayList.get(position));


        return  view ;


    }




    @Override
    public String getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        long itemId = position*100 + 100 ;
        return itemId ;
    }





}
