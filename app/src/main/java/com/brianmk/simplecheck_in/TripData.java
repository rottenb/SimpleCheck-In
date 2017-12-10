package com.brianmk.simplecheck_in;

import java.text.DateFormat;
import java.util.Date;

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
    private String mLocation;
    private int mMapDrawable;
    private String mWhenStart;
    private String mWhenEnd;
    private String mWhenPanic;
    private int mFav;
    private int mActivity;

    public static final int BIKING_IDX = 0;
    public static final int SKIING_IDX = 1;
    public static final int HIKING_IDX = 2;
    public static final int SNOWSHOEING_IDX = 3;
    public static final int TRAIL_RUN_IDX = 4;
    public static final int OTHER_ACT_IDX = 5;

    public TripData() {
        this.mTitle = "";
        this.mLocation = "";
        this.mWho = "";
        this.mMapDrawable = R.drawable.stand_by;
        this.mWhenStart = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenEnd = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenPanic = DateFormat.getDateTimeInstance().format(new Date());
        this.mActivity = OTHER_ACT_IDX;
        this.mFav = 0;
    }

    public TripData(String title, String location, int drawable, String who, int activity_idx, int fav) {
        this.mTitle = title;
        this.mLocation = location;
        this.mMapDrawable = drawable;
        this.mWho = who;
        this.mWhenStart = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenEnd = DateFormat.getDateTimeInstance().format(new Date());
        this.mWhenPanic = DateFormat.getDateTimeInstance().format(new Date());
        this.mActivity = activity_idx;
        this.mFav = fav;
    }

    @Override
    public String toString() {
        return "TripData [id=" + mId + ", title=" + mTitle + ", location=" + mLocation + "/n" +
                ", drawable=" + mMapDrawable +
                ", who=" + mWho +
                ", start=" + mWhenStart + ", end=" + mWhenEnd + ", panic =" + mWhenPanic +
                ", activity=" + mActivity +
                ", favourite=" + mFav +
                "]";
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

    public void setLocation(String location) {
        this.mLocation = location;
    }
    public String getLocation() {
        return this.mLocation;
    }

    public void setMapDrawable(int drawable) {
        this.mMapDrawable = drawable;
    }
    public int getMapDrawable() { return this.mMapDrawable; }

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

    public void setActivity(int idx) {
        this.mActivity = idx;
    }
    public int getActivity() {
        return this.mActivity;
    }

    public int getActivityIcon() {
        switch (this.mActivity) {
            case 0:
                return R.drawable.ic_bike_black_24dp;
            case 1:
                return R.drawable.ic_ski_black_24dp;
            case 2:
                return R.drawable.ic_hiking_black_24dp;
            case 3:
                return R.drawable.ic_snowshoe_black_24dp;
            case 4:
                return R.drawable.ic_trail_run_black_24dp;
            default:
                return R.drawable.ic_walk_black_24dp;
        }
    }

    public void setFav(int f) { this.mFav = f; }
    public int getFav() { return this.mFav; }
    public int getFavIcon() {
        if (mFav == 1) {
            return R.drawable.ic_star_black_24dp;
        } else {
            return 0;
        }
    }

}
