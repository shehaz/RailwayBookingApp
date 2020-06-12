package com.shehaz.railwaybookingapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "MyDB.db";
    public static int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "train_booking";
//    public static final String COL_1 = "ID";
    public static final String COL_2 = "SOURCE";
    public static final String COL_3 = "DESTINATION";
    public static final String COL_4 = "QUANTITY";
    public static final String COL_5 = "DATE";
    public static final String COL_6 = "TRAIN_NO";
    public static final String CREATE_TABLE_TRAIN= "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT,SOURCE TEXT,DESTINATION TEXT,QUANTITY INTEGER,DATE TEXT,TRAIN_NO INTEGER)";
    public static final String DELETE_TABLE_TRAIN="DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRAIN);

    }
    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_TRAIN);
        //Create tables again
        onCreate(db);
    }

    public boolean insertData(String source,String destination,String date,String quantity,String number){
        long result = 0;
        // Open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Start the transaction.
        db.beginTransaction();
        ContentValues contentValues;

        try
        {
            contentValues = new ContentValues();
            contentValues.put(COL_2,source);
            contentValues.put(COL_3,destination);
            contentValues.put(COL_4,date);
            contentValues.put(COL_5,quantity);
            contentValues.put(COL_6,number);
            // Insert Row
            result  = db.insert(TABLE_NAME, null, contentValues);
            // Insert into database successfully.
            db.setTransactionSuccessful();


        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
            if(result == -1)
                return false;
            else
                return true;
        }

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


}

