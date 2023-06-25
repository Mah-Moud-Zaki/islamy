package com.bfcai.islamy.Screens.WelcomeScreen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIMER = 4000;

    ImageView icon;
    TextView appName,gate;

    Animation side,bottom;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        icon=findViewById(R.id.imageView4);
        appName =findViewById(R.id.textView11);
        gate =findViewById(R.id.textView12);

        side = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //set animation on elements
        icon.setAnimation(side);
        appName.setAnimation(bottom);
        gate.setAnimation(bottom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedPreferences =getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

                if(isFirstTime){
                    setLocale("ar");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }


            }
        },SPLASH_TIMER);

    }
    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Setting",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
}