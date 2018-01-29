package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import utils.Constants;

/**
 * Here we initialize the db and handle DDL statements
 */

public class DBBase extends SQLiteOpenHelper {


    Context context;

    //building my query to create db with required fields as per srs
    String query="create table if not exists "+ Constants.TABLE_NAME +"("+
            Constants.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Constants.TITLE+" TEXT, "+
            Constants.DESCRIPTION+" TEXT, "+
            Constants.DATE+" timestamp, "+
            Constants.STATUS+" INTEGER );";


    public DBBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    // overriding onCreate and onUpgrade methods to systematically handle creation and updation

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        context.deleteDatabase(Constants.DB);
        onCreate(sqLiteDatabase);
    }

}
