package com.example.shivamarora.stepsensor;


import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.Calendar;


//Graphical Analysis Activity Main ..........................................................................


public class GraphicalAnalysis extends AppCompatActivity {

    private TabLayout mGraphical_tabLayout ;
    private ViewPager mGraphical_viewPager ;
    private CustomTabAdapter mGraphical_Adapter ;

    private  ArrayList<DbData> dbDataArrayList ;    //Take DbData from database and stores in ArrayList size = 8
    private Calendar calendar ;
    ArrayList<Integer> StepsCountInWeek ;           //Only Step Count in a week size = 8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphical_analysis);

        //Initialising DataBase ...................
        ActiveAndroid.initialize(this);

        //setting up toolbar

        Toolbar toolbar = (Toolbar) findViewById(R.id.Graphical_Toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//            getSupportActionBar().set
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mGraphical_tabLayout = (TabLayout) findViewById(R.id.Graphical_tabLayout);
        mGraphical_viewPager = (ViewPager) findViewById(R.id.Graphical_ViewPager);
        mGraphical_Adapter = new CustomTabAdapter(getSupportFragmentManager());
        calendar = Calendar.getInstance();

mGraphical_viewPager.setOffscreenPageLimit(3);

                dbDataArrayList = new ArrayList<DbData>(8);

        //        Log.i("poiuytrewq",calendar.get(Calendar.DAY_OF_WEEK) + "") ;
                                                                /*
                                                                                     Sun - 1
                                                                                     Mon - 2
                                                                                           .
                                                                                           .
                                                                                     Sat - 7

                                                                                                    */
        //TODO.............

        mGraphical_viewPager.setAdapter(mGraphical_Adapter);
        mGraphical_viewPager.setCurrentItem(1);
        mGraphical_tabLayout.setupWithViewPager(mGraphical_viewPager);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            GraphicalAnalysis.this.finish();
        }


        return true ;
    }

    @Override
    protected void onResume() {
        super.onResume();

           ArrayList<DbData> historyAll = (ArrayList) new Select().from(DbData.class).execute() ;


           for(int i = 0 ; i<8  ; i++) {
               int j = 0;
               while (j<historyAll.size()) {
                   if(calendar.get(Calendar.DATE) == historyAll.get(j).getDbDate() && calendar.get(Calendar.MONTH) == historyAll.get(j).getDbMonth() && calendar.get(Calendar.YEAR) == historyAll.get(j).getDbYear() )
                   {
                       dbDataArrayList.add(i,historyAll.get(j));
                       break;
                   }

                   j++ ;

                   if ( j == historyAll.size() )
                       dbDataArrayList.add(i , null) ;

               }


               calendar = DecrementCalenderDateByOne(calendar) ;
           }


           StepsCountInWeek = new ArrayList<>(8) ;
           int sumStepCount = 0 ;
           for(int i = 0 ; i< 8 ; i++){
               DbData currentData =  dbDataArrayList.get(i) ;
               if(currentData==null) {
                   StepsCountInWeek.add(i, 0);
               }

               else if(currentData!=null) {
                   sumStepCount = sumStepCount + currentData.getDbStepCount() ;
                   StepsCountInWeek.add(i, currentData.getDbStepCount());
               }
           }


        int totalStepsInWeek = sumStepCount ;
        int averageStepsInWeek = sumStepCount/8 ;

        long totalsCaloriesInWeek  =  Math.round((((0.57 * 2.21 * 85) / 4540) * totalStepsInWeek)) ;
        long  averageCaloriesInWeek = Math.round((((0.57 * 2.21 * 85) / 4540) * averageStepsInWeek)) ;

        long total_distnaceInWeek = Math.round(totalStepsInWeek/2.82) ;
        long average_distnaceInWeek = Math.round(averageStepsInWeek/2.82) ;


        StepsGraphFragment.mStepsTotal = totalStepsInWeek + "" ;
        StepsGraphFragment.mStepsAverage = averageStepsInWeek + "" ;
        StepsGraphFragment.StepCountInaWeek = StepsCountInWeek ;


        DistanceGraphFragment.mDistTotal = total_distnaceInWeek + "" ;
        DistanceGraphFragment.mDistAverage = average_distnaceInWeek + "" ;
        DistanceGraphFragment.StepCountInaWeek = StepsCountInWeek ;


        CaloriesGraphFragment.mCaloreisTotal = totalsCaloriesInWeek + "" ;
        CaloriesGraphFragment.mCaloreisAverage = averageCaloriesInWeek + "" ;
        CaloriesGraphFragment.StepCountInaWeek = StepsCountInWeek ;

    }


    Calendar DecrementCalenderDateByOne(Calendar calendar){
               calendar.roll(Calendar.DATE, false);
               return calendar ;
           }



}




//Custum Adapter .................................................................................


class CustomTabAdapter extends FragmentPagerAdapter{

    private  final int tabCount = 3 ;
    private final String[] tabTitle = {"DISTANCE" , "STEPS" , "CALORIES"} ;


    public CustomTabAdapter(FragmentManager fm) {
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


//StepsCount Fragment ....................................................


//Distance Count Fragment ..............................................................................

//Calories Count Fragment ............................................................................

