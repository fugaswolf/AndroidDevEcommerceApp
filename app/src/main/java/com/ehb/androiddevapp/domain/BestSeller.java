package com.ehb.androiddevapp.domain;

import java.io.Serializable;

public class BestSeller implements Serializable {
    String description;
    String name;
    String img_url;
    double price;
    int rating;

    //Local Database table name of this model
    public static final String TABLE_NAME = "best_seller";

    // local database column name of this model
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_Name = "name";
    public static final String COLUMN_description = "description";
    public static final String COLUMN_img_url = "img_url";
    public static final String COLUMN_rating = "rating";
    public static final String COLUMN_price = "price";

    //create table query of this model in local database
    public static final String CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_Name + " TEXT,"
            + COLUMN_description + " TEXT,"
            + COLUMN_img_url + " TEXT,"
            + COLUMN_rating + " INTEGER,"
            + COLUMN_price + " REAL"
            + ")";


    public BestSeller() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
