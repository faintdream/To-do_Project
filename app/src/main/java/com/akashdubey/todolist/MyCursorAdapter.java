package com.akashdubey.todolist;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import db.DBHelper;
import utils.Constants;

/**
 * Created by FLAdmin on 1/18/2018.
 */

public class MyCursorAdapter extends CursorAdapter {
    public static Cursor cursor1;
    TextView         date1 ;
    TextView         date2 ;
    TextView         title ;
    TextView         description;
    ImageView        status;
    DBHelper dbHelper ;

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        Log.d("sometag","MyCursorAdapter Initialised");
    }

//    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
//        super(context, c, autoRequery);
//    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       View view= LayoutInflater.from(context).inflate(R.layout.custom_view,null);

        Log.d("sometag","MyCursorAdapter binding success");

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        date1 = (TextView)view.findViewById(R.id.date1TV);
        date2 = (TextView)view.findViewById(R.id.date2TV);
        title = (TextView)view.findViewById(R.id.titleTV);
        description = (TextView)view.findViewById(R.id.descriptionTV);
        status=(ImageView)view.findViewById(R.id.statusIV);
        date1.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DATE)));
        date2.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DATE)));
        title.setText(cursor1.getString(cursor1.getColumnIndex(Constants.TITLE)));
        description.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DESCRIPTION)));


    }

//    public interface CursorListener {
//        void shareCursor(Cursor c1);
//    }

    void getAllData(){
        //db query

        Log.d("sometag","MyCursorAdapter dbHelper opens db connection");
        if (DBHelper.db.isOpen()){
            cursor1= DBHelper.db.query(
                    Constants.TABLE_NAME,
                    new String[]{"rowid _id",Constants.TITLE,Constants.DATE,Constants.DESCRIPTION},
                    null,null,null,null,null);
        }

    }
}
