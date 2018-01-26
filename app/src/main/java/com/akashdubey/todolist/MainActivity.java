    package com.akashdubey.todolist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import db.DBHelper;
import utils.Constants;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddNewTask.AddNewTaskListener, MyCursorAdapter.MarkCompleteListener, ModifyTask.ModifyTaskCursorListener, ModifyTask.ModifyTaskListner{

    ListView listView;
    DBHelper dbHelper;
    MyCursorAdapter myCursorAdapter;
    long row;
    ModifyTask.ModifyTaskCursorListener modifyTaskCursorListener;
    public static String mainTitle, mainDesc,mainDate,mainId;
    public static boolean ICON_TASK_COMPLETE=false;
        @Override
        protected void onRestart() {
            super.onRestart();
            ICON_TASK_COMPLETE=false;
            myCursorAdapter= new MyCursorAdapter(this, MyCursorAdapter.cursor1,0);
            myCursorAdapter.getAllData();
            listView.setAdapter(myCursorAdapter);
            myCursorAdapter.changeCursor(MyCursorAdapter.cursor1);

        }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        specificDataListener=(SpecificDataListener)getApplicationContext();

        listView=(ListView)findViewById(R.id.listview);
        dbHelper= new DBHelper(this);
        dbHelper.openConnection();
        myCursorAdapter= new MyCursorAdapter(this, MyCursorAdapter.cursor1,0);
        myCursorAdapter.getAllData();
        listView.setAdapter(myCursorAdapter);
        myCursorAdapter.changeCursor(MyCursorAdapter.cursor1);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             Cursor myCursor= myCursorAdapter.getSpecificData(i);
             ModifyTask obj= new ModifyTask();
             obj.show(getSupportFragmentManager(),"specifictag");
             modifyTaskCursorListener=(ModifyTask.ModifyTaskCursorListener)MainActivity.this;
             modifyTaskCursorListener.SendCursorInfo(myCursor);
            }
        });
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.main_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId=item.getItemId();

            switch(itemId){
                case R.id.actionAdd:
                    AddNewTask obj= new AddNewTask();
                    obj.show(getSupportFragmentManager(),"some tag");
                    break;

                case R.id.actionAllDone:
                    Intent action= new Intent(MainActivity.this,CompletedTasks.class);
                    startActivity(action);
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onClick(View view) {
        }

        public void openAddTask(){
            AddNewTask obj= new AddNewTask();
            obj.show(getSupportFragmentManager(),"some tag");
        }

        @Override
        public void InsertData(String title, String desc, String date) {
            //initialising db and opening connection
            dbHelper.openConnection();

            //getting ready to insert data
            ContentValues value= new ContentValues();
            value.put(Constants.STATUS,"0");
            value.put(Constants.TITLE,title);
            value.put(Constants.DESCRIPTION,desc);
            value.put(Constants.DATE,date);

            //the actual insert
            row=dbHelper.db.insert(Constants.TABLE_NAME,null,value);
            ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
            myCursorAdapter.getAllData();
            myCursorAdapter.swapCursor(MyCursorAdapter.cursor1);

            //closing db connection explicitly
            dbHelper.closeConnection();
        }

        @Override
        public void markComplete(String status, Integer position) {
            dbHelper.openConnection();
            ContentValues value= new ContentValues();
            value.put(Constants.STATUS,status);
            //MyCursorAdapter.cursor1.moveToPosition(position);

            row=dbHelper.db.update(Constants.TABLE_NAME,value,Constants.ID+"= ?",new String[]{position.toString()});
            myCursorAdapter.getAllData();
            myCursorAdapter.swapCursor(MyCursorAdapter.cursor1);
//            dbHelper.closeConnection();
        }



        @Override
        public void SendCursorInfo(Cursor c1) {
            mainTitle=c1.getString(1);
            mainDesc=c1.getString(2);
            mainDate=c1.getString(3);
            mainId=c1.getString(0);

        }


        @Override
        public void ModifyTask(String id,String title, String desc, String date) {
            dbHelper.openConnection();
            ContentValues value= new ContentValues();
            value.put(Constants.TITLE,title);
            value.put(Constants.DESCRIPTION, desc);
            value.put(Constants.DATE,date);

            row=dbHelper.db.update(Constants.TABLE_NAME,value,Constants.ID+"= ?",new String[]{id.toString()});
            myCursorAdapter.getAllData();
            myCursorAdapter.swapCursor(MyCursorAdapter.cursor1);
        }
    }

