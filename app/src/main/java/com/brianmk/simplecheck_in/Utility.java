package com.brianmk.simplecheck_in;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

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
                "Brian K",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Nelson - Giveout/Gold Creek",
                "http://www.trailforks.com/region/giveout-and-gold-creek/",
                R.drawable.nelson_giveout,
                "Brian K",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Whistler - Bike Park",
                "http://www.trailforks.com/region/whistler-mountain-bike-park/",
                R.drawable.whistler_bike_park,
                "Brian K, Kay C",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("North Vancouver - Seymour",
                "http://www.trailforks.com/region/mount-seymour/",
                R.drawable.north_vancouver_seymour,
                "Brian K, Kay C, James W",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("North Vancouver - Fromme",
                "http://www.trailforks.com/region/mount-fromme/",
                R.drawable.north_vancouver_fromme,
                "Brian K, Kay C, James W, Chris W",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Fraser Valley - Burke",
                "http://www.trailforks.com/region/burke-mountain/",
                R.drawable.fraser_valley_burke,
                "Brian K, Kay C, Chris W",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Fraser Valley - Sumas",
                "http://www.trailforks.com/region/sumas-mountain/",
                R.drawable.fraser_valley_sumas,
                "Brian K, James W, Clayton M",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Squamish - Diamond Head",
                "http://www.trailforks.com/region/diamond-head/",
                R.drawable.squamish_diamondhead,
                "Brian K, Kay C, Clayton M, James W",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Squamish - Alice Lake",
                "http://www.trailforks.com/region/alice-lake--highlands/",
                R.drawable.squamish_alice_lake,
                "Brian K, Ryan B, Ashley S",
                TripData.BIKING_IDX));

        tdb.addTrip(new TripData("Squamish - Diamond Head (ski)",
                "http://www.trailforks.com/region/diamond-head/",
                R.drawable.squamish_diamondhead,
                "Brian K, Kay C, Clayton M",
                TripData.SKIING_IDX));

        tdb.addTrip(new TripData("Whistler - Whistler Mountain",
                "http://www.trailforks.com/region/whistler-mountain-bike-park/",
                R.drawable.whistler_bike_park,
                "Brian K, Kay C, Kirsten R",
                TripData.SKIING_IDX));

        tdb.addTrip(new TripData("Bellingham - Galbraith",
                "http://www.trailforks.com/region/galbraith/",
                R.drawable.galbraith,
                "Brian K, Kay C, Dylan M, Chris D",
                TripData.SKIING_IDX));


    }

    static public void sendMessage(Activity activity, TripData tripData) {
        String subject = tripData.getTitle() + " " + tripData.getWhenStart();
        String body =   "WHERE: " + tripData.getLocation() + "\n\n" +
                "WHO: " + tripData.getWho() + "\n\n" +
                "WHEN (start): " + tripData.getWhenStart() + "\n" +
                "WHEN (end): " + tripData.getWhenEnd() + "\n" +
                "WHEN (panic): " + tripData.getWhenPanic();

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"brianmk@zombieworld.com"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            activity.startActivity(Intent.createChooser(sendIntent, "Email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    static public void editTrip() {

    }
}
