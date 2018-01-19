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

import db.DBBase;
import db.DBHelper;
import utils.Constants;

/**
 * Created by FLAdmin on 1/18/2018.
 */

public class MyCursorAdapter extends CursorAdapter {
    public static Cursor cursor1;
    TextView                date1 ;
    TextView                date2 ;
    TextView                title ;
    TextView                description;
    ImageView               status;
    DBHelper                dbHelper ;
    MarkCompleteListener    listener ;

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
    public void bindView(View view, final Context context, Cursor cursor) {

        date1 = (TextView)view.findViewById(R.id.date1TV);
        date2 = (TextView)view.findViewById(R.id.date2TV);
        title = (TextView)view.findViewById(R.id.titleTV);
        description = (TextView)view.findViewById(R.id.descriptionTV);
        status=(ImageView)view.findViewById(R.id.statusIV);
        date1.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DATE)));
        date2.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DATE)));
        title.setText(cursor1.getString(cursor1.getColumnIndex(Constants.TITLE)));
        description.setText(cursor1.getString(cursor1.getColumnIndex(Constants.DESCRIPTION)));

            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cursor1.getString(cursor1.getColumnIndex(Constants.STATUS)).equals("0")){
                        {
                            String tmpStatus="1";
                            listener= new MarkCompleteListener() {
                                @Override
                                public void markComplete(String status) {

                                }
                            };
                            listener.markComplete(tmpStatus);
                            status.setImageResource(R.drawable.complete);
                        }

                        //                    Toast.makeText(context, "getting the thumbsup fine", Toast.LENGTH_SHORT).show();
                    }


                }
            });

    }

    void getAllData(){
        //db query to list all tasks
        Log.d("sometag","MyCursorAdapter dbHelper opens db connection");
        if (DBHelper.db.isOpen()){
            cursor1= DBHelper.db.query(
                    Constants.TABLE_NAME,
                    new String[]{"rowid _id",Constants.TITLE,Constants.DATE,Constants.DESCRIPTION,Constants.STATUS},
                    Constants.STATUS+"=?",new String[]{"0"},null,null,null);
        }

    }


public interface MarkCompleteListener {
        public void markComplete(String status);
}


}