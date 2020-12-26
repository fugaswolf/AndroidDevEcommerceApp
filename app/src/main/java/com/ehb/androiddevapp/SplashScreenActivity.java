package com.ehb.androiddevapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    private static int LIMIT= 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //actionbar kleur wijzigen
        getWindow().setStatusBarColor(getResources().getColor(R.color.splashScreenActionBarColor));

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
}