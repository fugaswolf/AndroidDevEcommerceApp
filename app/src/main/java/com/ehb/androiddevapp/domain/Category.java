package com.ehb.androiddevapp.domain;

public class Category {

    String type;
    String img_url;
    // local database table name of this model
    public static final String TABLE_NAME = "category";
    // local database column name of this model
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_type = "type";
    public static final String COLUMN_img_url = "img_url";
    //create table query of this model in local database
    public static final String CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_type + " TEXT,"
            + COLUMN_img_url + " TEXT"
            + ")";

    public Category() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
