package com.example.naveed.aghas_rider_app.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.naveed.aghas_rider_app.Base.BaseActivity;
import com.example.naveed.aghas_rider_app.R;

import static com.example.naveed.aghas_rider_app.Helpers.Constants.SPLASH_TIME_OUT;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OpenSplashActivity();
    }

    private void OpenSplashActivity() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //   Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                // startActivity(i);
                //finish();

                Intent i;

                if(tokenHelper.GetToken()==null || tokenHelper.GetToken()=="") {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }
                else{
                    i = new Intent(SplashActivity.this, CurrentOrderActivity.class);

                }

                //i = new Intent(SplashScreen.this, LanuageSelection.class);


                startActivity(i);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
