
/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.shivamarora.stepsensor.R;

public class OpeningScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

       DoinBackground d =  new DoinBackground(this) ;
         d.execute();


    }
}

class DoinBackground extends AsyncTask<Void , Void , Void>{

    OpeningScreen openingScreen ;

    public DoinBackground(OpeningScreen openingScreen) {
        this.openingScreen = openingScreen;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("thread", "error") ;
        }
return null ;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        Intent i = new Intent(openingScreen , GetStartedScreen.class) ;
        openingScreen.startActivity(i);
        openingScreen.finish();

        super.onPostExecute(aVoid);
    }
}
