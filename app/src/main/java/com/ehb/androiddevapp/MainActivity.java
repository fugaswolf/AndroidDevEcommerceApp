package com.ehb.androiddevapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginScreen(View view) {
        Intent intent = new Intent (MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void registerScreen(View view) {
        Intent intent = new Intent (MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}