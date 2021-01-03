package com.ehb.androiddevapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.ehb.androiddevapp.adapter.CustomAdapter;

import java.util.Locale;

public class ChangeLanguage extends AppCompatActivity {
    //array of language names
    String[] countryNames={"English","Nederlands"};
    //images of flags coming drawables
    int flags[] = {R.drawable.ic_us, R.drawable.ic_netherlands};
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        //spinner and button init
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        Button change = (Button) findViewById(R.id.btn_change_language);

        //setting toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.change_language));

        //custom adapter of spinner
        CustomAdapter customAdapter=new CustomAdapter(this,flags,countryNames);
        //assigning the adapter
        spin.setAdapter(customAdapter);

        //listener if user select any value from spinner
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //saving the language selected
                language = countryNames[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //click listener of submit change of language
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if English language is selected
                if(language.equals("English")){
                    //for english
                    setLocate("en",ChangeLanguage.this);
                    //re run the activity to apply the changes
                    Toast.makeText(ChangeLanguage.this, getResources().getString(R.string.change_language), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeLanguage.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0, 0);
                    finish();

                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }
                //if Nederlands is selected in spinner
                else if(language.equals("Nederlands")){
                    //for german
                    setLocate("nl", ChangeLanguage.this);

                    Toast.makeText(ChangeLanguage.this, getResources().getString(R.string.change_language), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeLanguage.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0, 0);
                    finish();

                    overridePendingTransition(0, 0);
                    startActivity(intent);


                }
            }
        });
    }
    //function that actually change the language of the app
    // it input the name of strings.xml file and apply on whole app
    private static void setLocate(String s, Activity context) {
        Locale locale = new Locale(s); //
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

        setLanConfig(s,context);
    }
    //save the selected language in shared preferences
    public static void  setLanConfig(String key, Activity activity) {
        SharedPreferences sp = activity.getSharedPreferences("lang_setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putString("lang_setting",key);
        ed.apply();
    }
}