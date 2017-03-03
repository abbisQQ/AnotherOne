package com.example.babis.anotherone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Babis on 3/3/2017.
 */

public class DatabaseHlpr extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "tasksDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 2;


    // Constructor
    DatabaseHlpr(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+AppContract.TaskEntry.TABLE_NAME + " ( " +
                AppContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                AppContract.TaskEntry.NAME + " TEXT NOT NULL, "+
                AppContract.TaskEntry.RATING + " TEXT );");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppContract.TaskEntry.TABLE_NAME);
        onCreate(db);

    }


}
