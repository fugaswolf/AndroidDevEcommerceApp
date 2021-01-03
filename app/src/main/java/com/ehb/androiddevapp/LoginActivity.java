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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText myEmail;
    private EditText myPassword;
    private Button myLoginBtn;
    private FirebaseAuth myAuth;

    private Toolbar myToolbar;
    //local db
    private DatabaseHelper databaseHelper;
    // The validator for the email input field.
    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myEmail = findViewById(R.id.log_email);
        myPassword = findViewById(R.id.log_pass);
        myLoginBtn = findViewById(R.id.log_btn);
        myAuth = FirebaseAuth.getInstance();
        myToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup field validators.
        mEmailValidator = new EmailValidator();
        myEmail.addTextChangedListener(mEmailValidator);
        // database helper
        databaseHelper = new DatabaseHelper(this);


        myLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = myEmail.getText().toString();
                String password = myPassword.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()) {
                    //if internet available  than authenticate from firebase
                    if (mEmailValidator.isValid()) {
                        if (Utils.isInternetConnected) {

                            myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        ArrayList<User> userArrayList = new ArrayList<>();
                                        userArrayList = databaseHelper.getAllUsers();
                                        boolean isAlreadyRegistered = false;
                                        for (int i = 0; i < userArrayList.size(); i++) {
                                            if (userArrayList.get(i).getEmail().toLowerCase().equals(email.toLowerCase())) {
                                                isAlreadyRegistered = true;
                                                break;
                                            }
                                        }
                                        User user = new User();
                                        user.setEmail(email);
                                        user.setName(email);
                                        user.setPassword(password);
                                        if (!isAlreadyRegistered) {
                                            long temp = databaseHelper.insertUser(user);

                                        }
                                        Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Login attempt failed: " + task.getException(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            //getting data from local database
                            //getting all users from db
                            ArrayList<User> userArrayList = new ArrayList<>();
                            userArrayList = databaseHelper.getAllUsers();
                            boolean isLoginSuccess = false;
                            for (int i = 0; i < userArrayList.size(); i++) {
                                if (userArrayList.get(i).getEmail().toLowerCase().equals(email.toLowerCase())) {
                                    if (userArrayList.get(i).getPassword().toLowerCase().equals(password.toLowerCase())) {
                                        isLoginSuccess = true;
                                    }

                                    break;
                                }
                            }

                            if (isLoginSuccess) {
                                User user = new User();
                                user.setEmail(email);
                                user.setPassword(password);
                                user.setName("");
                                setSharedPreferences(true);
                                Toast.makeText(LoginActivity.this, "User Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);


                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        myEmail.setError("Wrong Email Format");
                        Toast.makeText(LoginActivity.this, "Wrong Email Format", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Please fill the necessary fields!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void registerScreen(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    //save in share preferences
    private void setSharedPreferences(boolean isLogin){
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putBoolean("isLogin", isLogin);
        editor.apply();
    }
}