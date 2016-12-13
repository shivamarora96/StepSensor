/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.amulyakhare.textdrawable.TextDrawable;
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
import com.bumptech.glide.Glide;
import com.example.shivamarora.stepsensor.Others.Constant;
import com.example.shivamarora.stepsensor.Database_Models.DbData;
import com.example.shivamarora.stepsensor.Database_Models.DbGeneral;
import com.example.shivamarora.stepsensor.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pkmmte.view.CircularImageView;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Main extends AppCompatActivity  implements SensorEventListener  {

    DrawerLayout mDrawerLayout ;
    NavigationView mNavigationView;
    android.support.v7.widget.Toolbar toolbar ;


    CircularImageView mHeaderDP ;
    TextView mHeaderName ;
    AdView adView ;

    int mDisplayfonstSize = 200 ;


    TextView mCaloriesTextView ;
    TextView mDistance;
    TextView mActivetime ;
    ImageView mStepCountDisplay ;
    TextView mStepsInHeader ;
    TextView mCurrentStepText ;
    long startTime ;
    long endTime;

    Boolean mPlayStopStatus ;
    int mCurrentNoOfSteps = 0 ;
    String mCalories ;

    Sensor  mStepCounter ;
    SensorManager mSensorManager ;

    RippleBackground rippleBackground ;
    Calendar mCalender ;
    DbData mCurrentDataInDB ;

    int yesterDayStepCount ;
    long yesterDayCal ;
    long yesterdayDist ;

    DbGeneral mDbGeneral ;
    ActionBarDrawerToggle actionBarDrawerToggle ;

    int backPressedCount = 0 ;


//NEVER START THIS ACTIVTY WITH INTENT OTTHER THAN GOOGLESINGIN ACTIVITY ............................


    @SuppressWarnings("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialising ActiveAndroid DataBase ....................................
        ActiveAndroid.initialize(this);
        List<DbGeneral> dbGeneralList =  new Select().from(DbGeneral.class).execute() ;

        if(dbGeneralList.size() == 0){
            Log.i("a" , "c") ;
            mDbGeneral = new DbGeneral(Constant.DB_GENERAL_SENSTIVITY_MEDIUM , Constant.DB_GENERAL_DEFAULT_STEPSIZE
                    , Constant.DB_GENERAL_DEFAULT_STEPGOAL ,Constant.DBG_GENERAL_DEFAULT_VERSION) ;
            mDbGeneral.save();
        }

        else {
            if(dbGeneralList.get(0)!=null) {
                Log.i("a" , "a") ;
                mDbGeneral = dbGeneralList.get(0);
            }

            else{
                Log.i("a" , dbGeneralList.size() + "") ;

                mDbGeneral = new DbGeneral(Constant.DB_GENERAL_SENSTIVITY_MEDIUM , Constant.DB_GENERAL_DEFAULT_STEPSIZE
                        , Constant.DB_GENERAL_DEFAULT_STEPGOAL ,Constant.DBG_GENERAL_DEFAULT_VERSION) ;
                mDbGeneral.save();
            }


        }

        Log.i("check" , mDbGeneral.getDbStepGoal() + "");


        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView)findViewById(R.id.navigaitonVIew);
        yesterDayStepCount = 0 ;
        mCalender = Calendar.getInstance() ;


        mCurrentDataInDB  = SearchDBusingCalender(mCalender) ;

        if(mCurrentDataInDB == null){


            //Adding yesterday AlertDialogue ..................
            ArrayList<DbData> historyAll = (ArrayList) new Select().from(DbData.class).execute() ;

            Calendar calendar = Calendar.getInstance() ;
            calendar.roll(Calendar.DATE, false);

            for(int j = 0 ; j<historyAll.size() ; j++){
                if(calendar.get(Calendar.DATE) == historyAll.get(j).getDbDate() && calendar.get(Calendar.MONTH) == historyAll.get(j).getDbMonth() && calendar.get(Calendar.YEAR) == historyAll.get(j).getDbYear()){
                   yesterDayStepCount =  historyAll.get(j).getDbStepCount() ;
                    Log.i("check" , mDbGeneral.getDbStepSize() + "");
                    yesterDayCal = Math.round((((0.57 * (100 / mDbGeneral.getDbStepSize()) * 85) / 4540) * yesterDayStepCount));
                    yesterdayDist = Math.round(yesterDayStepCount*mDbGeneral.getDbStepSize()/100) ;
                    break ;
                }
            }



if(historyAll.size()!=0) {
    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Main.this, SweetAlertDialog.SUCCESS_TYPE);
    sweetAlertDialog.setTitleText("YESTERDAY'S PROGRESS  \n \n ");
    sweetAlertDialog.setContentText("STEP COUNT : " + yesterDayStepCount + "\n \n" + "CALORIES : " + yesterDayCal + " cal \n \n"
            + "DISTANCE  : " + yesterdayDist + " m \n \n");

    sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
    sweetAlertDialog.show();

}



            mCurrentDataInDB = new DbData(0 , mCalender.get(Calendar.DATE), mCalender.get(Calendar.MONTH), mCalender.get(Calendar.YEAR) , mCalender.get(Calendar.DAY_OF_WEEK) , 0) ;
            mCurrentDataInDB.save() ;
            Log.i("db" , "Creating new Db");
        }

        else {


            if(mCurrentNoOfSteps < 9999){
                mDisplayfonstSize = 250 ;
            }
            else {
                mDisplayfonstSize = 200 ;
            }
            mCurrentNoOfSteps = mCurrentDataInDB.getDbStepCount() ;
//            mCurrentNoOfSteps = 0;
            Log.i("db" , "Updatiog mCurrent No of Steps from db") ;

        }
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         actionBarDrawerToggle = new ActionBarDrawerToggle(Main.this , mDrawerLayout , toolbar ,R.string.navigation_drawer_open , R.string.navigation_drawer_close ) ;
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.gpswalk) {

                    TextView textView = new TextView(getApplicationContext());
                    textView.setTextColor(Color.MAGENTA);
                    textView.setTextSize(10);
                    textView.setText("comming soon ");
                    item.setActionView(textView);


                } else if (item.getItemId() == R.id.graph) {
                    Intent i = new Intent(Main.this, GraphicalAnalysis.class);
                    startActivity(i);

                } else if (item.getItemId() == R.id.history) {
                    Intent i = new Intent(Main.this, History.class);
                    startActivity(i);

                } else if (item.getItemId() == R.id.notification) {

                    TextView textView = new TextView(getApplicationContext());
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(20);
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                    textView.setPadding(20, 20, 20, 20);
                    textView.setBackgroundColor(Color.RED);
                    textView.setText("0");
                    item.setActionView(textView);


                } else if (item.getItemId() == R.id.persondata) {
                    int currentLoginStatus = getIntent().getIntExtra("loginstatus", 0);
                    String picUrl = "";
                    String name = "";
                    String email = "" ;

                    if (currentLoginStatus == Constant.GOOGLE_PLUS_ALREADY_LOGIN || currentLoginStatus == Constant.GOOGLE_PLUS_LOGIN) {
                        picUrl = getIntent().getStringExtra("photo");
                        name = getIntent().getStringExtra("name");
                        email = getIntent().getStringExtra("email") ;

                    }

                    Intent i = new Intent(Main.this, Me.class);
                    i.putExtra("DP", picUrl);
                    i.putExtra("name", name);
                    i.putExtra("loginstatus", currentLoginStatus);
                    i.putExtra("email" , email) ;
                    startActivity(i);
//                    Main.this.finish();


                } else if (item.getItemId() == R.id.setting) {
                    int currentLoginStatus = getIntent().getIntExtra("loginstatus", 0);
                    Intent i = new Intent(Main.this, Setting_Activity.class);
                    i.putExtra("loginstatus", currentLoginStatus);
                    startActivity(i);

                } else if (item.getItemId() == R.id.share) {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "market://details?id=";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));

                } else if (item.getItemId() == R.id.rateus) {
                    //TODO .. put playstore link here
                    String appStoreUri = "market://details?id=" + getPackageName();
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appStoreUri));
                    startActivity(rateIntent);


                } else if (item.getItemId() == R.id.exit) {
                    Main.this.finish();
                }

                if (item.getItemId() != R.id.gpswalk && item.getItemId() != R.id.notification)
                    mDrawerLayout.closeDrawer(GravityCompat.START, true);


                return true;
            }
        });


        rippleBackground = (RippleBackground)findViewById(R.id.rippleEffect);
        mPlayStopStatus = Constant.CURRENTLY_STOPPED ;

        mDistance = (TextView)findViewById(R.id.distanceCovered);
        mActivetime = (TextView)findViewById(R.id.ActiveTime) ;
        mCaloriesTextView = (TextView)findViewById(R.id.Calories);
        mStepCountDisplay = (ImageView) findViewById(R.id.image_view);

        mStepsInHeader = (TextView)mNavigationView.getHeaderView(0).findViewById(R.id.Header_averageSteps) ;
        mCurrentStepText = (TextView)findViewById(R.id.OneCurrentNoofSteps);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


        mHeaderDP = (CircularImageView)mNavigationView.getHeaderView(0).findViewById(R.id.NavigationDrawer_DP);
        mHeaderName = (TextView)mNavigationView.getHeaderView(0).findViewById(R.id.NavigationDrawer_userName) ;


        Intent a = getIntent() ;
        int currentLoginStatus = a.getIntExtra("loginstatus", Constant.GOOGLE_PLUS_LOGOUT) ;

        if(currentLoginStatus == Constant.GOOGLE_PLUS_ALREADY_LOGIN || currentLoginStatus == Constant.GOOGLE_PLUS_LOGIN){
            mHeaderName.setText(a.getStringExtra("name").toUpperCase());
            String picUrl = a.getStringExtra("photo") ;

            mHeaderDP.setImageResource(R.drawable.ic_launcher);
            if(picUrl.length()>=6)
                Glide.with(getApplicationContext()).load(picUrl).into(mHeaderDP);
        }
        else{
            mHeaderName.setText("STEP SENSOR");
            mHeaderDP.setImageResource(R.drawable.ic_launcher);
//            Glide.with(getApplicationContext()).fromResource(R.drawable.ic_launcher).into(mHeaderDP) ;
        }



        List<DbData> historyOfData = new Select().from(DbData.class).execute() ;
        if(historyOfData.size() == 1 && historyOfData.get(0).getDbStepCount() == 0) {
            Show_Case_Calling();
        }



    }

    @Override
    protected void onStart() {
        //Displaying Ads ....

        adView = (AdView)findViewById(R.id.MainScreen_bannerAds) ;
        adView.loadAd(new AdRequest.Builder()
                .build()
        );

        super.onStart();
    }

    @Override
    public void onBackPressed() {

            backPressedCount ++ ;

         if(backPressedCount == 1)
            Toast.makeText(getApplicationContext() , "Press Back Two Times To Exit !! " , Toast.LENGTH_SHORT).show();

        else if(backPressedCount == 2) {
             super.onBackPressed();
             Main.this.finish();
         }


    }

    @Override
    protected void onPause() {
        super.onPause();
        mDbGeneral.save();
    }

    void Show_Case_Calling() {

        final ShowcaseView mainShow =  new ShowcaseView.Builder(Main.this)
                .blockAllTouches()
                .setContentTitle("WELCOME TO STEP SENSOR")
                .setStyle(R.style.CustomShowcaseTheme3)
                .setContentText(" \n \n \n \n \n CREATED BY - \n SHIVAM ARORA ")
                .hideOnTouchOutside()
                .build() ;
        mainShow.setButtonText("CONTINUE");

        final ShowcaseView showcaseview1 =  new ShowcaseView.Builder(Main.this)
                .setTarget(new ViewTarget(mStepCountDisplay))
                .blockAllTouches()
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitle("STEPS COUNT DISPLAY")
                .setContentText("Click On It To Start Or Stop The Step Sensor :) ")
                .hideOnTouchOutside()
                .build() ;
        showcaseview1.setShowcase(new ViewTarget(mStepCountDisplay) , true);

        final ShowcaseView showcaseviewcurrentSteps =  new ShowcaseView.Builder(Main.this)
                .setTarget(new ViewTarget(mStepCountDisplay))
                .blockAllTouches()
                .setStyle(R.style.CustomShowcaseTheme4)
                .setContentTitle("RECORD YOUR CURRENT STEPS")
                .setContentText("\n\n\n DISPLAYS YOUR COUNT OF CURRENT STEPS !! \n \n Move Ahead :->  ")
                .hideOnTouchOutside()
                .build() ;
        showcaseviewcurrentSteps.setShowcase(new ViewTarget(mCurrentStepText) , true);

        final ShowcaseView showcase2 =  new ShowcaseView.Builder(this)
                .setTarget(new PointTarget(new Point(100, 100)))
                .blockAllTouches()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("SLIDE IT !! HAVE FUN")
                .setContentText(" \n \n \n Slide To Open Up The Drawer ")
                .hideOnTouchOutside()
                .build() ;
        final ShowcaseView showcase3 =  new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(mDistance))
                .blockAllTouches()
                .setContentTitle("HOW MUCH I TRAVEL ?")
                .setStyle(R.style.CustomShowcaseTheme4)
                .setContentText("\n\n\nDisplays The Distance Covered in 'metres' ThroughOut a Day :) ")
                .hideOnTouchOutside()
                .build() ;

        final ShowcaseView showcase4 =  new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(mCaloriesTextView))
                .blockAllTouches()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentTitle("HOW MUCH CALORIES  I BURNT ?")
                .setContentText("\n\n\nCalories Burnt in 'cal' ThroughOut a Day ")
                .hideOnTouchOutside()
                .build() ;

        final ShowcaseView showcase5 =  new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(mActivetime))
                .blockAllTouches()
                .setContentTitle("AM I ACTIVE OR LAZY ? ")
                .setStyle(R.style.CustomShowcaseTheme3)
                .setContentText("\n\n\nMeasures Current Active Time :) ")
                .hideOnTouchOutside()
                .build() ;


        //Sequence ....................

        showcase2.hide();
        showcase3.hide();
        showcase4.hide();
        showcase5.hide();
        showcaseview1.hide();
        showcaseviewcurrentSteps.hide();
        mainShow.show();


        mainShow.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainShow.hide();
                showcaseview1.show();
            }
        });

        showcaseview1.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcaseview1.hide();
                showcase2.show();
            }
        });

        showcase2.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showcase2.hide();
                showcase3.show();

            }
        });

        showcase3.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showcase3.hide();
                showcase4.show();
            }
        });

        showcase4.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showcase4.hide();
                showcase5.show();
            }
        });

        showcase5.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcase5.hide();
                showcaseviewcurrentSteps.show();
            }
        });

    showcaseviewcurrentSteps.overrideButtonClick(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showcaseviewcurrentSteps.hide();
        }
    });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(Main.this);
        menuInflater.inflate(R.menu.action_bar_menu, menu);


        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


