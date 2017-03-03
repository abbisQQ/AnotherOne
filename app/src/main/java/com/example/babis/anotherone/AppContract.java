package com.example.babis.anotherone;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.HashMap;

/**
 * Created by Babis on 3/3/2017.
 */

public class AppContract {


    // The authority, which is how your code knows which Content Provider to access
    public static final String PROVIDER_NAME = "com.example.babis.anotherone.BabisProvider";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/cpcontacts");


    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class TaskEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = Uri.parse(String.valueOf(BASE_CONTENT_URI));


        // Task table and column names
        public static final String TABLE_NAME = "TABLE_NAME";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String NAME = "name";
        public static final String RATING = "rating";
        static  final  int uricode = 1;

        static HashMap<String, String>  values;


        static final UriMatcher uriMatcher;

        static {
            uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(PROVIDER_NAME,"/cpcontacts",uricode);

        }



        /*
        The above table structure looks something like the sample table below.
        With the name of the table and columns on top, and potential contents in rows
        Note: Because this implements BaseColumns, the _id column is generated automatically
        tasks
         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    description     |    priority   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |  Complete lesson   |       1       |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    Go shopping     |       3       |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   Learn guitar     |       2       |
         - - - - - - - - - - - - - - - - - - - - - -
         */

    }
}