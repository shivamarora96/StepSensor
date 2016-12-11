/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Adapters_Customs;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shivamarora.stepsensor.Fragments.CaloriesGraphFragment;
import com.example.shivamarora.stepsensor.Fragments.DistanceGraphFragment;
import com.example.shivamarora.stepsensor.Fragments.StepsGraphFragment;

public class Graphical_Adapter extends FragmentPagerAdapter {

    private  final int tabCount = 3 ;
    private final String[] tabTitle = {"DISTANCE" , "STEPS" , "CALORIES"} ;


    public Graphical_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        if(position == 1)
        return new StepsGraphFragment() ;

        else if(position == 2)
            return new CaloriesGraphFragment() ;

        else
            return new DistanceGraphFragment() ;

    }


    @Override
    public int getCount() {
        return  tabCount ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position] ;
    }
}
