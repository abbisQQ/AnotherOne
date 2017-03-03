package com.example.babis.anotherone;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Babis on 3/3/2017.
 */

public class BabisProvider extends ContentProvider{

    private DatabaseHlpr databaseHlpr;


    @Override
    public boolean onCreate() {
        databaseHlpr = new DatabaseHlpr(getContext());

        //this might be un-needed !#!@####!@!@$%#!^^^^#^!^$^^$!#^!^$$!^^!%%!$%!%$!!#%#!#!
        databaseHlpr.getWritableDatabase();

        return true;


    }




    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        sqLiteQueryBuilder.setTables(AppContract.TaskEntry.TABLE_NAME);

        switch (AppContract.TaskEntry.uriMatcher.match(uri)){
            case AppContract.TaskEntry.uricode:
                sqLiteQueryBuilder.setProjectionMap(AppContract.TaskEntry.values);
                break;
            default:
                throw  new IllegalArgumentException("Unknown uri " + uri);
        }

        final SQLiteDatabase db = databaseHlpr.getReadableDatabase();
        Cursor cursor = sqLiteQueryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);


        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (AppContract.TaskEntry.uriMatcher.match(uri)){
            case AppContract.TaskEntry.uricode:
                return "vnd.android.cursor.dir/cpcontacts";
            default:
                throw  new IllegalArgumentException("Unsupported uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = databaseHlpr.getWritableDatabase();
        long rowID = db.insert(AppContract.TaskEntry.TABLE_NAME,null,values);

        if(rowID>0){


            Uri _uri = ContentUris.withAppendedId(AppContract.BASE_CONTENT_URI,rowID);

            return _uri;

        }else {

            Toast.makeText(getContext(),"Insert failed",Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rowsDeleted  = 0;
        final SQLiteDatabase db = databaseHlpr.getWritableDatabase();
        switch (AppContract.TaskEntry.uriMatcher.match(uri)){
            case AppContract.TaskEntry.uricode:
                rowsDeleted =  db.delete(AppContract.TaskEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw  new IllegalArgumentException("Unknown uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int rowsUpdated = 0;


        final SQLiteDatabase db = databaseHlpr.getWritableDatabase();
        switch (AppContract.TaskEntry.uriMatcher.match(uri)){
            case AppContract.TaskEntry.uricode:
                rowsUpdated =  db.update(AppContract.TaskEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw  new IllegalArgumentException("Unknown uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);


        return rowsUpdated;
    }



}
