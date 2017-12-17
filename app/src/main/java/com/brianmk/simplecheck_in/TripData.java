package com.brianmk.simplecheck_in;

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
    private int mMapDrawable;   // TODO: this becomes a screencap from Maps
    private String mStartTime;
    private String mStartDate;
    private String mEndTime;
    private String mEndDate;
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
        this.mMapDrawable = R.drawable.ic_landscape_black_48dp;
        this.mStartDate = "00 Dec 0000";
        this.mStartTime = "00:00";
        this.mEndDate = "00 Dec 0000";
        this.mEndTime = "00:00";

        this.mActivity = OTHER_ACT_IDX;
    }

    public TripData(String title, String location, int drawable, String who, String sDate, String sTime, String eDate, String eTime, int activity_idx) {
        this.mTitle = title;
        this.mLocation = location;
        this.mMapDrawable = drawable;
        this.mWho = who;
        this.mStartDate = sDate;
        this.mStartTime = sTime;
        this.mEndDate = eDate;
        this.mEndTime = eTime;

        this.mActivity = activity_idx;
    }

    @Override
    public String toString() {
        return "TripData [id=" + mId + ", title=" + mTitle + ", location=" + mLocation + "/n" +
                ", drawable=" + mMapDrawable +
                ", who=" + mWho +
                ", startDate=" + mStartDate + ", startTime=" + mStartTime +
                ", endDate=" + mEndDate + ", endTime=" + mEndTime +
                ", activity=" + mActivity +
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

    public void setStartDate(String date) { this.mStartDate = date; }
    public String getStartDate() { return this.mStartDate; }
    public void setStartTime(String time) { this.mStartTime = time; }
    public String getStartTime() { return this.mStartTime; }

    public void setEndDate(String date) { this.mEndDate = date; }
    public String getEndDate() { return this.mEndDate; }
    public void setEndTime(String time) { this.mEndTime = time; }
    public String getEndTime() { return this.mEndTime; }

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


}
