/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivamarora.stepsensor.R;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.Calendar;

public class DistanceGraphFragment extends android.support.v4.app.Fragment {


    public static String mDistAverage = "0" ;
    public static String mDistTotal= "0" ;
    Calendar c = Calendar.getInstance() ;
    public static ArrayList<Integer> StepCountInaWeek ;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_distance, container, false) ;

        TextView mDistAverage_textview = (TextView)v.findViewById(R.id.FragmentDistance__averageSteps);
        TextView mDistTotal_textview = (TextView)v.findViewById(R.id.FragmentDistance_totalNoOfSteps);

        mDistAverage_textview.setText(mDistAverage + " m");
        mDistTotal_textview.setText(mDistTotal + " m");
        ValueLineChart mDistLineChart  = (ValueLineChart)v.findViewById(R.id.FragmentDistance_linechart) ;

        ValueLineSeries valueLineSeries = new ValueLineSeries() ;
        valueLineSeries.setColor(0xFF56B7F1);

        for(int i = 0 ; i<8 ; i++){
            if(i == 0)
            valueLineSeries.addPoint(new ValueLinePoint("Today" ,
                    Math.round( DistanceGraphFragment.StepCountInaWeek.get(i) / 2.82)));
            else
                valueLineSeries.addPoint(new ValueLinePoint(StepsGraphFragment.returnDayOfWeekInString(c) ,
                        Math.round( DistanceGraphFragment.StepCountInaWeek.get(i) / 2.82)));

              c.roll(Calendar.DATE, false);
        }

        mDistLineChart.addSeries(valueLineSeries);
        mDistLineChart.startAnimation();
        return v ;
    }

}
