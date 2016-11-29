/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor;

/**
 * Created by ShivamArora on 30-08-2016.
 */
public class Constant {
    static final boolean CURRENTLY_STOPPED = true;
    static final boolean CURRENTLY_PLAYING = false;


    static final String DATABASE_NAME = "STEP SENSOR DATABASE";
    static final int DATABASE_VERSION = 1;


    static final String DB_COLUMN_STEPSCOUNT = "S";
    //    static  final String DB_COLUMN_CALORIES = "B" ;
//    static  final String DB_COLUMN_DISTANCE = "D" ;
    static final String DB_COLUMN_TIMEINMILLISEC = "T";
    static final String DB_COLUMN_DATE = "D";
    static final String DB_COLUMN_MONTH = "M";
    static final String DB_COLUMN_YEAR = "Y";
    static final String DB_COLUMN_WEEKDAYS = "W";

    static final String DBG_COLUMN_Senstivity = "G1";
    static final String DBG_COLUMN_StepSize = "G2";
    static final String DBG_COLUMN_StepGoal = "G3";
    static final String DBG_COLUMN_VERSION = "G4";
    static final String DBG_COLUMN_ID = "G5";

    static final int DBG_GENERAL_DEFAULT_VERSION = 1;

//Initialising Value
    static final int DB_GENERAL_DEFAULT_STEPSIZE = 36;
    static final long DB_GENERAL_DEFAULT_STEPGOAL = 5000;


    static final int DB_GENERAL_ID = 9;

    static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    static final int CAMERA_MEDIA_TYPE_IMAGE = 1;
    static final String CAMERA_IMAGE_DIRECTORY_NAME = "Step_Sensor";
    static final String CAMERA_IMAGE_CONSTANT_FILE_NAME = "Step_Senor_DP";
    static final int GALLERY_PICK_PHOTO_CODE = 1046;
    static final int CAMERA_IMAGE_WIDTH = 200;
    static final int GOOGLE_PLUS_SIGNIN_REQUESTCODE = 1909;

    static final int GOOGLE_PLUS_ALREADY_LOGIN = -56;
    static final int GOOGLE_PLUS_LOGIN = 66;
    static final int GOOGLE_PLUS_LOGOUT = 8896;


    static final int DB_GENERAL_SENSTIVITY_LOW = 1;
    static final int DB_GENERAL_SENSTIVITY_MEDIUM = 2;
    static final int DB_GENERAL_SENSTIVITY_HIGH = 3;

    static final int DB_GENERAL_SENSTIVITY_FASTEST = 0;
    static final int DB_GENERAL_SENSTIVITY_DEFAULT = 2;


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






