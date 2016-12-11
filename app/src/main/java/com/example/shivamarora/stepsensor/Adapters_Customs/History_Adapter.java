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
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.shivamarora.stepsensor.Others.History_Extras.GroupPlusChild;
import com.example.shivamarora.stepsensor.R;

import java.util.ArrayList;

public class History_Adapter extends BaseExpandableListAdapter {

    Context context ;
    ArrayList<GroupPlusChild> groupPlusChildArrayList ;

    public History_Adapter(Context context, ArrayList<GroupPlusChild> groupPlusChildArrayList) {
        this.context = context;
        this.groupPlusChildArrayList = groupPlusChildArrayList;
    }

    @Override
    public int getGroupCount() {
        return groupPlusChildArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPlusChildArrayList.get(groupPosition).getGroupObject();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupPlusChildArrayList.get(groupPosition).getChildObject();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition*100;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*100 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View v = convertView ;

        if(v == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context) ;
            v = layoutInflater.inflate(R.layout.custom_history_layout_child_listview , parent , false) ;

        }


        TextView dateTextView = (TextView)v.findViewById(R.id.HistoryDate) ;
        TextView weekDay = (TextView)v.findViewById(R.id.HistoryWeekDayTextView) ;

        dateTextView.setText(groupPlusChildArrayList.get(groupPosition).getGroupObject().getDate());
        weekDay.setText(groupPlusChildArrayList.get(groupPosition).getGroupObject().getWeekDay());

        return  v ;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView ;

        if(v == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context) ;
            v = layoutInflater.inflate(R.layout.custom_history_layout_expandable_child_layout , parent , false) ;

        }


        TextView dateTextView = (TextView)v.findViewById(R.id.childDatePreview) ;
        TextView steps = (TextView)v.findViewById(R.id.ChildStepsPreview) ;
        TextView calories = (TextView)v.findViewById(R.id.ChildCaloriesPreview) ;
        TextView distance = (TextView)v.findViewById(R.id.ChildDistancePreview) ;


        steps.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getStepCountInChild());
        dateTextView.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getDateChild()) ;
        calories.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getCaloriesCountInChild() + " cal" ) ;
        distance.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getDistanceCountInChild() + " m ");

        return  v ;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
