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
    private final static String column2 = "UN";
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

    public Cursor getAllRecords(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + tblname + " where deleted='NO'";
        return db.rawQuery(query, null);
    }

    public Cursor getRecord(String uid){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + tblname + " where uid='" + uid + "'";
        return db.rawQuery(query, null);
    }

    public String getUID(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select uid from " + tblname, null);;
        int count = cursor.getCount();
        String uid = "000000";
        if(count >= 0 && count < 9){
            uid = "00000" + (count+1);
        }
        else if(count >= 9 && count < 99){
            uid = "0000" + (count+1);
        }
        else if(count >= 99 && count < 999){
            uid = "000" + (count+1);
        }
        else if(count >= 999 && count < 9999){
            uid = "00" + (count+1);
        }
        else if(count >= 9999 && count < 99999){
            uid = "0" + (count+1);
        }
        else if(count >= 99999 && count < 999999){
            uid = "" + (count+1);

        }
        return uid;
    }

    public void addNewRecord(String un,String fn, String ln, String pass){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column1,getUID());
        values.put(column2, un);
        values.put(column3, fn);
        values.put(column4, ln);
        values.put(column5, pass);
        values.put(column6, "NO");

        db.insert(tblname, null, values);
        db.close();
    }

    public void editRecord(String uid, String un,String fn, String ln, String pass){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column2, un);
        values.put(column3, fn);
        values.put(column4, ln);
        values.put(column5, pass);
        values.put(column6, "NO");

        String args[] = {uid};
        db.update(tblname, values,"uid=?",args);
        db.close();
    }

    public void deleteRecord(String uid){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column6, "YES");
        String args[] = {uid};
        db.update(tblname, values,"uid=?",args);
        db.close();
    }

}