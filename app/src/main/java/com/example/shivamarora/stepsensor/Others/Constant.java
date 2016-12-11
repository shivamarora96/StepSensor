/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Others;

/**
 * Created by ShivamArora on 30-08-2016.
 */
public class Constant {
    public  static final boolean CURRENTLY_STOPPED = true;
    public  static final boolean CURRENTLY_PLAYING = false;


    public  static final String DATABASE_NAME = "STEP SENSOR DATABASE";
    public  static final int DATABASE_VERSION = 1;


    public  static final String DB_COLUMN_STEPSCOUNT = "S";
    //    public  static  final String DB_COLUMN_CALORIES = "B" ;
//    public  static  final String DB_COLUMN_DISTANCE = "D" ;
    public  static final String DB_COLUMN_TIMEINMILLISEC = "T";
    public  static final String DB_COLUMN_DATE = "D";
    public  static final String DB_COLUMN_MONTH = "M";
    public  static final String DB_COLUMN_YEAR = "Y";
    public  static final String DB_COLUMN_WEEKDAYS = "W";

    public  static final String DBG_COLUMN_Senstivity = "G1";
    public  static final String DBG_COLUMN_StepSize = "G2";
    public  static final String DBG_COLUMN_StepGoal = "G3";
    public  static final String DBG_COLUMN_VERSION = "G4";
    public  static final String DBG_COLUMN_ID = "G5";

    public  static final int DBG_GENERAL_DEFAULT_VERSION = 1;

//Initialising Value
    public  static final int DB_GENERAL_DEFAULT_STEPSIZE = 36;
    public  static final long DB_GENERAL_DEFAULT_STEPGOAL = 5000;


    public  static final int DB_GENERAL_ID = 9;

    public  static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public  static final int CAMERA_MEDIA_TYPE_IMAGE = 1;
    public  static final String CAMERA_IMAGE_DIRECTORY_NAME = "Step_Sensor";
    public  static final String CAMERA_IMAGE_CONSTANT_FILE_NAME = "Step_Senor_DP";
    public  static final int GALLERY_PICK_PHOTO_CODE = 1046;
    public  static final int CAMERA_IMAGE_WIDTH = 200;
    public  static final int GOOGLE_PLUS_SIGNIN_REQUESTCODE = 1909;

    public  static final int GOOGLE_PLUS_ALREADY_LOGIN = -56;
    public  static final int GOOGLE_PLUS_LOGIN = 66;
    public  static final int GOOGLE_PLUS_LOGOUT = 8896;


    public  static final int DB_GENERAL_SENSTIVITY_LOW = 1;
    public  static final int DB_GENERAL_SENSTIVITY_MEDIUM = 2;
    public  static final int DB_GENERAL_SENSTIVITY_HIGH = 3;

    public  static final int DB_GENERAL_SENSTIVITY_FASTEST = 0;
    public  static final int DB_GENERAL_SENSTIVITY_DEFAULT = 2;


}

   /*

calories ---------- Math.round((((0.57 * 2.21 * 85) / 4540) * totalStepsInWeek))
dis --------------- Math.round(averageStepsInWeek/2.82)


by default step size is 36


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






