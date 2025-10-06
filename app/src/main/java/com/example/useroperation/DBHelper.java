package com.example.useroperation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{
    private final static String dbname = "UserDB.s3db";
    private final static int dbversion = 1;

    private final static String tblname = "users";
    private final static String column1 = "UID";
    private final static String column2 = "UserN";
    private final static String column3 = "FN";
    private final static String column4 = "LN";
    private final static String column5 = "Pass";
    private final static String column6 = "DELETED"; // YES or NO - if deleted or not

    //create the database
    public DBHelper(Context context) { //constructor
        super(context, dbname, null, dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + tblname + " ( " +
                column1 + " TEXT (6) PRIMARY KEY, " +
                column2 + " TEXT (20), " +
                column3 + " TEXT (30), " +
                column4 + " TEXT (20), " +
                column5 + " TEXT (10), " +
                column6 + " TEXT (3) )";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tblname);
        onCreate(sqLiteDatabase);
    }



}
