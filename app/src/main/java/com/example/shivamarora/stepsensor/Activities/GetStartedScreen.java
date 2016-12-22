
package com.example.shivamarora.stepsensor.Activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.shivamarora.stepsensor.Database_Models.DbData;
import com.example.shivamarora.stepsensor.R;
import com.example.shivamarora.stepsensor.SignIn;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class GetStartedScreen extends AppCompatActivity {

    Button getStarted ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_screen);

        ActiveAndroid.initialize(this);

        if(new Select().from(DbData.class).execute().size() != 0 ){
            Intent i = new Intent(this , SignIn.class);
            startActivity(i);
            this.finish();
        }

        Get_Hash_For_FB_28_Char();



        getStarted = (Button)findViewById(R.id.OpenScreen_getstartedButton);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GetStartedScreen.this, SignIn.class);
                startActivity(i);
                GetStartedScreen.this.finish();
            }
        });


    }

    private void Get_Hash_For_FB_28_Char() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.shivamarora.stepsensor",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


}


