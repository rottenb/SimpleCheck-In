package com.brianmk.simplecheck_in;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rot on 2016-10-14.
 *
 * Simple class containing all data for a single trip
 */

public class TripDataBase extends SQLiteOpenHelper {
    private static final String LOG_TAG = TripDataBase.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TripDB";

    private static final String TABLE_TRIPS = "trips";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DRAWABLE = "drawable";
    private static final String KEY_WHO = "who";
    private static final String KEY_START = "start";
    private static final String KEY_END = "end";
    private static final String KEY_PANIC = "panic";

    private static final int ID_OFFSET = 0;
    private static final int TITLE_OFFSET = 1;
    private static final int LOCATION_OFFSET = 2;
    private static final int DRAWABLE_OFFSET = 3;
    private static final int WHO_OFFSET = 4;
    private static final int WHEN_START_OFFSET = 5;
    private static final int WHEN_END_OFFSET = 6;
    private static final int WHEN_PANIC_OFFSET = 7;

    private static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_LOCATION, KEY_DRAWABLE, KEY_WHO,
                                                KEY_START, KEY_END, KEY_PANIC};

    public TripDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRIP_TABLE = "CREATE TABLE trips ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "location TEXT, " +
                "drawable INTEGER, " +
                "who TEXT, " +
                "start TEXT, end TEXT, panic TEXT )";

        db.execSQL(CREATE_TRIP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS trips");

        this.onCreate(db);
    }

    public boolean isTrips() {
        String query = "SELECT * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean result;

        result = cursor.moveToFirst();
        cursor.close();

        return result;
    }

    public void addTrip(TripData tripData) {
        Log.d(LOG_TAG, "addTrip("+tripData.toString()+")");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, tripData.getTitle());
        values.put(KEY_LOCATION, tripData.getLocation());
        values.put(KEY_DRAWABLE, tripData.getDrawable());
        values.put(KEY_WHO, tripData.getWho());
        values.put(KEY_START, tripData.getWhenStart());
        values.put(KEY_END, tripData.getWhenEnd());
        values.put(KEY_PANIC, tripData.getWhenPanic());

        db.insert(TABLE_TRIPS, null, values);
        db.close();
    } // addTrip()

    public TripData getTrip(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_TRIPS,
                COLUMNS,
                " id = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        TripData tripData = new TripData();

        if (id == 0) {
            cursor.close();
            db.close();

            return tripData;
        }

        tripData.setId(Integer.parseInt(cursor.getString(ID_OFFSET)));
        tripData.setTitle(cursor.getString(TITLE_OFFSET));
        tripData.setTitle(cursor.getString(LOCATION_OFFSET));
        tripData.setDrawable(cursor.getInt(DRAWABLE_OFFSET));
        tripData.setWho(cursor.getString(WHO_OFFSET));
        tripData.setWhenStart(DateFormat.getDateTimeInstance().format(new Date()));
        tripData.setWhenEnd(DateFormat.getDateTimeInstance().format(new Date()));
        tripData.setWhenPanic(DateFormat.getDateTimeInstance().format(new Date()));

        cursor.close();
        db.close();

        return tripData;
    } // getTrip()

    public TripData getTrip(String title) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_TRIPS,
                COLUMNS,
                " title = ?",
                new String[] {String.valueOf(title)},
                null,
                null,
                null,
                null );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        TripData tripData = new TripData();
        tripData.setId(Integer.parseInt(cursor.getString(ID_OFFSET)));
        tripData.setTitle(cursor.getString(TITLE_OFFSET));
        tripData.setLocation(cursor.getString(LOCATION_OFFSET));
        tripData.setDrawable(cursor.getInt(DRAWABLE_OFFSET));
        tripData.setWho(cursor.getString(WHO_OFFSET));
        tripData.setWhenStart(DateFormat.getDateTimeInstance().format(new Date()));
        tripData.setWhenEnd(DateFormat.getDateTimeInstance().format(new Date()));
        tripData.setWhenPanic(DateFormat.getDateTimeInstance().format(new Date()));

        cursor.close();
        db.close();

        return tripData;
    }

    public List<TripData> getAllTrips() {
        List<TripData> trips = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TripData tripData;
        if (cursor.moveToFirst()) {
            do {
                tripData = new TripData();
                tripData.setId(Integer.parseInt(cursor.getString(ID_OFFSET)));
                tripData.setTitle(cursor.getString(TITLE_OFFSET));
                tripData.setLocation(cursor.getString(LOCATION_OFFSET));
                tripData.setDrawable(cursor.getInt(DRAWABLE_OFFSET));
                tripData.setWho(cursor.getString(WHO_OFFSET));
                tripData.setWhenStart(DateFormat.getDateTimeInstance().format(new Date()));
                tripData.setWhenEnd(DateFormat.getDateTimeInstance().format(new Date()));
                tripData.setWhenPanic(DateFormat.getDateTimeInstance().format(new Date()));

                trips.add(tripData);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return trips;

    } // getAllTrips()

    public List<String> getAllTripTitles() {
        List<String> titles = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                titles.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return titles;
    } // getAllTripTitles()

    public void deleteTrip(TripData tripData) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TRIPS, KEY_ID + " = ?", new String[] {String.valueOf(tripData.getId())});
        db.close();
    } //deleteTrip(TripData)

    public void deleteTrip(int position) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TRIPS, KEY_ID + " = ?", new String[] {String.valueOf(position)});
        db.close();
    } //deleteTrip(int)

    public void deleteTrip(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TRIPS, KEY_TITLE + " = ?", new String[] {title});
        db.close();
    } // deleteTrip(String)

    public void deleteALLTripDB(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    } // deleteAllTripDB()

    public int updateTrip(TripData tripData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, tripData.getTitle());
        values.put(KEY_LOCATION, tripData.getLocation());
        values.put(KEY_DRAWABLE, tripData.getDrawable());
        values.put(KEY_WHO, tripData.getWho());
        values.put(KEY_START, tripData.getWhenStart());
        values.put(KEY_END, tripData.getWhenEnd());
        values.put(KEY_PANIC, tripData.getWhenPanic());

        int i = db.update(TABLE_TRIPS,
                values,
                KEY_ID+" = ?",
                new String[] { String.valueOf(tripData.getId()) } );

        db.close();

        Log.d(LOG_TAG, "updateTrip() " + tripData.getId());

        return i;
    } // updateTrip()


} // TripDataBase
