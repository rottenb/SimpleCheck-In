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

class TripDataBase extends SQLiteOpenHelper  {
    private static final String LOG_TAG = TripDataBase.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TripDB";

    private static final String TABLE_TRIPS = "trips";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_WHO = "who";
    private static final String KEY_START = "start";
    private static final String KEY_END = "end";
    private static final String KEY_PANIC = "panic";

    private static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_WHO,
                                                KEY_START, KEY_END, KEY_PANIC};

    TripDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRIP_TABLE = "CREATE TABLE trips ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
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

    boolean isTrips() {
        String query = "SELECT * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean result;

        result = cursor.moveToFirst();
        cursor.close();

        return result;
    }

    void addTrip(TripData tripData) {
        Log.d(LOG_TAG, "addTrip("+tripData.toString()+")");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, tripData.getTitle());
        values.put(KEY_WHO, tripData.getWho());
        values.put(KEY_START, tripData.getWhenStart());
        values.put(KEY_END, tripData.getWhenEnd());
        values.put(KEY_PANIC, tripData.getWhenPanic());

        db.insert(TABLE_TRIPS, null, values);
        db.close();
    } // addTrip()

    TripData getTrip(int id) {
        Log.d(LOG_TAG, "getTrip("+id+")");

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
        tripData.setId(Integer.parseInt(cursor.getString(0)));
        tripData.setTitle(cursor.getString(1));
        tripData.setWho(cursor.getString(2));
        tripData.setWhenStart(DateFormat.getDateTimeInstance().format(new Date()));
        tripData.setWhenEnd(DateFormat.getDateTimeInstance().format(new Date()));
        tripData.setWhenPanic(DateFormat.getDateTimeInstance().format(new Date()));

        Log.d(LOG_TAG, tripData.toString());
        cursor.close();
        return tripData;
    } // getTrip()

    public List<TripData> getAllTrips() {
        List<TripData> trips = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TripData tripData;
        if (cursor.moveToFirst()) {
            do {
                tripData = new TripData();
                tripData.setId(Integer.parseInt(cursor.getString(0)));
                tripData.setTitle(cursor.getString(1));
                tripData.setWho(cursor.getString(2));
                tripData.setWhenStart(DateFormat.getDateTimeInstance().format(new Date()));
                tripData.setWhenEnd(DateFormat.getDateTimeInstance().format(new Date()));
                tripData.setWhenPanic(DateFormat.getDateTimeInstance().format(new Date()));

                trips.add(tripData);
            } while (cursor.moveToNext());
        }

        Log.d(LOG_TAG, "getAllTrips()" + trips.toString());

        cursor.close();
        return trips;

    } // getAllTrips()

    List<String> getAllTripTitles() {
        List<String> titles = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TripData tripData;

        if (cursor.moveToFirst()) {
            do {
                tripData = new TripData();
                tripData.setId(Integer.parseInt(cursor.getString(0)));
                tripData.setTitle(cursor.getString(1));
                tripData.setWho(cursor.getString(2));
                tripData.setWhenStart(DateFormat.getDateTimeInstance().format(new Date()));
                tripData.setWhenEnd(DateFormat.getDateTimeInstance().format(new Date()));
                tripData.setWhenPanic(DateFormat.getDateTimeInstance().format(new Date()));

                titles.add(tripData.getTitle());
            } while (cursor.moveToNext());
        }

        cursor.close();
        return titles;
    } // getAllTripTitles()

    void deleteALLTripDB(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}