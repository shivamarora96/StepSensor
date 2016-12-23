
package com.example.shivamarora.stepsensor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivamarora.stepsensor.Activities.GoogleSigniIn;
import com.example.shivamarora.stepsensor.Activities.Main;
import com.example.shivamarora.stepsensor.Others.Constant;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignIn extends AppCompatActivity implements FirebaseAuth.AuthStateListener {


    SignInButton Login_Button ;
    AdView mAdView;
    InterstitialAd mInterstitalAdView ;
    TextView skipNow ;
    GoogleSignInOptions gso ;
    GoogleApiClient googleApiClient ;
    FirebaseAuth mAuth  ;
    Button GooglePlus_Button;
    LoginButton facebookSignIn ;
    CallbackManager callbackManager_facebook;
     SweetAlertDialog mDialog_WhileLogin ;
    final  String TAG = "LOG_TAG" ;
    int requestCode ;
    static  int mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGOUT ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_google_signi_in);
        super.onCreate(savedInstanceState);
        Log.i(TAG , "IN ON_CREATE");

        requestCode =  getIntent().getIntExtra("request_LOGIN_LOGOUT" , Constant.GOOGLE_PLUS_LOGIN) ;
        Show_ADs();

        callbackManager_facebook = CallbackManager.Factory.create();
        Login_Button = (SignInButton)findViewById(R.id.GoogleSignIn_Button) ;
        mAuth = FirebaseAuth.getInstance() ;

        set_GoogleApiClient_And_GSO();
        mAuth.addAuthStateListener(this);
