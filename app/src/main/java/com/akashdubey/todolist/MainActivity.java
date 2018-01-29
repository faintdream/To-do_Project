    package com.akashdubey.todolist;


/*
            This class manages the first UI to shown to user and any other action which takes places from there on
 */
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import db.DBHelper;
import utils.Constants;
import static com.akashdubey.todolist.MyCursorAdapter.cursor1;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddNewTask.AddNewTaskListener,
            MyCursorAdapter.MarkCompleteListener,
            ModifyTask.ModifyTaskCursorListener,
            ModifyTask.ModifyTaskListener {


        //gracefuly clsoing curosr and db connection
        @Override
        protected void onDestroy() {
            super.onDestroy();
            cursor1.close();
            dbHelper.closeConnection();
        }
    ListView listView;
    DBHelper dbHelper;
    MyCursorAdapter myCursorAdapter;
    long row;
    ModifyTask.ModifyTaskCursorListener modifyTaskCursorListener;
    public static String mainTitle, mainDesc,mainDate,mainId;
    public static boolean ICON_TASK_COMPLETE=false;

    //handling state of thumb icon, when user returns here from other activity
    @Override
        protected void onRestart() {
            super.onRestart();
            ICON_TASK_COMPLETE=false;
            myCursorAdapter= new MyCursorAdapter(this, cursor1,0);
            myCursorAdapter.getAllData();
            listView.setAdapter(myCursorAdapter);
            myCursorAdapter.changeCursor(cursor1);
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listview);
        dbHelper= new DBHelper(this);
        dbHelper.openConnection();
        myCursorAdapter= new MyCursorAdapter(this, cursor1,0);
        myCursorAdapter.getAllData();
        listView.setAdapter(myCursorAdapter);
        myCursorAdapter.changeCursor(cursor1);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object tag=i;
                myCursorAdapter.setStatusOnClickAction(tag,view);
                return true;
            }
        });

        //handling list view onClick event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    Cursor myCursor = myCursorAdapter.getSpecificData(i);
                    ModifyTask obj = new ModifyTask();
                    obj.show(getSupportFragmentManager(), "specifictag");                               //calling the alert dialog fragment
                    modifyTaskCursorListener = (ModifyTask.ModifyTaskCursorListener) MainActivity.this;
                    modifyTaskCursorListener.SendCursorInfo(myCursor);                                     // passing the current cursor information to Modify task action
                }catch(Exception e){}
            }
        });
    }

        //handling options menu
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.main_menu,menu);                                          // inflating the required layout
            return super.onCreateOptionsMenu(menu);
        }


        //handling click event for corresponding menu item
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int itemId=item.getItemId();
            switch(itemId){
                case R.id.actionAdd:                                                                    //calling add task fragment ( alert dialog)
                    AddNewTask obj= new AddNewTask();
                    obj.show(getSupportFragmentManager(),"some tag");
                    break;

                case R.id.actionAllDone:                                                                //calling mark compete activity
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

        // method handles the db insert
        @Override
        public void InsertData(String title, String desc, String date) {
            dbHelper.openConnection();

            ContentValues value= new ContentValues();
            value.put(Constants.STATUS,"0");
            value.put(Constants.TITLE,title);
            value.put(Constants.DESCRIPTION,desc);
            value.put(Constants.DATE,date);
            row=dbHelper.db.insert(Constants.TABLE_NAME,null,value);
            myCursorAdapter.getAllData();
            myCursorAdapter.swapCursor(cursor1);

            dbHelper.closeConnection();
        }

        //method handles the db update
        @Override
        public void markComplete(String status, Integer position) {
            if(!DBHelper.dbHelper.db.isOpen()){
                dbHelper.openConnection();
            }
            dbHelper.openConnection();
            ContentValues value= new ContentValues();
            value.put(Constants.STATUS,status);
            row=dbHelper.db.update(Constants.TABLE_NAME,value,Constants.ID+"= ?",new String[]{position.toString()});
            myCursorAdapter.getAllData();
            myCursorAdapter.swapCursor(cursor1);
        }


        //method handles the passing of cursor
        @Override
        public void SendCursorInfo(Cursor c1) {
            mainTitle=c1.getString(1);
            mainDesc=c1.getString(2);
            mainDate=c1.getString(3);
            mainId=c1.getString(0);
        }


        //method handles the db update
        @Override
        public void ModifyTask(String id,String title, String desc, String date) {
            dbHelper.openConnection();
            ContentValues value= new ContentValues();
            value.put(Constants.TITLE,title);
            value.put(Constants.DESCRIPTION, desc);
            value.put(Constants.DATE,date);

            row=dbHelper.db.update(Constants.TABLE_NAME,value,Constants.ID+"= ?",new String[]{id.toString()});
            myCursorAdapter.getAllData();
            myCursorAdapter.swapCursor(cursor1);
        }

    }

