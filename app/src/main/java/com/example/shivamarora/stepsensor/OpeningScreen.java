package com.example.shivamarora.stepsensor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class OpeningScreen extends AppCompatActivity {

    AlarmManager alarmManager ;
    ImageView i ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);


        int delay = 4 ;
        Intent [] intent = new Intent[1] ;
        intent[0] = new Intent(this , One.class) ;
PendingIntent pendingIntent  = PendingIntent.getActivities(OpeningScreen.this , 007 , intent , 0) ;
        overridePendingTransition(android.R.anim.overshoot_interpolator , android.R.anim.overshoot_interpolator);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE) ;
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + delay * 1000, pendingIntent );



    }
}
