package com.sjsu.cmpe.kartisian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamtha on 10/5/14.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "kartisianLocations";

    // Table name
    private static final String TABLE_PLACES = "places";

    // Table Columns names
    private static final String KEY_LOCATIONNAME = "location_name";
    private static final String KEY_CONCADDRESS = "concatenated_address";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        String CREATE_LOCATIONS_TABLE = "CREATE TABLE places(location_name TEXT PRIMARY KEY, concatenated_address TEXT, latitude TEXT, longitude TEXT)";
        db.execSQL(CREATE_LOCATIONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        // Create tables again
        onCreate(db);
    }

    /**
     * Inserting new place into places table
     * */
    public void insertPlace(String locationname, String conc_address, String lat, String longi ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOCATIONNAME, locationname);
        values.put(KEY_CONCADDRESS,conc_address);
        values.put(KEY_LATITUDE,lat);
        values.put(KEY_LONGITUDE,longi);

        // Inserting Row
        db.insert(TABLE_PLACES, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting all Places
     * returns list of Places
     * */
    public List<String> getAllPlaces(){
        List<String> places = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT location_name FROM places ORDER BY location_name";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                places.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning places
        return places;
    }

    public String getAPlace(String spintext)
    {
        String destination = new String();
        String selectQuery = new String("SELECT * FROM places where location_name = '" + spintext + "'");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(1)!=null) {
                    destination = cursor.getString(1);
                }
                else
                {
                    if((cursor.getString(2)!=null)&&(cursor.getString(3)!=null))
                    {
                        destination = cursor.getString(2)+","+cursor.getString(3);
                    }
                    else
                        destination = null;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return destination;
    }
}
