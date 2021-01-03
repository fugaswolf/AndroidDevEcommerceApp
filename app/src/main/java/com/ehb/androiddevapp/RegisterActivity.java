package com.ehb.androiddevapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ehb.androiddevapp.database.DatabaseHelper;
import com.ehb.androiddevapp.domain.EmailValidator;
import com.ehb.androiddevapp.domain.User;
import com.ehb.androiddevapp.domain.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText myName;
    private EditText myEmail;
    private EditText myPassword;
    private Button myRegBtn;
    private FirebaseAuth myAuth;

    private Toolbar myToolbar;
    //local db
    private DatabaseHelper databaseHelper;

    private EmailValidator emailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myName = findViewById(R.id.reg_name);
        myEmail = findViewById(R.id.reg_email);
        myPassword = findViewById(R.id.reg_password);
        myRegBtn = findViewById(R.id.log_btn);
        myAuth = FirebaseAuth.getInstance();

        myToolbar = findViewById(R.id.reg_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //inti local db
        databaseHelper = new DatabaseHelper(this);

        emailValidator = new EmailValidator();

        myEmail.addTextChangedListener(emailValidator);





        //eens we op create account gedrukt hebben zal het volgende gebeuren:
        myRegBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //input text per veld wordt bij gehouden in een string variabele
                String name = myName.getText().toString();
                String email = myEmail.getText().toString();
                String password = myPassword.getText().toString();

                //als alle velden correct ingevuld zijn:
                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    //maak een account aan met de gegevens die ingevuld zijn
                    if(emailValidator.isValid()) {
                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                        //if internet available
                        if (Utils.isInternetConnected) {
                            myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //bij succes
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account succesfully created", Toast.LENGTH_SHORT).show();
                                        //sync user with db
                                        databaseHelper.insertUser(user);
                                        //redirect naar de home activity eens de registratie voltooid is

                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {

                            //save in local database and sync it later

                            ArrayList<User> userArrayList = new ArrayList<>();
                            userArrayList = databaseHelper.getAllUsers();
                            boolean isAlreadyRegistered = false;
                            for (int i = 0; i < userArrayList.size(); i++) {
                                if (userArrayList.get(i).getEmail().toLowerCase().equals(email.toLowerCase())) {
                                    isAlreadyRegistered = true;
                                    Toast.makeText(RegisterActivity.this, "This User Already Registered", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }

                            if (!isAlreadyRegistered) {
                                long temp = databaseHelper.insertUser(user);
                                if (temp > 0) {
                                    setSharedPreferences(true);
                                    Toast.makeText(RegisterActivity.this, "User Register Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    setUserToShared(user);
                                    startActivity(intent);
                                }
                            }
                        }
                    }else {
                        myEmail.setError("Email is not Valid");
                        Toast.makeText(RegisterActivity.this, "Email is not Valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //zo niet, de volgende toast message tonen
                    Toast.makeText(RegisterActivity.this, "Please fill empty field", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void loginScreen(View view) {
        Intent intent = new Intent (RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //save in share preferences
    private void setSharedPreferences(boolean isLogin){
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putBoolean("isLogin", isLogin);
        editor.apply();
    }

    private void setUserToShared(User user){
        SharedPreferences.Editor editor = getSharedPreferences("userdata", MODE_PRIVATE).edit();
        editor.putString("email", user.getEmail());
        editor.putString("pass",user.getPassword());
        editor.apply();
    }

}