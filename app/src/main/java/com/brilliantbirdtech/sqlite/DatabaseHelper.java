package com.brilliantbirdtech.sqlite;

/**
 * Created by HP on 3/8/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Person.db"; // context.deleteDatabase(DATABASE_NAME);
    public static final String TABLE_NAME = "ContactInfo";
    public static final String COL_1 = "AADHAR";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "ADDRESS";
    public static final String COL_4 = "MOBILE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (AADHAR TEXT PRIMARY KEY,NAME TEXT,ADDRESS TEXT,MOBILE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );// only on version change
        //db.execSQL("DELETE FROM ContactInfo ");
        onCreate(db);
    }

    public boolean insertData(String aadhar, String name,String address,String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,aadhar);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,mobile);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_NAME,null);
    }

    public boolean updateData(String id,String name,String address,String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,address);
        contentValues.put(COL_4,mobile);
        db.update(TABLE_NAME, contentValues, "AADHAR = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "AADHAR = ?",new String[] {id});
    }


    public void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );// only on version change
        //db.execSQL("DELETE FROM ContactInfo ");
    }

}