    package com.akashdubey.todolist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
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

    public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddNewTask.AddNewTaskListener, MyCursorAdapter.MarkCompleteListener{

    ListView listView;
    DBHelper dbHelper;
    MyCursorAdapter myCursorAdapter;
    long row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listview);
        dbHelper= new DBHelper(this);
        dbHelper.openConnection();
        myCursorAdapter= new MyCursorAdapter(this, MyCursorAdapter.cursor1,0);
        myCursorAdapter.getAllData();
        listView.setAdapter(myCursorAdapter);
        myCursorAdapter.changeCursor(MyCursorAdapter.cursor1);
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


            // unused code i am going to remove it, i shall not fail to do so..

            Date tmpDate= null;
            try {

                tmpDate = new SimpleDateFormat("yy-mm-dd", Locale.ENGLISH).parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            //getting ready to insert data
            ContentValues value= new ContentValues();
            value.put(Constants.STATUS,"0");
            value.put(Constants.TITLE,title);
            value.put(Constants.DESCRIPTION,desc);
            value.put(Constants.DATE,date);

//            the actual insert
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
    }
