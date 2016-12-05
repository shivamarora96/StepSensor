
package com.example.shivamarora.stepsensor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GoogleSigniIn extends AppCompatActivity {


    private AdView mAdView;
    private InterstitialAd mInterstitalAdView ;
    TextView skipNow ;
    ProgressDialog mprogressDialog ;
    SweetAlertDialog dilogwhileLogin ;
    SweetAlertDialog mSweetDialog ;
    SweetAlertDialog sweetAlertDialoglogout ;

    GoogleSignInOptions gso  ;
    GoogleApiClient mgoogleApiClient;
    SignInButton signInButton ;

    int requestCode ;
    String mPersonName = null ;
    String mPersonPhoto  = null;
    String mPersonEmail = null ;
    static  int mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGOUT ;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_signi_in);


//TestID
//        5B39BC16E8DC3387A579AEC32C6BD20D
        mAdView = (AdView)findViewById(R.id.GoogleSignIn_BannerAdd) ;
        mAdView.loadAd(new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice("5B39BC16E8DC3387A579AEC32C6BD20D")
                        .build()

        );



    mInterstitalAdView = new InterstitialAd(GoogleSigniIn.this);
        mInterstitalAdView.setAdUnitId(getString(R.string.InterstetialAdunitId));
        mInterstitalAdView.loadAd(new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice("5B39BC16E8DC33 87A579AEC32C6BD20D")
                        .build()
        );
        mInterstitalAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(mInterstitalAdView.isLoaded())
                    mInterstitalAdView.show();
            }
        });


        requestCode =  getIntent().getIntExtra("request_LOGIN_LOGOUT" , Constant.GOOGLE_PLUS_LOGIN) ;


       // Configure sign-in to request the user's ID, email address, and basic
       // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
       gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestEmail()
               .requestProfile()
               .build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
       // options specified by gso.
       mgoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
               .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                   @Override
                   public void onConnectionFailed(ConnectionResult connectionResult) {

                       Toast.makeText(GoogleSigniIn.this , "Connection Failed , Try after sometimes :(" , Toast.LENGTH_LONG).show();
                   }
               })
               .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
               .build();


//Customize SignInButton .........................



       signInButton = (SignInButton)findViewById(R.id.GoogleSignIn_Button) ;
       signInButton.setScopes(gso.getScopeArray());

        if(requestCode == Constant.GOOGLE_PLUS_LOGIN) {
            setGooglePlusButtonText(signInButton , "LOG IN");
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }


        else if(requestCode == Constant.GOOGLE_PLUS_LOGOUT) {
            setGooglePlusButtonText(signInButton , "LOG OUT");

            signInButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    setGooglePlusButtonText(signInButton, "LOGIN AGAIN");
                    SignOut();


                }
            });
        }


//SKIP  NOW Dialogue Loading ......................

       mprogressDialog = new ProgressDialog(this);

       skipNow = (TextView)findViewById(R.id.GoogleSignIn_SkipForNow);
        skipNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoinBackgroundGoogleSignIn doinBackgroundGoogleSignIn =  new DoinBackgroundGoogleSignIn(GoogleSigniIn.this , mprogressDialog);
                doinBackgroundGoogleSignIn.execute() ;
                mprogressDialog.show() ;
            }
        });



    }




    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }



    //Starting the intent prompts the user to select a Google account to sign in with.
    private void signIn() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mgoogleApiClient);
        startActivityForResult(signInIntent, Constant.GOOGLE_PLUS_SIGNIN_REQUESTCODE);
        GoogleSigniIn.mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGIN;

        dilogwhileLogin = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dilogwhileLogin.getProgressHelper().setBarColor(Color.parseColor("#FF1414"));
        dilogwhileLogin.setTitleText("Loading");
        dilogwhileLogin.setCancelable(false);
        dilogwhileLogin.show();

    }

     void SignOut() {
        GoogleSigniIn.mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGOUT;
        Auth.GoogleSignInApi.signOut(mgoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {

                            sweetAlertDialoglogout = new SweetAlertDialog(GoogleSigniIn.this, SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialoglogout.setTitleText("SUCCESSFULL");
                            sweetAlertDialoglogout.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    requestCode = Constant.GOOGLE_PLUS_LOGIN;
                                    Intent toGoogleSignIn = new Intent(GoogleSigniIn.this, GoogleSigniIn.class);
                                    toGoogleSignIn.putExtra("request_LOGIN_LOGOUT", requestCode);
                                    startActivity(toGoogleSignIn);
                                    GoogleSigniIn.this.finish();
                                }
                            });
                            sweetAlertDialoglogout.show();


                        } else {
                            Toast.makeText(getApplicationContext() , "NETWORK ERROR :(" , Toast.LENGTH_SHORT).show();
                            Intent toGoogleSignIn = new Intent(GoogleSigniIn.this, GoogleSigniIn.class);
                            requestCode = Constant.GOOGLE_PLUS_LOGOUT ;
                            toGoogleSignIn.putExtra("request_LOGIN_LOGOUT", requestCode);
                            startActivity(toGoogleSignIn);
                            GoogleSigniIn.this.finish();

                        }

                    }
                });
    }


