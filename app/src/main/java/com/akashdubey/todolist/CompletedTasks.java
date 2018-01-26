package com.akashdubey.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import db.DBHelper;

/**
 * Created by FLAdmin on 1/23/2018.
 */

public class CompletedTasks extends AppCompatActivity implements MyCursorAdapter.MarkCompleteListener{
    ListView listView;
    DBHelper dbHelper;
    MyCursorAdapter myCursorAdapter;
    MyCursorAdapter.MarkCompleteListener listener;

    long row;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_task);

        MainActivity.ICON_TASK_COMPLETE=true;
        listView=(ListView)findViewById(R.id.listview);
        dbHelper= new DBHelper(this);
        dbHelper.openConnection();
        myCursorAdapter= new MyCursorAdapter(this, MyCursorAdapter.cursor1,0);
        listener=(MyCursorAdapter.MarkCompleteListener)this;
        myCursorAdapter.getCompletedTaskData();
        listView.setAdapter(myCursorAdapter);
        myCursorAdapter.changeCursor(MyCursorAdapter.cursor1);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CompletedTasks.this, "long press from completedTasks", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }



    @Override
    public void markComplete(String status, Integer position) {

    }

}
