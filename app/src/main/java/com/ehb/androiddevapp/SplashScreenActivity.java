package com.ehb.androiddevapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    private static int LIMIT= 2000;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //actionbar kleur wijzigen
        getWindow().setStatusBarColor(getResources().getColor(R.color.splashScreenActionBarColor));
        //set app laaguge that user selected ast time
        setLocate(getLanConfig(SplashScreenActivity.this),getBaseContext());
        //een splash screen tonen (2 sec lang) en daarna door verwijzen naar de main activity
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, LIMIT);


    }

    public static String getLanConfig(Context context){
        SharedPreferences sp=context.getSharedPreferences("lang_setting",Context.MODE_PRIVATE);
        return  sp.getString("lang_setting","en");

    }

    private  void setLocate(String s, Context context) {
        Locale locale = new Locale(s); // where 'hi' is Language code, set this as per your Spinner Item selected
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
        setLanConfig(s,SplashScreenActivity.this);

    }

    public  void  setLanConfig(String key, Activity activity) {
        SharedPreferences sp=activity.getSharedPreferences("lang_setting",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putString("lang_setting",key);
        ed.apply();
    }
}