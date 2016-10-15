package com.brianmk.simplecheck_in;

import java.text.DateFormat;
import java.util.Date;

import static android.R.attr.id;

/**
 * Created by rot on 2016-10-15.
 *
 * Holds the data for a single trip
 */

class TripData {
    private static final String LOG_TAG = TripData.class.getSimpleName();

    private int mId;

    private String mTitle;
    private String mWho;

    private String mWhenStart;
    private String mWhenEnd;
    private String mWhenPanic;


    TripData() {
        this.mId = 0;
        this.mTitle = "Where";
        this.mWho = "Who";
        this.mWhenStart = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenEnd = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenPanic = DateFormat.getDateTimeInstance().format(new Date());
    }

    TripData(String title, String who) {
        this.mId = id;
        this.mTitle = title;
        this.mWho = who;

        this.mWhenStart = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenEnd = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenPanic = DateFormat.getDateTimeInstance().format(new Date());
    }

    @Override
    public String toString() {
        return "TripData [id=" + mId + ", title=" + mTitle + ", who=" + mWho +
                ", start=" + mWhenStart + ", end=" + mWhenEnd + ", panic =" + mWhenPanic + "]";
    }

    void setId(int id) {
        this.mId = id;
    }
    int getId() {
        return mId;
    }

    void setTitle(String title) {
        this.mTitle = title;
    }
    String getTitle() {
        return mTitle;
    }

    void setWho(String who) {
        this.mWho = who;
    }
    String getWho() {
        return mWho;
    }

    void setWhenStart(String dateTime) {
        this.mWhenStart = dateTime;
    }
    String getWhenStart() {
        return this.mWhenStart;
    }

    void setWhenEnd(String dateTime) {
        this.mWhenEnd = dateTime;
    }
    String getWhenEnd() {
        return this.mWhenEnd;
    }

    void setWhenPanic(String dateTime) {
        this.mWhenPanic = dateTime;
    }
    String getWhenPanic() {
        return mWhenPanic;
    }

}