//RateUs

        if(item.getItemId() == R.id.rating ){

            //TODO .. put playstore link here
            String appStoreUri = "market://details?id=" + getPackageName();
            Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appStoreUri));
            startActivity(rateIntent);

        }


        else if (item.getItemId() == R.id.Account) {

            int currentLoginStatus = getIntent().getIntExtra("loginstatus", 0);
            Intent toGoogleSigniIn = new Intent(Main.this, GoogleSigniIn.class);

            if (currentLoginStatus == Constant.GOOGLE_PLUS_ALREADY_LOGIN || currentLoginStatus == Constant.GOOGLE_PLUS_LOGIN) {
                toGoogleSigniIn.putExtra("request_LOGIN_LOGOUT", Constant.GOOGLE_PLUS_LOGOUT);
                startActivity(toGoogleSigniIn);
                finish();
            } else if (currentLoginStatus == Constant.GOOGLE_PLUS_LOGOUT) {

                toGoogleSigniIn.putExtra("request_LOGIN_LOGOUT", Constant.GOOGLE_PLUS_LOGIN);
                startActivity(toGoogleSigniIn);
                finish();


            }


        }



        //Sharing ....
        else if(item.getItemId() == R.id.sharing){


            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "market://details?id=";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

            Toast.makeText(Main.this , item.getTitle() , Toast.LENGTH_SHORT).show();
        }


        //About Us !!!
        else if(item.getItemId() == R.id.Infoing){

            AlertDialog alertDialog = new AlertDialog.Builder(Main.this ).create() ;
            alertDialog.setIcon(R.drawable.ic_launcher);
            alertDialog.setTitle(" STEP SENSOR");
            alertDialog.setMessage("\n\nLight Weight , Easy To Install and Calibrate.\nSTEP SENSOR counts your daily active steps and is battery Efficient \n\n\n  CREATED BY :- \n\n SHIVAM ARORA");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }

    return true ;
    }


    @Override
    protected void onStop() {
        adView.destroy();
        super.onStop();
    }

    @Override
    protected void onResume() {

        super.onResume();


        if(CheckIsDateChanged(mCalender, mCurrentDataInDB)){
//            mCurrentDataInDB = new DbData(0 , mCalender.get(Calendar.DATE) ,mCalender.get(Calendar.MONTH) ,mCalender.get(Calendar.YEAR) , mCalender.get(Calendar.DAY_OF_WEEK) );
//            mCurrentDataInDB.save() ;
//            Log.i("db" , "Adding New Field To Data Base ... ") ;
//            mCurrentNoOfSteps = 0 ;



            mStepCountDisplay.setImageDrawable(TextDrawable
                            .builder()
                            .beginConfig()
                            .bold()
                            .withBorder(4)
                            .fontSize(mDisplayfonstSize)
                            .endConfig()
                            .buildRound(mCurrentNoOfSteps + "", Color.parseColor("#FF1744"))

            );
            mStepsInHeader.setText(mCurrentNoOfSteps - mCurrentDataInDB.getDbStepCount() + "");
            mCurrentStepText.setText(mCurrentNoOfSteps - mCurrentDataInDB.getDbStepCount() + "");
        }
        else if(!CheckIsDateChanged(mCalender, mCurrentDataInDB)){
//            mCurrentNoOfSteps = mCurrentDataInDB.getDbStepCount() ;


            if(mCurrentNoOfSteps < 9999){
                mDisplayfonstSize = 250 ;
            }
            else {
                mDisplayfonstSize = 200 ;
            }


            mStepCountDisplay.setImageDrawable(TextDrawable
                            .builder()
                            .beginConfig()
                            .bold()
                            .withBorder(4)
                            .fontSize(mDisplayfonstSize)
                            .endConfig()
                            .buildRound(mCurrentNoOfSteps + "", Color.parseColor("#FF1744"))

            );
            mStepsInHeader.setText(mCurrentNoOfSteps - mCurrentDataInDB.getDbStepCount() + "");
            mCurrentStepText.setText(mCurrentNoOfSteps - mCurrentDataInDB.getDbStepCount() + "");
        }


        mStepCountDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCurrentNoOfSteps < 9999){
                    mDisplayfonstSize = 250 ;
                }
                else {
                    mDisplayfonstSize = 200 ;
                }



                if (mPlayStopStatus == Constant.CURRENTLY_STOPPED) {
                    mSensorManager.registerListener(Main.this, mStepCounter, mDbGeneral.getDbSenstivity());
                    mCurrentNoOfSteps-- ;
                    mPlayStopStatus = Constant.CURRENTLY_PLAYING;
                    rippleBackground.startRippleAnimation();

                    startTime = System.currentTimeMillis();

                    Snackbar s = Snackbar.make(v, "STARTED !! ", Snackbar.LENGTH_LONG) ;
                    s.setActionTextColor(Color.RED);
                    s.setAction(" STOP ", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            endTime = System.currentTimeMillis() - startTime;
                            mActivetime.setText(endTime/60000 + " :: " + (endTime/1000) %60);

                            mPlayStopStatus = Constant.CURRENTLY_STOPPED;
                            mSensorManager.unregisterListener(Main.this, mStepCounter);
                            rippleBackground.stopRippleAnimation();
                            mCurrentDataInDB.setDbStepCount(mCurrentNoOfSteps);
                            long activeTime = mCurrentDataInDB.getDbActiveTimeInMilli() + endTime ;
                            mCurrentDataInDB.setDbActiveTimeInMilli(activeTime);
                            mCurrentDataInDB.save();

                        }
                    }) ;
                    s.show();



                } else if (mPlayStopStatus == Constant.CURRENTLY_PLAYING) {

                    endTime = System.currentTimeMillis() - startTime;
                    mActivetime.setText(endTime/60000 + " :: " + (endTime/1000) %60);

                    if(mCurrentNoOfSteps < 9999){
                        mDisplayfonstSize = 250 ;
                    }
                    else {
                        mDisplayfonstSize = 200 ;
                    }

                    mPlayStopStatus = Constant.CURRENTLY_STOPPED;
                    mSensorManager.unregisterListener(Main.this, mStepCounter);
                    rippleBackground.stopRippleAnimation();
                    mCurrentDataInDB.setDbStepCount(mCurrentNoOfSteps);
                    long activeTime = mCurrentDataInDB.getDbActiveTimeInMilli() + endTime ;
                    mCurrentDataInDB.setDbActiveTimeInMilli(activeTime);
                    mCurrentDataInDB.save();


                    Snackbar s = Snackbar.make(v, "STOPPED !! ", Snackbar.LENGTH_LONG);
                    s.setActionTextColor(Color.GREEN);
                    s.setAction(" START  ", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(mCurrentNoOfSteps < 9999){
                                mDisplayfonstSize = 250 ;
                            }
                            else {
                                mDisplayfonstSize = 200 ;
                            }

                            mSensorManager.registerListener(Main.this, mStepCounter, mDbGeneral.getDbSenstivity());
                            mCurrentNoOfSteps--;
                            mPlayStopStatus = Constant.CURRENTLY_PLAYING;
                            rippleBackground.startRippleAnimation();
                            startTime = System.currentTimeMillis();

                        }
                    }) ;
                    s.show();


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
        long distnaceInMeters = Math.round((mCurrentNoOfSteps* mDbGeneral.getDbStepSize() / 100)) ;
        mDistance.setText(distnaceInMeters + "  m ");

        if(mCurrentNoOfSteps < 9999){
            mDisplayfonstSize = 250 ;
        }
        else {
            mDisplayfonstSize = 200 ;
        }

        mStepCountDisplay.setImageDrawable(TextDrawable
                        .builder()
                        .beginConfig()
                        .bold()
                        .withBorder(4)
                        .fontSize(mDisplayfonstSize)
                        .endConfig()
                        .buildRound(mCurrentNoOfSteps + "", Color.parseColor("#FF1744"))

        );

        mStepsInHeader.setText(mCurrentNoOfSteps - mCurrentDataInDB.getDbStepCount() + "");
        mCurrentStepText.setText(mCurrentNoOfSteps - mCurrentDataInDB.getDbStepCount() + "");
        //TODO WEIGHT..............................

//        int caloriesBurnt = (int) Math.round((((0.57 * 100/stepsizeincm * 85) / 4540) * stepcount)+ 7);
     long  caloriesBurnt = Math.round((((0.57 * (100/mDbGeneral.getDbStepSize()) * 85) / 4540) * mCurrentNoOfSteps)) ;
        mCalories = caloriesBurnt + "";
        mCaloriesTextView.setText(mCalories + "  Cal");




        endTime = System.currentTimeMillis() - startTime ;
        mActivetime.setText(endTime/60000 + " :: " + (endTime/1000) %60);




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
        endTime = System.currentTimeMillis() - startTime;
        mActivetime.setText(endTime / 60000 + " :: " + (endTime / 1000) % 60);

        long activeTime = mCurrentDataInDB.getDbActiveTimeInMilli() + endTime ;

        mCurrentDataInDB.setDbActiveTimeInMilli(activeTime);
        mCurrentDataInDB.setDbStepCount(mCurrentNoOfSteps);
        mCurrentDataInDB.save() ;

    }




}

