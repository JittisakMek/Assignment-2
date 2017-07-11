package com.egci428.assignment2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mek on 27/11/2559.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MYLOCATION = "myLocation_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_LATITUDE = "Latitude";
    public static final String COLUMN_LONGITUDE = "Longitude";

    private static final String DATABASE_NAME = "myLocation.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_MYLOCATION + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_USERNAME + " text," + COLUMN_PASSWORD + " text," + COLUMN_LATITUDE + " text," + COLUMN_LONGITUDE + " text);";

    public  MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("MySQLiteHelper", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE); //create new database
        Log.d("MySQLiteHelper","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYLOCATION);
        onCreate(db);
    }
}
