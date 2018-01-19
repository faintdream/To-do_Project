package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import utils.Constants;

/**
 * here we handle the CRUD operations
 */

public class DBHelper {

    public static SQLiteDatabase db;
    private  Context context=null;
    public   DBBase dbBase=null;
    public static DBHelper dbHelper;

     public DBHelper(Context context){
         this.context=context;
         dbBase=new DBBase(this.context, Constants.DB,null,Constants.DB_VERSION);

     }

     public static DBHelper getInstance(Context context){

         if(dbHelper==null){
             dbHelper=new DBHelper(context);

         }

         return dbHelper;
     }

     public void openConnection(){

         try{
             db=dbBase.getWritableDatabase();
         }catch (Exception e){
             e.printStackTrace();
             db=dbBase.getReadableDatabase();
         }
     }


     public void closeConnection(){
         if(db.isOpen()){
             db.close();
         }
     }


}
