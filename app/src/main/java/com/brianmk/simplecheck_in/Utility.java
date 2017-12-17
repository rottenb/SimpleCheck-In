package com.brianmk.simplecheck_in;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import java.util.Comparator;

/**
 * Created by rot on 2016-10-27.
 *
 * Collection of handy functions
 */

public class Utility {
    // ADMIN FUNCTION: populate the trip database with a few trips
    static public void populateTripDB(TripDataBase tdb) {
        tdb.addTrip(new TripData("Nelson - Mountain Station",
                "http://www.trailforks.com/region/mountain-station/",
                R.drawable.nelson_mountain_station,
                "Brian Kennedy, Christine Wallace",
                "14 Dec 2017", "11:00", "14 Dec 2017", "14:30",
                TripData.HIKING_IDX));

        tdb.addTrip(new TripData("Nelson - Giveout/Gold Creek",
                "http://www.trailforks.com/region/giveout-and-gold-creek/",
                R.drawable.nelson_giveout,
                "Brian Kennedy",
                "10 Apr 2017", "10:00", "10 Apr 2017", "17:45",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Whistler - Bike Park",
                "http://www.trailforks.com/region/whistler-mountain-bike-park/",
                R.drawable.whistler_bike_park,
                "Brian Kennedy, Kay Cahill",
                "01 Jun 2017", "09:00", "01 Jun 2017", "15:30",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("North Vancouver - Seymour",
                "http://www.trailforks.com/region/mount-seymour/",
                R.drawable.north_vancouver_seymour,
                "Brian Kennedy, Kay Cahill, James Wade",
                "27 Dec 2017", "08:00", "27 Dec 2017", "16:00",
                TripData.SNOWSHOEING_IDX));

        tdb.addTrip(new TripData("North Vancouver - Fromme",
                "http://www.trailforks.com/region/mount-fromme/",
                R.drawable.north_vancouver_fromme,
                "Brian Kennedy, Kay Cahill, James Wade, Chris White",
                "15 Aug 2017", "12:00", "15 Aug 2017", "16:00",
                TripData.SKIING_IDX));

        tdb.addTrip(new TripData("Fraser Valley - Burke",
                "http://www.trailforks.com/region/burke-mountain/",
                R.drawable.fraser_valley_burke,
                "Brian Kennedy, Kay Cahill, Chris White",
                "03 Oct 2017", "16:00", "03 Dec 2017", "20:30",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Fraser Valley - Sumas",
                "http://www.trailforks.com/region/sumas-mountain/",
                R.drawable.fraser_valley_sumas,
                "Brian Kennedy, James Wade, Clayton MacDonald",
                "06 May 2017", "11:30", "06 May 2017", "17:00",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Squamish - Diamond Head",
                "http://www.trailforks.com/region/diamond-head/",
                R.drawable.squamish_diamondhead,
                "Brian Kennedy, Kay Cahill, Clayton MacDonald, James Wade",
                "12 Sep 2017", "10:00", "12 Sep 2017", "15:45",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Squamish - Alice Lake",
                "http://www.trailforks.com/region/alice-lake--highlands/",
                R.drawable.squamish_alice_lake,
                "Brian Kennedy, Ryan B, Ashley S",
                "01 Nov 2017", "13:30", "01 Nov 2017", "17:15",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Squamish - Diamond Head (ski)",
                "http://www.trailforks.com/region/diamond-head/",
                R.drawable.squamish_diamondhead,
                "Brian Kennedy, Kay Cahill, Clayton MacDonald",
                "06 Dec 2017", "06:00", "07 Dec 2017", "16:00",
                TripData.SKIING_IDX));

        tdb.addTrip(new TripData("Whistler - Whistler Mountain",
                "http://www.trailforks.com/region/whistler-mountain-bike-park/",
                R.drawable.whistler_bike_park,
                "Brian Kennedy, Kay Cahill, Kirsten Ruecker",
                "20 Jul 2017", "10:00", "20 Jul 2017", "16:30",
                TripData.SKIING_IDX));

        tdb.addTrip(new TripData("Bellingham - Galbraith",
                "http://www.trailforks.com/region/galbraith/",
                R.drawable.galbraith,
                "Brian Kennedy, Kay Cahill, Dylan Morgan, Chris Duppenthaler",
                "30 Aug 2017", "11:30", "30 Aug 2017", "19:00",
                TripData.BIKING_IDX));
    }

    static public void sendMessage(Activity activity, TripData tripData) {
        String subject = tripData.getTitle() + " " + tripData.getStartDate() + " @ " + tripData.getStartTime();
        String body =   "WHERE: " + tripData.getLocation() + "\n\n" +
                "WHO: " + tripData.getWho() + "\n\n" +
                "WHEN (start): " + tripData.getStartDate() + " @ " + tripData.getStartTime() + "\n" +
                "WHEN (end): " + tripData.getEndDate() + " @ " + tripData.getEndTime();

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"backcountry.checkin@gmail.com"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            activity.startActivity(Intent.createChooser(sendIntent, "Email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            // Lolwut?
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}


class TripTitleComparator implements Comparator<TripData> {
    public int compare(TripData t1, TripData t2) {
        return t1.getTitle().compareTo(t2.getTitle());
    }
}

class TripActivityComparator implements Comparator<TripData> {
    public int compare(TripData t1, TripData t2) {
        return t1.getActivity() - t2.getActivity();
    }
}
