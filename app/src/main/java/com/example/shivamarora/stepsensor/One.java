package com.example.shivamarora.stepsensor;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.amulyakhare.textdrawable.TextDrawable;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class One extends AppCompatActivity  implements SensorEventListener  {

    DrawerLayout mDrawerLayout ;
    NavigationView mNavigationView;
    android.support.v7.widget.Toolbar toolbar ;


    TextView mCaloriesTextView ;
    TextView mDistance;
    TextView mActivetime ;
    ImageView mStepCountDisplay ;

    Boolean mPlayStopStatus ;
    int mCurrentNoOfSteps = 0 ;
    String mCalories ;

    Sensor  mStepCounter ;
    SensorManager mSensorManager ;

    RippleBackground rippleBackground ;
    Calendar mCalender ;
    DbData mCurrentDataInDB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        ActiveAndroid.initialize(this);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView)findViewById(R.id.navigaitonVIew);

        mCalender = Calendar.getInstance() ;

//        Log.i("zxcv" , Calendar.MONDAY + "") ;
        mCurrentDataInDB  = SearchDBusingCalender(mCalender) ;

        if(mCurrentDataInDB == null){
            mCurrentDataInDB = new DbData(0 , mCalender.get(Calendar.DATE), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.YEAR) , mCalender.get(Calendar.DAY_OF_WEEK)) ;
            mCurrentDataInDB.save() ;
            Log.i("db" , "Creating new Db");
        }

        else {
            mCurrentNoOfSteps = mCurrentDataInDB.getDbStepCount() ;
            Log.i("db" , "Updatiog mCurrent No of Steps from db") ;

        }
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(One.this , mDrawerLayout , toolbar ,R.string.navigation_drawer_open , R.string.navigation_drawer_close ) ;
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.homeB) {

                } else if (itemId == R.id.graph) {

                } else if (itemId == R.id.history) {
                    Intent i = new Intent(One.this , History.class) ;
                    startActivity(i);

                } else if (itemId == R.id.notification) {

                } else if (itemId == R.id.persondata) {

                } else if (itemId == R.id.setting) {

                } else if (itemId == R.id.share) {

                } else if (itemId == R.id.rateus) {

                } else if (itemId == R.id.exit) {
                    One.this.finish();
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        rippleBackground = (RippleBackground)findViewById(R.id.rippleEffect);
        mPlayStopStatus = Constant.CURRENTLY_STOPPED ;

        mDistance = (TextView)findViewById(R.id.distanceCovered);
        mActivetime = (TextView)findViewById(R.id.ActiveTime) ;
        mCaloriesTextView = (TextView)findViewById(R.id.Calories);
        mStepCountDisplay = (ImageView) findViewById(R.id.image_view);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(CheckIsDateChanged(mCalender, mCurrentDataInDB)){
//            mCurrentDataInDB = new DbData(0 , mCalender.get(Calendar.DATE) ,mCalender.get(Calendar.MONTH) ,mCalender.get(Calendar.YEAR) , mCalender.get(Calendar.DAY_OF_WEEK) );
//            mCurrentDataInDB.save() ;
//            Log.i("db" , "Adding New Field To Data Base ... ") ;
//            mCurrentNoOfSteps = 0 ;
            mStepCountDisplay.setImageDrawable(TextDrawable.builder().buildRound(mCurrentNoOfSteps + "", Color.parseColor("#FF1744")));

        }
        else if(!CheckIsDateChanged(mCalender, mCurrentDataInDB)){
//            mCurrentNoOfSteps = mCurrentDataInDB.getDbStepCount() ;
            mStepCountDisplay.setImageDrawable(TextDrawable.builder().buildRound(mCurrentNoOfSteps + "", Color.parseColor("#FF1744")));

        }


        mStepCountDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayStopStatus == Constant.CURRENTLY_STOPPED) {
                    mSensorManager.registerListener(One.this, mStepCounter, SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);
                    mCurrentNoOfSteps-- ;
                    Toast.makeText(One.this, "STARTED !! ", Toast.LENGTH_SHORT).show();
                    mPlayStopStatus = Constant.CURRENTLY_PLAYING;
                    rippleBackground.startRippleAnimation();


                } else if (mPlayStopStatus == Constant.CURRENTLY_PLAYING) {

                    mPlayStopStatus = Constant.CURRENTLY_STOPPED;
                    mSensorManager.unregisterListener(One.this, mStepCounter);
                    Toast.makeText(One.this, "STOPPED !! ", Toast.LENGTH_SHORT).show();
                    rippleBackground.stopRippleAnimation();
                    mCurrentDataInDB.setDbStepCount(mCurrentNoOfSteps);
                    mCurrentDataInDB.save();

                }

            }
        }) ;

        }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if( event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            setValues(event) ;
        }
        else if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            getStepDetectorValue(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }


    void setValues(SensorEvent s){

        mCurrentNoOfSteps ++ ;
//        mCurrentNoOfSteps = Math.round(s.values[0]);
//        Float stepcount = Float.parseFloat(mCurrentNoOfSteps);
//        long distnaceInMeters = Math.round(stepcount / 2.82) ;
        long distnaceInMeters = Math.round(mCurrentNoOfSteps/2.82) ;
        mDistance.setText(distnaceInMeters + "  m ");
        mStepCountDisplay.setImageDrawable(TextDrawable.builder().buildRound(mCurrentNoOfSteps + "", Color.parseColor("#FF1744")));

        //TODO WEIGHT..............................

//        int caloriesBurnt = (int) Math.round((((0.57 * 2.21 * 85) / 4540) * stepcount)+ 7);
     long  caloriesBurnt = Math.round((((0.57 * 2.21 * 85) / 4540) * mCurrentNoOfSteps)) ;
        mCalories = caloriesBurnt + "";
        mCaloriesTextView.setText(mCalories + "  Cal");

    }


    void getStepDetectorValue(SensorEvent s){
        float[] stepDetectorValues  = s.values ;
    }



    DbData SearchDBusingCalender(Calendar c){

        DbData dbData = new Select().from(DbData.class).where(Constant.DB_COLUMN_DATE + " = ?", c.get(Calendar.DATE))
                .where(Constant.DB_COLUMN_MONTH + " = ?", c.get(Calendar.MONTH))
                .where(Constant.DB_COLUMN_YEAR + " = ?", c.get(Calendar.YEAR))
                .where(Constant.DB_COLUMN_WEEKDAYS + " = ?" , c.get(Calendar.DAY_OF_WEEK))
                .executeSingle() ;

        return dbData ;
    }


    boolean CheckIsDateChanged(Calendar c , DbData dbData){

        int currentDate = c.get(Calendar.DATE);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);
        int currentweekDay = c.get(Calendar.DAY_OF_WEEK) ;

        if(currentDate == dbData.getDbDate() && currentMonth == dbData.getDbMonth() && currentYear == dbData.getDbYear() && currentweekDay == dbData.getDbWeekDays())
            return false;

        else
            return true ;

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSensorManager.unregisterListener(this, mStepCounter);
        mCurrentDataInDB.setDbStepCount(mCurrentNoOfSteps);
        mCurrentDataInDB.save() ;

    }





/*

Steps / Day	Classification
< 5000	Sedentary
5,000-7,499	Low Active
7,500-9,999	Somewhat Active
10,000	Active
>12,500	Highly Active

(( 0.57 * 2.21 * ___kg ) / 4540 )  + 7  = calories per step

*//*


*/


    //_______________________________________________________________________________________________________________



}