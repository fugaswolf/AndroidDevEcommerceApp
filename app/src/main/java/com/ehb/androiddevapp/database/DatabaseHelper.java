package com.ehb.androiddevapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ehb.androiddevapp.domain.Address;
import com.ehb.androiddevapp.domain.BestSeller;
import com.ehb.androiddevapp.domain.Category;
import com.ehb.androiddevapp.domain.Featured;
import com.ehb.androiddevapp.domain.Items;
import com.ehb.androiddevapp.domain.User;

import java.util.ArrayList;

//this is the database class that handel all sqlite database operation of the app
public class DatabaseHelper extends SQLiteOpenHelper {



    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "mma_db";

    //constructor of class
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // this method called on creation of the object and run the all query of table creation if they are not in
    //database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Address.CREATE_TABLE);
        db.execSQL(BestSeller.CREATE_TABLE);
        db.execSQL(Category.CREATE_TABLE);
        db.execSQL(Featured.CREATE_TABLE);
        db.execSQL(Items.CREATE_TABLE);

    }

    //add new user data in the User table
    public long insertUser(User user){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add it
        values.put(User.COLUMN_Name, user.getName());
        values.put(User.COLUMN_Email, user.getEmail());
        values.put(User.COLUMN_Pass, user.getPassword());

        // insert row
        long id = db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    //add new address in the app
    public long insertAddress(Address address){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add it
        values.put(Address.COLUMN_address, address.getAddress());


        // insert row
        long id = db.insert(Address.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    //add new featured data in the local database
    public long insertFeatured(Featured featured){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add it
        values.put(Featured.COLUMN_Name, featured.getName());
        values.put(Featured.COLUMN_description, featured.getDescription());
        values.put(Featured.COLUMN_img_url, featured.getImg_url());
        values.put(Featured.COLUMN_rating, featured.getRating());
        values.put(Featured.COLUMN_price, featured.getPrice());

        // insert row
        long id = db.insert(Featured.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    //add new best seller in the database
    public long insertBestSeller(BestSeller bestSeller){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add it
        values.put(BestSeller.COLUMN_Name, bestSeller.getName());
        values.put(BestSeller.COLUMN_description, bestSeller.getDescription());
        values.put(BestSeller.COLUMN_img_url, bestSeller.getImg_url());
        values.put(BestSeller.COLUMN_rating, bestSeller.getRating());
        values.put(BestSeller.COLUMN_price, bestSeller.getPrice());

        // insert row
        long id = db.insert(BestSeller.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    //add new item in local database
    public long insertItem(Items items){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add it
        values.put(Items.COLUMN_Name, items.getName());
        values.put(Items.COLUMN_description, items.getDescription());
        values.put(Items.COLUMN_img_url, items.getImg_url());
        values.put(Items.COLUMN_rating, items.getRating());
        values.put(Items.COLUMN_price, items.getPrice());
        values.put(Items.COLUMN_type, items.getType());

        // insert row
        long id = db.insert(Items.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    //add new category in local db records
    public long insertCategory(Category category){
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add it
        values.put(Category.COLUMN_type, category.getType());
        values.put(Category.COLUMN_img_url, category.getImg_url());

        // insert row
        long id = db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    //getting all best seller list from the database
    public ArrayList<BestSeller> getAllBestSeller() {
        SQLiteDatabase db = this.getReadableDatabase();
        //this array list will store the all data
        ArrayList<BestSeller> arrayList = new ArrayList<BestSeller>();
        //query that get all seller from database
        Cursor res = db.rawQuery( "select * from "+BestSeller.TABLE_NAME, null );
        //move the cursor to the first row of results
        res.moveToFirst();
        //loop will continue until last row data extract
        while(!res.isAfterLast()) {
            BestSeller obj = new BestSeller();
            //getting data from database and store in the Object
            obj.setName(res.getString(res.getColumnIndex(BestSeller.COLUMN_Name)));
            obj.setDescription(res.getString(res.getColumnIndex(BestSeller.COLUMN_description)));
            obj.setImg_url(res.getString(res.getColumnIndex(BestSeller.COLUMN_img_url)));
            obj.setPrice(res.getDouble(res.getColumnIndex(BestSeller.COLUMN_price)));
            obj.setRating(res.getInt(res.getColumnIndex(BestSeller.COLUMN_rating)));
            //add the object in arraylist
            arrayList.add(obj);
            //move the cursor the next row
            res.moveToNext();
        }
        //after all iteration returning the arraylist
        return arrayList;
    }
    //getting all featured list from the database
    public ArrayList<Featured> getAllFeatured() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Featured> arrayList = new ArrayList<Featured>();
        //query that get all featured row from database
        Cursor res = db.rawQuery( "select * from "+Featured.TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()) {
            Featured obj = new Featured();
            //getting data from database and store in the Object
            obj.setName(res.getString(res.getColumnIndex(BestSeller.COLUMN_Name)));
            obj.setDescription(res.getString(res.getColumnIndex(BestSeller.COLUMN_description)));
            obj.setImg_url(res.getString(res.getColumnIndex(BestSeller.COLUMN_img_url)));
            obj.setPrice(res.getDouble(res.getColumnIndex(BestSeller.COLUMN_price)));
            obj.setRating(res.getInt(res.getColumnIndex(BestSeller.COLUMN_rating)));
            //add the object in arraylist
            arrayList.add(obj);
            //move the cursor the next row
            res.moveToNext();
        }
        //after all iteration returning the arraylist
        return arrayList;
    }
    public ArrayList<Items> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Items> arrayList = new ArrayList<Items>();
        //query that get all items row from database
        Cursor res = db.rawQuery( "select * from "+Items.TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()) {
            Items obj = new Items();
            //getting data from database and store in the Object
            obj.setName(res.getString(res.getColumnIndex(Items.COLUMN_Name)));
            obj.setDescription(res.getString(res.getColumnIndex(Items.COLUMN_description)));
            obj.setImg_url(res.getString(res.getColumnIndex(Items.COLUMN_img_url)));
            obj.setType(res.getString(res.getColumnIndex(Items.COLUMN_type)));
            obj.setPrice(res.getDouble(res.getColumnIndex(Items.COLUMN_price)));
            obj.setRating(res.getInt(res.getColumnIndex(Items.COLUMN_rating)));
            //add the object in arraylist
            arrayList.add(obj);
            //move the cursor the next row
            res.moveToNext();
        }
        //after all iteration returning the arraylist
        return arrayList;
    }
    public ArrayList<User> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> arrayList = new ArrayList<User>();
        //query that get all users row from database
        Cursor res = db.rawQuery( "select * from "+User.TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()) {
            User obj = new User();
            //getting data from database and store in the Object
            obj.setName(res.getString(res.getColumnIndex(User.COLUMN_Name)));
            obj.setEmail(res.getString(res.getColumnIndex(User.COLUMN_Email)));
            obj.setPassword(res.getString(res.getColumnIndex(User.COLUMN_Pass)));
            //add the object in arraylist
            arrayList.add(obj);
            //move the cursor the next row
            res.moveToNext();
        }
        //after all iteration returning the arraylist
        return arrayList;
    }
    public ArrayList<Address> getAllAddresses() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Address> arrayList = new ArrayList<Address>();
        Cursor res = db.rawQuery( "select * from "+Address.TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()) {
            Address obj = new Address();
            //getting data from database and store in the Object
            obj.setAddress(res.getString(res.getColumnIndex(Address.COLUMN_address)));
            arrayList.add(obj);
            //move the cursor the next row
            res.moveToNext();
        }
        //after all iteration returning the arraylist
        return arrayList;
    }
    public ArrayList<Category> getAllCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Category> arrayList = new ArrayList<Category>();
        //query that get all categories row from database
        Cursor res = db.rawQuery( "select * from "+Category.TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()) {
            Category obj = new Category();
            //getting data from database and store in the Object
            obj.setType(res.getString(res.getColumnIndex(Category.COLUMN_type)));
            obj.setImg_url(res.getString(res.getColumnIndex(Category.COLUMN_img_url)));
            arrayList.add(obj);
            //move the cursor the next row
            res.moveToNext();
        }
        //after all iteration returning the arraylist
        return arrayList;
    }

    //this function will delete the all records of address table
    public void deleteAddress(){
        SQLiteDatabase db = this.getWritableDatabase();
        //query to delete all data from table address
        db.execSQL("delete from "+ Address.TABLE_NAME);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
