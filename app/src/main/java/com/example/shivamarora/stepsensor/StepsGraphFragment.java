/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.Calendar;

public class StepsGraphFragment extends android.support.v4.app.Fragment {

    static  String mStepsAverage = "0" ;
    static  String mStepsTotal = "0" ;
     Calendar calendar = Calendar.getInstance() ;
    static ArrayList<Integer> StepCountInaWeek ;

    public StepsGraphFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps_graphical, container, false) ;

        TextView mStepsAverage_textview = (TextView) view.findViewById(R.id.FragmentSteps_averageSteps);
        mStepsAverage_textview.setText(mStepsAverage);

        TextView mStepsTotal_textview = (TextView)view.findViewById(R.id.FragmentSteps_totalNoOfSteps);
        mStepsTotal_textview.setText(mStepsTotal);

        BarChart mStepsBarChart = (BarChart)view.findViewById(R.id.FragmentSteps_stepsBarChart);


        for(int i = 0 ; i<8 ; i++) {
            if(i == 0)
                mStepsBarChart.addBar(new BarModel("TODAY" , StepCountInaWeek.get(0) , 0xFF63CBB0));

            else
                mStepsBarChart.addBar(new BarModel(StepsGraphFragment.returnDayOfWeekInString(calendar), StepCountInaWeek.get(i), 0xFF56B7F1));

            calendar.roll(Calendar.DATE , false);
        }
        mStepsBarChart.startAnimation();

        return view ;
    }



   static String returnDayOfWeekInString(Calendar c){

        int calenderWeekEncoding = c.get(Calendar.DAY_OF_WEEK) ;

        if(calenderWeekEncoding == Calendar.MONDAY )
           return "Mon" ;

        else if(calenderWeekEncoding == Calendar.TUESDAY)
            return "Tue";
        else if(calenderWeekEncoding == Calendar.WEDNESDAY)
            return "Wed";
        else if(calenderWeekEncoding == Calendar.THURSDAY)
            return "Thur";
        else if(calenderWeekEncoding == Calendar.FRIDAY)
            return "Fri";
        else if(calenderWeekEncoding == Calendar.SATURDAY)
            return "Sat";
        else if(calenderWeekEncoding == Calendar.SUNDAY)
            return "Sun";

       return ".." ;

    }

}