/*

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mgoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constant.GOOGLE_PLUS_SIGNIN_REQUESTCODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
//        Log.i("Google", "handleSignInResult:" + result.isSuccess());

        if(dilogwhileLogin!=null) {
            if (dilogwhileLogin.isShowing())
                dilogwhileLogin.dismissWithAnimation();
        }




        if (result.isSuccess()) {
            //After you retrieve the sign-in result, you can check if sign-in succeeded with the isSuccess method.
            // If sign-in succeeded, you can call the getSignInAccount method to get a GoogleSignInAccount object that contains information about the signed-in user,
            // such as the user's name.
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();

            mPersonName = acct.getDisplayName();
            mPersonPhoto = acct.getPhotoUrl() + "";
            mPersonEmail = acct.getEmail();
            acct.getGrantedScopes();

            Log.i("picurl" , mPersonPhoto) ;

                mSweetDialog = new SweetAlertDialog(GoogleSigniIn.this, SweetAlertDialog.SUCCESS_TYPE);
                mSweetDialog.setContentText("\n\nSinged In Successfully as \n\n" + mPersonName + "\n\n");
                mSweetDialog.setTitleText("SUCCESSFULL");
                mSweetDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent toOne = new Intent(GoogleSigniIn.this, One.class);
                        toOne.putExtra("name", mPersonName);
                        toOne.putExtra("loginstatus" , mCurrentLoginStatus);
                        toOne.putExtra("photo", mPersonPhoto);
                        toOne.putExtra("email", mPersonEmail);
                        startActivity(toOne);
                        GoogleSigniIn.this.finish();

                    }
                });
            if(mCurrentLoginStatus == Constant.GOOGLE_PLUS_LOGIN) {
                mSweetDialog.show();
            }
                updateUI(true , result.getStatus());



        } else {
            // Signed out, show unauthenticated UI.
//            Toast.makeText(GoogleSigniIn.this, ":",Toast.LENGTH_SHORT ).show();
            mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGOUT ;
            updateUI(false , result.getStatus());
        }
    }

    private void updateUI(boolean isSignedIn , Status status) {
        if (isSignedIn) {
            Toast.makeText(this , "Login Successfull :)  --> " + mPersonName , Toast.LENGTH_LONG).show();
        } else {

            if(status.getStatusCode()  == 7)
            Toast.makeText(this , "Please check your Network Connection" , Toast.LENGTH_LONG).show();        }
    }



    @Override
    public void onStart() {
        super.onStart();

        if (getIntent().getIntExtra("request_LOGIN_LOGOUT", -1) == -1) {

            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mgoogleApiClient);
            if (opr.isDone()) {

                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.

//            Log.i("Google", "Got cached sign-in");
                mCurrentLoginStatus = Constant.GOOGLE_PLUS_ALREADY_LOGIN;
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);

                Intent toOne = new Intent(GoogleSigniIn.this, One.class);
                toOne.putExtra("name", mPersonName);
                toOne.putExtra("loginstatus", mCurrentLoginStatus);
                toOne.putExtra("photo", mPersonPhoto);
                toOne.putExtra("email", mPersonEmail);
                startActivity(toOne);

                Log.i("dp" , mPersonPhoto);

                GoogleSigniIn.this.finish();
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
//            showProgressDialog();

                mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGOUT;
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }
    }




}


class DoinBackgroundGoogleSignIn extends AsyncTask<Void , Void ,Void>{

    GoogleSigniIn googleSigniIn ;
    ProgressDialog progressDialog ;

    public DoinBackgroundGoogleSignIn(GoogleSigniIn googleSigniIn , ProgressDialog progressDialog ) {
        this.googleSigniIn = googleSigniIn;
        this.progressDialog = progressDialog ;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if(progressDialog == null)
        progressDialog = new ProgressDialog(googleSigniIn , ProgressDialog.STYLE_SPINNER ) ;

        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("LOADING ");
        progressDialog.setMessage("Please wait a bit ... ");

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {

        Intent i = new Intent(googleSigniIn , One.class) ;
        i.putExtra("loginstatus" , Constant.GOOGLE_PLUS_LOGOUT);
        googleSigniIn.startActivity(i);
        googleSigniIn.finish();


        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.hide();


        super.onPostExecute(aVoid);
    }





}