package com.example.finalprojectapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NewsArticles.DB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Articles";
    public static final String COL_ID = "_ID";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_URL = "URL";
    public static final String COL_DATE = "DATE";
    public static final String COL_SECTION = "SECTION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " text, "
                + COL_URL + " text, "
                + COL_DATE + " text, "
                + COL_SECTION + " text);");  // add or remove columns
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
