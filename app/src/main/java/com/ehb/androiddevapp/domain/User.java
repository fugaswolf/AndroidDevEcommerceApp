package com.ehb.androiddevapp.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String name,email,password;

    // local database table name of this model
    public static final String TABLE_NAME = "users";

    // local database column name of this model
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_Name = "name";
    public static final String COLUMN_Pass = "password";
    public static final String COLUMN_Email = "email";
    // local database table create query of this model
    public static final String CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_Name + " TEXT,"
            + COLUMN_Email + " TEXT,"
            + COLUMN_Pass + " TEXT"
            + ")";


    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
