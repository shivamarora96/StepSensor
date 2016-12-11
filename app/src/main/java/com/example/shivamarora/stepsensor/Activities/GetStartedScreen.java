/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.shivamarora.stepsensor.Database_Models.DbData;
import com.example.shivamarora.stepsensor.R;
//
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.Profile;
//import com.facebook.ProfileTracker;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton ;
//
//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;


public class GetStartedScreen extends AppCompatActivity {

    Button getStarted ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_screen);



        ActiveAndroid.initialize(this);

        if(new Select().from(DbData.class).execute().size() != 0 ){
            Intent i = new Intent(this , GoogleSigniIn.class);
            startActivity(i);
            this.finish();
        }


getStarted = (Button)findViewById(R.id.OpenScreen_getstartedButton);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GetStartedScreen.this, GoogleSigniIn.class);
                startActivity(i);
                GetStartedScreen.this.finish();
            }
        });







    }

}





















/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/*

    AlarmManager alarmManager ;
    ImageView i ;
    LoginButton fb_login_button ;
    CallbackManager fb_callbackManager ;
    ProfileTracker profileTracker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialising Facebook sdk..............................................
        //Make sure to always Initalize before setContentView()
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_get_started_screen);

        fb_callbackManager = CallbackManager.Factory.create();

        fb_login_button = (LoginButton) findViewById(R.id.fb_login_button);


        fb_login_button.registerCallback(fb_callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Successfull ", Toast.LENGTH_LONG).show();
                DelayInStarting(4);
                Log.i("facebook", "Successfull");
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel ", Toast.LENGTH_LONG).show();
                Log.i("facebook", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Failed :(", Toast.LENGTH_LONG).show();
                Log.i("facebook", "Error");
            }

        });

       */
/* ArrayList<String> mPermissions = new ArrayList<>();
        mPermissions.add("publish_actions") ;

        fb_login_button.setPublishPermissions(mPermissions);
*//*

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fb_callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    void DelayInStarting(int delay){

        Intent [] intent = new Intent[1] ;
        intent[0] = new Intent(this , Main.class) ;
        PendingIntent pendingIntent  = PendingIntent.getActivities(GetStartedScreen.this , 0 , intent , 0) ;
        overridePendingTransition(android.R.anim.overshoot_interpolator , android.R.anim.overshoot_interpolator);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE) ;
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + delay * 1000, pendingIntent);

    }

*/
