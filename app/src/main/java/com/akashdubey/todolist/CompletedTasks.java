package com.akashdubey.todolist;


/*
This class handles actions needed to view  and delete completed tasks
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import db.DBHelper;
import utils.Constants;
import static com.akashdubey.todolist.MyCursorAdapter.cursor1;


public class CompletedTasks extends AppCompatActivity implements
        MyCursorAdapter.MarkCompleteListener,
        MyCursorAdapter.DeleteListener {
    ListView listView;
    DBHelper dbHelper;
    MyCursorAdapter myCursorAdapter;
    MyCursorAdapter.MarkCompleteListener listener;
    MyCursorAdapter.DeleteListener deleteListener;
    long row;


    //overriding onCreate  to generated needed view  and also handling longPress action
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_task);

        MainActivity.ICON_TASK_COMPLETE=true;
        listView=(ListView)findViewById(R.id.listview);
        dbHelper= new DBHelper(this);
        dbHelper.openConnection();
        myCursorAdapter= new MyCursorAdapter(this, cursor1,0);
        listener=(MyCursorAdapter.MarkCompleteListener)this;
        deleteListener=(MyCursorAdapter.DeleteListener)this;
        myCursorAdapter.getCompletedTaskData();
        listView.setAdapter(myCursorAdapter);
        myCursorAdapter.changeCursor(cursor1);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int tmpPosition = Integer.parseInt(cursor1.getString(0));
                deleteListener.deleteTask(tmpPosition);
                return true;
            }
        });
    }



    @Override
    public void markComplete(String status, Integer position) {

    }

    // handling action for task deletion
    @Override
    public void deleteTask(Integer position) {
        if(!DBHelper.dbHelper.db.isOpen()){
                    dbHelper.openConnection();
        }
                dbHelper.db.delete(Constants.TABLE_NAME,Constants.ID+"= ?",new String[]{position.toString()});
                myCursorAdapter.getCompletedTaskData();
                myCursorAdapter.swapCursor(cursor1);
            }
    }
