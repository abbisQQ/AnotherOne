package com.example.babis.anotherone;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name,rating;
    MyAdapter mAdapter;
    Cursor cursor;
    RecyclerView mRecyclerView;
    DatabaseHlpr databaseHlpr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHlpr = new DatabaseHlpr(this);

        name = (EditText)findViewById(R.id.name);
        rating = (EditText)findViewById(R.id.ratings);
        cursor = getAllData();

        // Set the RecyclerView to its corresponding view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewTasks);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new MyAdapter(cursor,this);
        mRecyclerView.setAdapter(mAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String id = (String)viewHolder.itemView.getTag(R.string.key);
                String isToDelete = name.getText().toString();

                long idDeleted = getContentResolver().delete(AppContract.BASE_CONTENT_URI," id=?",new String[]{id});

                if(idDeleted>0){
                    Toast.makeText(getBaseContext(),"deleted",Toast.LENGTH_SHORT).show();
                }


                mAdapter.swapCursor(getAllData());

            }
        }).attachToRecyclerView(mRecyclerView);

    }

    public void addDataClicked(View view) {

        String getName = name.getText().toString();
        String getRating = rating.getText().toString();

        ContentValues values = new ContentValues();
        if(getName.length()==0){
            return;
        }
        values.put(AppContract.TaskEntry.NAME, getName);
        values.put(AppContract.TaskEntry.RATING,getRating);

        Uri uri = getContentResolver().insert(AppContract.BASE_CONTENT_URI,values);

        Toast.makeText(getBaseContext(),"data inserted",Toast.LENGTH_SHORT).show();

        mAdapter.swapCursor(getAllData());

    }


    public  Cursor getAllData(){
        final SQLiteDatabase db = databaseHlpr.getReadableDatabase();

        return db.rawQuery("select * from "+AppContract.TaskEntry.TABLE_NAME,null);

    }



    public void deleteClicked(View view) {

        String isToDelete = name.getText().toString();

        long idDeleted = getContentResolver().delete(AppContract.BASE_CONTENT_URI," name=?",new String[]{isToDelete});

        if(idDeleted>0){
            Toast.makeText(this,"deleted",Toast.LENGTH_SHORT).show();
        }


        mAdapter.swapCursor(getAllData());


    }

    public void updateClicked(View view) {

        String isToUpdate = name.getText().toString();
        String getRating = rating.getText().toString();
        ContentValues values = new ContentValues();
        values.put(AppContract.TaskEntry.NAME,isToUpdate);
        values.put(AppContract.TaskEntry.RATING,getRating);
        long idUpdated = getContentResolver().update(AppContract.BASE_CONTENT_URI,values,"name=?",new String[]{isToUpdate});

        if(idUpdated>0){
            Toast.makeText(this,"updated",Toast.LENGTH_SHORT).show();
        }


        mAdapter.swapCursor(getAllData());

    }
}
