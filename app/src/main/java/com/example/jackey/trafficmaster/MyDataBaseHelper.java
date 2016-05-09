package com.example.jackey.trafficmaster;

/**
 * Created by Jackey on 15/4/8.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_TRAFFIC = "create table traffic ("
            + "id integer primary key autoincrement, "
            + "tracheck integer, "
            + "hour integer, "
            + "day integer, "
            + "month integer, "
            + "year integer)";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRAFFIC);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists Traffic");
        db.execSQL("drop table if exists Category");
        onCreate(db);

    }
}
