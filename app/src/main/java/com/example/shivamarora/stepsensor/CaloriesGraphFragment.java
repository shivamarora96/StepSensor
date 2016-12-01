/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Calendar;

public class CaloriesGraphFragment extends android.support.v4.app.Fragment {

    static String mCaloreisAverage= "0" ;
    static String  mCaloreisTotal= "0" ;
     Calendar calen = Calendar.getInstance() ;
    static ArrayList<Integer> StepCountInaWeek ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calories, container, false) ;

        TextView mCaloreisAverage_textview = (TextView)v.findViewById(R.id.FragmentCalories__averageSteps) ;
        TextView mCaloreisTotal_textview = (TextView)v.findViewById(R.id.FragmentCalories_totalNoOfSteps) ;
        mCaloreisAverage_textview.setText(mCaloreisAverage + " cal");
        mCaloreisTotal_textview.setText(mCaloreisTotal + " cal");

      PieChart mCaloriesPieChart = (PieChart)v.findViewById(R.id.piechart) ;

       for(int i = 0 ; i< 8 ; i++){
           if(i == 0)
                 mCaloriesPieChart.addPieSlice(new PieModel("Today" ,
                   Math.round((((0.57 * 2.21 * 85) / 4540) * StepCountInaWeek.get(0))) , Color.parseColor("#abc123")));

           else if(i%2 == 0 && i%3 != 0)
                    mCaloriesPieChart.addPieSlice(new PieModel(StepsGraphFragment.returnDayOfWeekInString(calen) ,
                     Math.round((((0.57 * 2.21 * 85) / 4540) * StepCountInaWeek.get(i))) , Color.parseColor("#006699")));

           else if(i%3 == 0)
                    mCaloriesPieChart.addPieSlice(new PieModel(StepsGraphFragment.returnDayOfWeekInString(calen),
                       Math.round((((0.57 * 2.21 * 85) / 4540) * StepCountInaWeek.get(i))), Color.parseColor("#ffff00")));
           else
               mCaloriesPieChart.addPieSlice(new PieModel(StepsGraphFragment.returnDayOfWeekInString(calen),
                       Math.round((((0.57 * 2.21 * 85) / 4540) * StepCountInaWeek.get(i))), Color.parseColor("#FFC0CB")));

           calen.roll(Calendar.DATE, false);
       }

        mCaloriesPieChart.startAnimation();

        return v ;

    }
}
