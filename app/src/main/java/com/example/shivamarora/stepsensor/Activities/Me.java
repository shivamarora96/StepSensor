/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;
import com.bumptech.glide.Glide;
import com.example.shivamarora.stepsensor.Others.Constant;
import com.example.shivamarora.stepsensor.R;
import com.pkmmte.view.CircularImageView;

public class Me extends AppCompatActivity {

    FloatingActionButton fab;
    CircularImageView mDP;
    EditText meName;
    EditText meEmail;
    ImageButton gplusButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);


        Toolbar toolbar = (Toolbar) findViewById(R.id.MeToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        meName = (EditText) findViewById(R.id.Me_name);
        meEmail = (EditText)findViewById(R.id.ME_EMAIL);


        gplusButton = (ImageButton) findViewById(R.id.ME_gplusButton);


        Intent fromOne = getIntent();
        String picUrl = fromOne.getStringExtra("DP");
        String name = fromOne.getStringExtra("name");
        final int currentLoginStatus = fromOne.getIntExtra("loginstatus", Constant.GOOGLE_PLUS_LOGOUT);


        meName.setText(name);
        getSupportActionBar().setTitle(name);

    gplusButton=(ImageButton) findViewById(R.id.ME_gplusButton);

    gplusButton.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View v) {
                                           Intent toGoogleSigniIn = new Intent(Me.this, GoogleSigniIn.class);

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
                                   }

    );


    fab =(FloatingActionButton) findViewById(R.id.MeFabCamera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    mDP=(CircularImageView) findViewById(R.id.ME_CAMERA_DP);
        meEmail.setText("step@sensor.com");

    if(currentLoginStatus==Constant.GOOGLE_PLUS_ALREADY_LOGIN||currentLoginStatus==Constant.GOOGLE_PLUS_LOGIN)

    {

        meEmail.setText(getIntent().getStringExtra("email"));
//        meEmail.set;

        if (picUrl.length() > 5) {
            Glide.with(getApplicationContext()).load(picUrl).into(mDP);
        }


    }



}






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            Me.this.finish();

        return true ;
    }






    }
