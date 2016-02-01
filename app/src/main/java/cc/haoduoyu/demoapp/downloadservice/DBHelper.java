package cc.haoduoyu.demoapp.downloadservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by XP on 2016/2/1.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final int VERSION = 1;
    private static final String CREATE = "create table thread_info(_id integer primary key autoincrement,"
            + "thread_id integer, url text,start intger,end intger, finished integer)";
    private static final String DROP = "drop table if exists thread_info";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP);
        db.execSQL(CREATE);
    }
}
