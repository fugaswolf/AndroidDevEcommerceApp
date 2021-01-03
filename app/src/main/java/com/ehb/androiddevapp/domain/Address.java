package com.ehb.androiddevapp.domain;

public class Address {
    String address;
    boolean isSelected;

    //Local Database table name of this model
    public static final String TABLE_NAME = "addresses";

    // local database column name of this model
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_address = "address";

    //create table query of this model in local database
    public static final String CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_address + " TEXT"
            + ")";

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Address() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
