package com.brianmk.simplecheck_in;

import java.text.DateFormat;
import java.util.Date;

import static android.R.attr.id;

/**
 * Created by rot on 2016-10-15.
 *
 * Holds the data for a single trip
 */

public class TripData {
    private static final String LOG_TAG = TripData.class.getSimpleName();

    private int mId;

    private String mTitle;
    private String mWho;

    private String mWhenStart;
    private String mWhenEnd;
    private String mWhenPanic;


    public TripData() {
        this.mId = 0;
        this.mTitle = "Where";
        this.mWho = "Who";
        this.mWhenStart = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenEnd = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenPanic = DateFormat.getDateTimeInstance().format(new Date());
    }

    public TripData(String title, String who) {
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

    public void setId(int id) {
        this.mId = id;
    }
    public int getId() {
        return mId;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
    public String getTitle() {
        return mTitle;
    }

    public void setWho(String who) {
        this.mWho = who;
    }
    public String getWho() {
        return mWho;
    }

    public void setWhenStart(String dateTime) {
        this.mWhenStart = dateTime;
    }
    public String getWhenStart() {
        return this.mWhenStart;
    }

    public void setWhenEnd(String dateTime) {
        this.mWhenEnd = dateTime;
    }
    public String getWhenEnd() {
        return this.mWhenEnd;
    }

    public void setWhenPanic(String dateTime) {
        this.mWhenPanic = dateTime;
    }
    public String getWhenPanic() {
        return mWhenPanic;
    }

}
