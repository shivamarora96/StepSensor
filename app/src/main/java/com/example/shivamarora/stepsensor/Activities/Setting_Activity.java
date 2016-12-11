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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.shivamarora.stepsensor.Adapters_Customs.Settings_Adapter;
import com.example.shivamarora.stepsensor.Database_Models.DbGeneral;
import com.example.shivamarora.stepsensor.Others.Constant;
import com.example.shivamarora.stepsensor.R;

import java.util.ArrayList;
import java.util.List;

public class Setting_Activity extends AppCompatActivity {

    Toolbar settingsToolbar ;
    ListView listView ;
    int currentLoginstatus = Constant.GOOGLE_PLUS_LOGOUT ;
    Settings_Adapter adapter ;
    DbGeneral mDbGeneral ;
    int stepGoals ;
    int stepSizeIncm ;
    int senstivity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);

        currentLoginstatus = getIntent().getIntExtra("loginstatus" , Constant.GOOGLE_PLUS_LOGOUT);
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

        Log.i("mDbGeneral" , mDbGeneral.getDbID() + "") ;

        settingsToolbar = (Toolbar)findViewById(R.id.Setting_Toolbar) ;
        setSupportActionBar(settingsToolbar);
        getSupportActionBar().setTitle("SETTINGS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        ArrayList<String> settings = new ArrayList<>() ;
        settings.add( " Account ");
        settings.add(" Step Size ");
        settings.add( " Senstivity ");
        settings.add(" Step Goal");
        settings.add(" About Us ");
        settings.add( " Version ");


        adapter = new Settings_Adapter(Setting_Activity.this , settings);
        listView = (ListView)findViewById(R.id.Setting_ListView) ;
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(position == 0){
                    //Account Setting


                    Intent toGoogleSigniIn = new Intent(Setting_Activity.this, GoogleSigniIn.class);

                    if (currentLoginstatus == Constant.GOOGLE_PLUS_ALREADY_LOGIN || currentLoginstatus == Constant.GOOGLE_PLUS_LOGIN) {
                        toGoogleSigniIn.putExtra("request_LOGIN_LOGOUT", Constant.GOOGLE_PLUS_LOGOUT);
                        startActivity(toGoogleSigniIn);
                        finish();
                    } else if (currentLoginstatus == Constant.GOOGLE_PLUS_LOGOUT) {

                        toGoogleSigniIn.putExtra("request_LOGIN_LOGOUT", Constant.GOOGLE_PLUS_LOGIN);
                        startActivity(toGoogleSigniIn);
                        finish();

                    }



                }
                else if(position == 1){
                    //StepSize ....



                    final View numberPicker_View = LayoutInflater.from(Setting_Activity.this).inflate(R.layout.number_picker_custom_layout , null);
                    final NumberPicker stepSizePicker  = (NumberPicker)numberPicker_View.findViewById(R.id.NumberPicker_CustomLayout) ;
                    stepSizePicker.setMaxValue(50);
                    stepSizePicker.setMinValue(35);
                    stepSizePicker.setValue(mDbGeneral.getDbStepSize());


                    //It Dowsnot change the Actual Value ... this only change the displayed value ............
                    String [] displayedValue = new String[16] ;
                    for(int i = 35 ; i <=50 ; i++ ){
                        displayedValue[i-35] = i + " cm" ;
                    }

                    stepSizePicker.setDisplayedValues(displayedValue);


                    AlertDialog alertDialog = new AlertDialog.Builder(Setting_Activity.this ).create() ;
                    alertDialog.setIcon(R.drawable.ic_launcher);
                    alertDialog.setTitle("STEP SENSOR");
                    alertDialog.setView(numberPicker_View);
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, " CANCEL ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, " SET ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stepSizeIncm = stepSizePicker.getValue() ;
                            mDbGeneral.setDbStepSize(stepSizeIncm);
                            mDbGeneral.save();
                            Toast.makeText(Setting_Activity.this , "Step Size set To : " + stepSizeIncm + " cm" , Toast.LENGTH_LONG).show();
                        }
                    });
                    alertDialog.show();













                }
                else if (position == 2){
                    //Senstivity

                    final View numberPicker_View = LayoutInflater.from(Setting_Activity.this).inflate(R.layout.number_picker_custom_layout , null);
                    final NumberPicker senstivityKeeper  = (NumberPicker)numberPicker_View.findViewById(R.id.NumberPicker_CustomLayout) ;
                    senstivityKeeper.setMaxValue(3);
                    senstivityKeeper.setMinValue(0);
                    senstivityKeeper.setValue(mDbGeneral.getDbSenstivity());


                    //It Dowsnot change the Actual Value ... this only change the displayed value ............
                    String [] displayedValue = new String[4] ;
                    for(int i = 0 ; i <=4 ; i++ ){

                        if( i == 0){
                            displayedValue[i] = " VERY LOW " ;
                        }
                        else if (i == 1) {
                            displayedValue[i] = " LOW " ;

                        }
                        else if (i == 2) {

                            displayedValue[i] = " MEDIUM (Best Result) " ;
                        }
                        else if (i == 3) {

                            displayedValue[i] = " HIGH " ;
                        }


                    }

                    senstivityKeeper.setDisplayedValues(displayedValue);
                    


                    AlertDialog alertDialog = new AlertDialog.Builder(Setting_Activity.this ).create() ;
                    alertDialog.setIcon(R.drawable.ic_launcher);
                    alertDialog.setTitle("STEP SENSOR");
                    alertDialog.setView(numberPicker_View);

                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, " CANCEL ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, " SET ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            senstivity = senstivityKeeper.getValue() ;
                            mDbGeneral.setDbSenstivity(senstivity);
                            mDbGeneral.save();
                            Toast.makeText(Setting_Activity.this , "SENSTIVITY : " + senstivityKeeper.getDisplayedValues()[senstivity] , Toast.LENGTH_LONG).show();
                        }
                    });
                    alertDialog.show();



                }
                else if(position == 3){
                    //Step Goal ....


                    final View numberPicker_View = LayoutInflater.from(Setting_Activity.this).inflate(R.layout.number_picker_custom_layout , null);
                    final NumberPicker stepGoalPicker  = (NumberPicker)numberPicker_View.findViewById(R.id.NumberPicker_CustomLayout) ;
                    stepGoalPicker.setMaxValue(20);
                    stepGoalPicker.setMinValue(5);
                    int currentgoal = (int) mDbGeneral.getDbStepGoal() / 1000;
                    Log.i("currentgoal" ,currentgoal + "");
                    stepGoalPicker.setValue(currentgoal);


                    //It Dowsnot change the Actual Value ... this only change the displayed value ............
                    String [] displayedValue = new String[16] ;
                    for(int i = 5 ; i <=20 ; i++ ){
                        displayedValue[i-5] = i*1000 + "  steps/day" ;
                    }

                    stepGoalPicker.setDisplayedValues(displayedValue);




                    AlertDialog alertDialog = new AlertDialog.Builder(Setting_Activity.this ).create() ;
                    alertDialog.setIcon(R.drawable.ic_launcher);
                    alertDialog.setTitle("STEP SENSOR");
                    alertDialog.setView(numberPicker_View);

                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, " CANCEL ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, " SET ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stepGoals = stepGoalPicker.getValue()  * 1000;
                            mDbGeneral.setDbStepGoal(stepGoals);
                            mDbGeneral.save();
                            Toast.makeText(Setting_Activity.this , "Step Goal : " + stepGoals , Toast.LENGTH_LONG).show();
                        }
                    });
                    alertDialog.show();



                }
                else if (position == 4){
                    //About Us


                    AlertDialog alertDialog = new AlertDialog.Builder(Setting_Activity.this ).create() ;
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
                else if(position == 5){
                    //Version

                    AlertDialog alertDialog = new AlertDialog.Builder(Setting_Activity.this ).create() ;
                    alertDialog.setIcon(R.drawable.ic_launcher);
                    alertDialog.setTitle(" STEP SENSOR");
                    alertDialog.setMessage("\n\n  VERSION :: " + mDbGeneral.getDbVersion());
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }

                Snackbar.make(parent , listView.getAdapter().getItem(position) + "" , Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStop() {
        mDbGeneral.save();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            Setting_Activity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}


