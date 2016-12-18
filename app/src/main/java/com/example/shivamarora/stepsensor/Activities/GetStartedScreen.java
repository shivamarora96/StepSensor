
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


public class GetStartedScreen extends AppCompatActivity {

    Button getStarted ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_screen);


        ActiveAndroid.initialize(this);

//        if(new Select().from(DbData.class).execute().size() != 0 ){
            Intent i = new Intent(this , GoogleSigniIn.class);
            startActivity(i);
            this.finish();
//        }


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