//        mDialog_WhileLogin = new SweetAlertDialog(SignIn.this , SweetAlertDialog.PROGRESS_TYPE) ;

        skipNow = (TextView)findViewById(R.id.GoogleSignIn_SkipForNow);
        skipNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackGroundTask backGroundTask = new BackGroundTask(SignIn.this) ;
                backGroundTask.execute();
            }
        });

    }


    //ShowAds.......................................
    private void Show_ADs() {
        //TestID
//        5B39BC16E8DC3387A579AEC32C6BD20D
        mAdView = (AdView)findViewById(R.id.GoogleSignIn_BannerAdd) ;
        mAdView.loadAd(new AdRequest.Builder()
//                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                        .addTestDevice("5B39BC16E8DC3387A579AEC32C6BD20D")
                        .build()
        );


        mInterstitalAdView = new InterstitialAd(SignIn.this);
        mInterstitalAdView.setAdUnitId(getString(R.string.InterstetialAdunitId));
        mInterstitalAdView.loadAd(new AdRequest.Builder()
//                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                        .addTestDevice("5B39BC16E8DC3387A579AEC32C6BD20D")
                        .build()
        );
        mInterstitalAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if(mInterstitalAdView.isLoaded())
                    mInterstitalAdView.show();
            }
        });
    }



    //Setting up gso and google_api_client ...............................................................................................

    private void set_GoogleApiClient_And_GSO() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(SignIn.this)
                            .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                                @Override
                                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                                    //TODO

                                }
                            })
                            .addApi(Auth.GOOGLE_SIGN_IN_API , gso)
                            .build();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG , "IN ON_START");

        final Dialog d = new Dialog(SignIn.this);
        d.setContentView(R.layout.custom_login_dialogue_chooser);
        d.setTitle("Connect With Us :: ");
        facebookSignIn = (LoginButton) d.findViewById(R.id.Facebook_login_button);
        GooglePlus_Button = (Button) d.findViewById(R.id.Google_Login_Button);
        GooglePlus_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Log.i("abcd" , "clicked yo yo !!!");
                Call_GooglePlus_SignIn();
            }
        });

        Login_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    d.show();
                    Call_Facebook_Registerion_SignIn();
                }
            });


    }

    //Calling and Registering CallBack for Facebook

    void Call_Facebook_Registerion_SignIn(){
        Log.i(TAG , "IN  Call_Facebook_Registerion_SignIn");

        if(facebookSignIn!=null){

            facebookSignIn.setReadPermissions("email", "public_profile");
            facebookSignIn.registerCallback(callbackManager_facebook, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    mDialog_WhileLogin = new SweetAlertDialog(SignIn.this , SweetAlertDialog.PROGRESS_TYPE) ;
                    mDialog_WhileLogin.setTitleText("Loading ");
                    mDialog_WhileLogin.getProgressHelper().setBarColor(Color.parseColor("#FF1414"));
                    mDialog_WhileLogin.setCancelable(false);
                    mDialog_WhileLogin.show();

                    handle_Facebook_SignIn(loginResult);
                    Log.i(TAG , "SUCCESS");
                }

                @Override
                public void onCancel() {
                    Snackbar.make(findViewById(R.id.SignIn_coordinateView) , "Cancelled !! " , Snackbar.LENGTH_SHORT).show();
                    Log.i(TAG , " CANCELLED ");
                }

                @Override
                public void onError(FacebookException error) {
                    Snackbar.make(findViewById(R.id.SignIn_coordinateView) ,"Error:" + error.toString() , Snackbar.LENGTH_SHORT).show();
                    Log.i(TAG , "Error");

                }
            });



        }



    }


    //Calling GOogle Plus SignIn...................................................................

    void Call_GooglePlus_SignIn(){

//        dilogwhileLogin = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        dilogwhileLogin.getProgressHelper().setBarColor(Color.parseColor("#FF1414"));
//        dilogwhileLogin.setTitleText("Loading");
//        dilogwhileLogin.setCancelable(false);
//        dilogwhileLogin.show();

        mDialog_WhileLogin = new SweetAlertDialog(SignIn.this , SweetAlertDialog.PROGRESS_TYPE) ;
        mDialog_WhileLogin.setTitleText("Loading ");
        mDialog_WhileLogin.getProgressHelper().setBarColor(Color.parseColor("#FF1414"));
        mDialog_WhileLogin.setCancelable(false);
        mDialog_WhileLogin.show();

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent , 25);
        Log.i(TAG , "IN Call_GooglePlus_SignIn()") ;
    }
    //Handle Facebook Sign IN ....................................................

    private void handle_Facebook_SignIn(LoginResult loginResult) {
        Log.i(TAG , "IN handle_Facebook_SignIn ") ;

        AccessToken fbAccessToken = loginResult.getAccessToken() ;
        AuthCredential authCredential = FacebookAuthProvider.getCredential(fbAccessToken.getToken()) ;
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i(TAG , "IN handle_Facebook_SignIn + ON_COMPLETE ") ;

                        if(!task.isSuccessful())
                            Toast.makeText(SignIn.this , "Login Failed ... Try again !! " , Toast.LENGTH_SHORT ).show();
                    }
                }) ;
    }




    //Handel GooglePlus SignIN ........................................................................
    private void handle_GooglePlus_SignIn(GoogleSignInResult result) {
        Log.i(TAG , "IN handle_GooglePlus_SignIn") ;
        GoogleSignInAccount account = result.getSignInAccount();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken() , null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignIn.this ,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i(TAG , "IN handle_GooglePlus_SignIn + OnComplete ") ;

                        if(!task.isSuccessful()){
                            Toast.makeText(SignIn.this ,"Login Failed ... , Try Again !"  , Toast.LENGTH_SHORT ).show();
                        }

                    }
                }) ;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG , "On_Activity") ;

        super.onActivityResult(requestCode, resultCode, data);

        //Facebook .........
        callbackManager_facebook.onActivityResult(requestCode , resultCode,data);


        //GooglePlus ........
        if(requestCode == 25){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.i("abc " , result + "") ;
            if(result.isSuccess()){
                handle_GooglePlus_SignIn(result);

            }
        }


    }





    //GET FACEBOOK HASH ........................................................................

    private void Get_Hash_For_FB_28_Char() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.shivamarora.gplusfb",
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


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        Log.i(TAG , "AuthListner") ;
        final FirebaseUser user = firebaseAuth.getCurrentUser() ;
        if(user!=null) {

            Log.i(TAG , "AuthListner +  Not Null User") ;
            String currentName = user.getDisplayName();
            Toast.makeText(SignIn.this, "User - > " + currentName, Toast.LENGTH_LONG).show();
//            mCurrentLoginStatus = Constant.GOOGLE_PLUS_ALREADY_LOGIN ;
            if(mDialog_WhileLogin!=null && mDialog_WhileLogin.isShowing()){

                mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGIN;
                mDialog_WhileLogin.dismiss();

                SweetAlertDialog  sweetAlertDialog_Login = new SweetAlertDialog(SignIn.this, SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog_Login.setTitleText("SUCCESSFULL");
                sweetAlertDialog_Login.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        Intent to_Main = new Intent(SignIn.this, Main.class);
                        to_Main.putExtra("name", user.getDisplayName());
                        to_Main.putExtra("loginstatus", mCurrentLoginStatus);
                        to_Main.putExtra("photo", user.getPhotoUrl() + "");
                        to_Main.putExtra("email", user.getEmail() + " ");
                        startActivity(to_Main);
                        finish();
                    }
                });
                sweetAlertDialog_Login.show();


            }

            else if(mDialog_WhileLogin == null ) {

                mCurrentLoginStatus = Constant.GOOGLE_PLUS_ALREADY_LOGIN;

                mDialog_WhileLogin = new SweetAlertDialog(SignIn.this , SweetAlertDialog.PROGRESS_TYPE);
                mDialog_WhileLogin.setTitleText("Loading");
                mDialog_WhileLogin.setCancelable(false);
                mDialog_WhileLogin.getProgressHelper().setBarColor(Color.parseColor("#ff1414"));
                mDialog_WhileLogin.show();

                Intent to_Main = new Intent(SignIn.this, Main.class);
                to_Main.putExtra("name", currentName);
                to_Main.putExtra("loginstatus", mCurrentLoginStatus);
                to_Main.putExtra("photo", user.getPhotoUrl() + "");
                to_Main.putExtra("email", user.getEmail());
                startActivity(to_Main);
                mDialog_WhileLogin.dismiss();
                finish();

            }
            Log.i(TAG,"PERSON - > " + currentName + " : " + user.getPhotoUrl()+" : " +  user.getEmail() ) ;


        }
        else if(user == null){
            Log.i(TAG , "AuthListner +  Null User") ;
            mCurrentLoginStatus = Constant.GOOGLE_PLUS_LOGOUT ;

            //TODO ADD INTENT TO SAME ACTIVITY OR LOGOUT.................

        }
    }
}


/*

 Intent toOne = new Intent(GoogleSigniIn.this, Main.class);
                toOne.putExtra("name", mPersonName);
                toOne.putExtra("loginstatus", mCurrentLoginStatus);
                toOne.putExtra("photo", mPersonPhoto);
                toOne.putExtra("email", mPersonEmail);
                startActivity(toOne);



*/

class BackGroundTask extends AsyncTask<Void , Void , Void>{

    SweetAlertDialog sweetAlertDialog ;
    SignIn signIn ;

    public BackGroundTask(SignIn signIn) {
        this.signIn = signIn;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        sweetAlertDialog= new SweetAlertDialog(signIn , SweetAlertDialog.PROGRESS_TYPE) ;
        sweetAlertDialog.setTitleText("Just a second !") ;
        sweetAlertDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent i = new Intent(signIn , Main.class) ;
        i.putExtra("loginstatus" , Constant.GOOGLE_PLUS_LOGOUT);
        signIn.startActivity(i);
        sweetAlertDialog.dismiss();
        signIn.finish();
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }
}