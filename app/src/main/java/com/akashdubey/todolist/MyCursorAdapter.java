package com.akashdubey.todolist;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import db.DBBase;
import db.DBHelper;
import utils.Constants;

/**
 * Created by FLAdmin on 1/18/2018.
 */

public class MyCursorAdapter extends CursorAdapter {
    public ArrayList<String> tmpDesc=new ArrayList<String>();

    public static Cursor cursor1;
    TextView                date1 ;
    TextView                date2 ;
    static TextView          title ;
    static TextView                description;
    ImageView               status;
    DBHelper                dbHelper ;
    public MarkCompleteListener    listener ;
    private  Integer tmpPosition=0;

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        Log.d("sometag","MyCursorAdapter Initialised");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       View view= LayoutInflater.from(context).inflate(R.layout.custom_view,null);

        Log.d("sometag","MyCursorAdapter layout ready");

        return view;
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {


        date1 = (TextView)view.findViewById(R.id.date1TV);
        date2 = (TextView)view.findViewById(R.id.date2TV);
        title = (TextView)view.findViewById(R.id.titleTV);
        description = (TextView)view.findViewById(R.id.descriptionTV);
        status=(ImageView)view.findViewById(R.id.statusIV);
        status.setTag(cursor1.getPosition());
        date1.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DATE)));
        date2.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DATE)));
        title.setText(cursor1.getString(cursor1.getColumnIndex(Constants.TITLE)));
        description.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DESCRIPTION)));
        tmpDesc.add(description.getText().toString());
        listener = (MarkCompleteListener)context;

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cursor1.getString(cursor1.getColumnIndex(Constants.STATUS)).equals("0")){
                        {
                            String tmpStatus="1";
                            Object tag= view.getTag();
                            tmpPosition=(Integer)tag;
//                            ImageView v=(ImageView)view.findViewWithTag(tag);
//                            v.setImageResource(R.drawable.complete);
                            cursor1.moveToPosition(tmpPosition);
                    tmpPosition=Integer.parseInt(cursor.getString(0));
                    Toast.makeText(context, "row ="+cursor1.getString(0)+" ID = "+cursor1.getString(1), Toast.LENGTH_SHORT).show();
//                            Toast.makeText(context, "position"+tmpPosition, Toast.LENGTH_SHORT).show();
                    listener.markComplete(tmpStatus,tmpPosition);
                }
                //                    Toast.makeText(context, "getting the thumbsup fine", Toast.LENGTH_SHORT).show();
            }
                }
            });
    }



    void getAllData(){
        //db query to list all tasks
        Log.d("sometag","MyCursorAdapter dbHelper opens db connection");
//        dbHelper.openConnection();

            cursor1= DBHelper.db.query(
                    Constants.TABLE_NAME,
                    new String[]{"rowid _id",Constants.TITLE,Constants.DATE,Constants.DESCRIPTION,Constants.STATUS},
                    Constants.STATUS+"=?",new String[]{"0"},null,null,Constants.DATE +" ASC ");


    }


     Cursor getSpecificData(int tmpPosition){

        Cursor cursor1=null;

        cursor1=DBHelper.db.query(Constants.TABLE_NAME,
                new String[]{"rowid _id",Constants.TITLE, Constants.DESCRIPTION,Constants.STATUS},
                Constants.DESCRIPTION+"=?",new String[]{tmpDesc.get(tmpPosition)},null,null,null);
        cursor1.moveToFirst();

//        Toast.makeText(mContext, "mainTitle "+ cursor1.getString(1).toString()+" mainDesc "+cursor1.getString(2).toString(), Toast.LENGTH_SHORT).show();
        return cursor1;
    }


    void getCompletedTaskData(){
        //db query to list all tasks
        Log.d("sometag","MyCursorAdapter dbHelper opens db connection");
//        dbHelper.openConnection();
//
        cursor1= DBHelper.db.query(
                Constants.TABLE_NAME,
                new String[]{"rowid _id",Constants.TITLE,Constants.DATE,Constants.DESCRIPTION,Constants.STATUS},
                Constants.STATUS+"=?",new String[]{"1"},null,null,Constants.DATE +" DESC ");


    }

public interface MarkCompleteListener {

        public void markComplete(String status, Integer position);
}



}
