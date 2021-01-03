package com.ehb.androiddevapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAuth = FirebaseAuth.getInstance();
    }

    public void loginScreen(View view) {
        Intent intent = new Intent (MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void registerScreen(View view) {
        Intent intent = new Intent (MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(myAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }else {
            if(getSharedPreferences()){
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        }
    }

    private boolean getSharedPreferences(){
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        return prefs.getBoolean("isLogin", false);
    }

}