package com.akashdubey.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import db.DBHelper;

/**
 * Created by FLAdmin on 1/23/2018.
 */

public class CompletedTasks extends AppCompatActivity implements MyCursorAdapter.MarkCompleteListener{
    ListView listView;
    DBHelper dbHelper;
    MyCursorAdapter myCursorAdapter;
    long row;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_task);


        listView=(ListView)findViewById(R.id.listview);
        dbHelper= new DBHelper(this);
        dbHelper.openConnection();
            myCursorAdapter= new MyCursorAdapter(this, MyCursorAdapter.cursor1,0);
        myCursorAdapter.getCompletedTaskData();
        listView.setAdapter(myCursorAdapter);
        myCursorAdapter.changeCursor(MyCursorAdapter.cursor1);


    }

    @Override
    public void markComplete(String status, Integer position) {

    }
}
