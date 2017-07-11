package com.egci428.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mek on 27/11/2559.
 */
public class ProfileDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String [] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERNAME, MySQLiteHelper.COLUMN_PASSWORD, MySQLiteHelper.COLUMN_LATITUDE, MySQLiteHelper.COLUMN_LONGITUDE};

    public ProfileDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    public void createProfile(String user, String pass, String lat, String longi){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USERNAME, user);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, pass);
        values.put(MySQLiteHelper.COLUMN_LATITUDE, lat);
        values.put(MySQLiteHelper.COLUMN_LONGITUDE, longi);
        long insertId = database.insert(MySQLiteHelper.TABLE_MYLOCATION,null,values);
    }

    public void deleteProfile (Profile profile){
        long id = profile.getId();
        System.out.println("Profile deleted with id: "+id);
        database.delete(MySQLiteHelper.TABLE_MYLOCATION, MySQLiteHelper.COLUMN_ID + " = "+id,null);
    }

    public List<Profile> getAllProfiles(){
        List<Profile> profileList = new ArrayList<Profile>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MYLOCATION, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        //equals null come out loop i.e. last line
        //keep adding one row into arraylist
        while(!cursor.isAfterLast()){
            Profile profile = cursorToProfile(cursor);
            profileList.add(profile);
            cursor.moveToNext();
        }
        cursor.close();
        return profileList;
    }

    public Cursor getInformation(ProfileDataSource dataSource){
        database = dbHelper.getReadableDatabase();
        Cursor CR = database.query(MySQLiteHelper.TABLE_MYLOCATION, allColumns, null, null, null, null, null);
        return CR;
    }

    // why parse to integer?????
    private Profile cursorToProfile(Cursor cursor){
        Profile profile = new Profile(cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4));
        System.out.println("String = "+cursor.getString(1));
        System.out.println("String = "+cursor.getString(2));
        profile.setId(cursor.getLong(0));
        return  profile;
    }
}
