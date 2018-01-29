package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import utils.Constants;

/**
 * here we handle db instantiation, open and close
 */


//initializing the DBHelper constructor with db name, context and version
public class DBHelper {

    public static SQLiteDatabase db;
    private  Context context=null;
    public   DBBase dbBase=null;
    public static DBHelper dbHelper;

     public DBHelper(Context context){
         this.context=context;
         dbBase=new DBBase(this.context, Constants.DB,null,Constants.DB_VERSION);

     }


     //initialize the instance just once
     public static DBHelper getInstance(Context context){
         if(dbHelper==null){
             dbHelper=new DBHelper(context);
         }
         return dbHelper;
     }


     //db connection opener
     public void openConnection(){
         try{
             db=dbBase.getWritableDatabase();
         }catch (Exception e){
             e.printStackTrace();
             db=dbBase.getReadableDatabase();
         }
     }

    // for gracefully clsoing db connection
     public void closeConnection(){
         if(db.isOpen()){
             db.close();
         }
     }

}
