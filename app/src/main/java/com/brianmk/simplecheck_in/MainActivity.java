package com.brianmk.simplecheck_in;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private List<String> tripTitleList;
    private ArrayAdapter<String> mTripListAdapter;
    private ListView tripListView;
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TripDataBase tripDB = new TripDataBase(this);

        tripTitleList = tripDB.getAllTripTitles();
        tripTitleList.add(0, "-- Tap To Add New Trip --");

        mTripListAdapter = new ArrayAdapter<> (this, R.layout.trip_list_item,
                                                R.id.trip_list_item_textview,
                                                tripTitleList );
        tripListView = (ListView) findViewById(R.id.trip_list);
        tripListView.setAdapter(mTripListAdapter);

        registerForContextMenu(tripListView);


        // Tapping on a list item brings up the trip details overview
        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                if (position == 0) {
                    Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
                    tripIntent.putExtra("LIST_POSITION", position);
                    tripIntent.putExtra("LIST_TITLE", tripTitleList.get(position));
                    startActivity(tripIntent);
                } else {
                    DialogFragment dialog = new TripDialog();
                    Bundle args = new Bundle();
                    args.putString("LIST_TITLE", tripTitleList.get(position));
                    dialog.setArguments(args);
                    dialog.show(getFragmentManager(), "Trip Details");
                    Log.d(LOG_TAG, "returned to main");
                }
            }
        });
    } // onCreate()

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        if (info.position == 0)
            return; // No menu

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu_delete:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                // Delete the trip entry, find it in the database through it's title
                TripDataBase tdb = new TripDataBase(getApplicationContext());
                tdb.deleteTrip(tripTitleList.get(info.position));

                mTripListAdapter.remove(tripTitleList.get(info.position));

                tdb.close();

                break;

            default:
                Log.d(LOG_TAG, "onContextItemSelected: FELL THROUGH!");
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    } // onActivityResult()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        TripDataBase tdb = new TripDataBase(this);

        // Menu items
        switch (id) {
            case R.id.main_menu_add:
                Log.d(LOG_TAG, "add");
                break;
            case R.id.main_menu_edit:
                Log.d(LOG_TAG, "edit");
                break;
            case R.id.main_menu_user_settings:
                Log.d(LOG_TAG, "user settings");
                break;
            case R.id.main_menu_app_settings:
                Log.d(LOG_TAG, "app settings");
                break;
            case R.id.main_menu_delete_db:
                Log.d(LOG_TAG, "DELETE DATABASE");

                tdb.deleteALLTripDB(this);

                mTripListAdapter.notifyDataSetChanged();
                tripListView.setAdapter(mTripListAdapter);

                tdb.close();

                break;
            case R.id.main_menu_create_db:
                Log.d(LOG_TAG, "CREATE DATABASE");

                populateTripDB(tdb);

                mTripListAdapter.notifyDataSetChanged();
                tripListView.setAdapter(mTripListAdapter);

                tdb.close();

                break;
            default:
                Log.d(LOG_TAG, "Oops, fell through");
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void editTrip(View view) {
        Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
        tripIntent.putExtra("LIST_POSITION", mPosition);
        tripIntent.putExtra("LIST_TITLE", tripTitleList.get(mPosition));
        startActivity(tripIntent);
    }

    public void sendTrip(View view) {
        Log.d(LOG_TAG, "sendTrip()");
    }

    private void populateTripDB(TripDataBase tdb) {
        tdb.addTrip(new TripData("Nelson - Mountain Station",
                                "http://www.trailforks.com/region/mountain-station/",
                                R.drawable.nelson_mountain_station,
                                "Brian K"));

        tdb.addTrip(new TripData("Nelson - Giveout/Gold Creek",
                                "http://www.trailforks.com/region/giveout-and-gold-creek/",
                                R.drawable.nelson_giveout,
                                "Brian K"));

        tdb.addTrip(new TripData("Whistler - Bike Park",
                                "http://www.trailforks.com/region/whistler-mountain-bike-park/",
                                R.drawable.whistler_bike_park,
                                "Brian K, Kay C"));

        tdb.addTrip(new TripData("North Vancouver - Seymour",
                                "http://www.trailforks.com/region/mount-seymour/",
                                R.drawable.north_vancouver_seymour,
                                "Brian K, Kay C, James W"));

        tdb.addTrip(new TripData("North Vancouver - Fromme",
                                "http://www.trailforks.com/region/mount-fromme/",
                                R.drawable.north_vancouver_fromme,
                                "Brian K, Kay C, James W, Chris W"));

        tdb.addTrip(new TripData("Fraser Valley - Burke",
                                "http://www.trailforks.com/region/burke-mountain/",
                                R.drawable.fraser_valley_burke,
                                "Brian K, Kay C, Chris W"));

        tdb.addTrip(new TripData("Fraser Valley - Sumas",
                                "http://www.trailforks.com/region/sumas-mountain/",
                                R.drawable.fraser_valley_sumas,
                                "Brian K, James W, Clayton M"));

        tdb.addTrip(new TripData("Squamish - Diamond Head",
                                "http://www.trailforks.com/region/diamond-head/",
                                R.drawable.squamish_diamondhead,
                                "Brian K, Kay C, Clayton M, James W"));

        tdb.addTrip(new TripData("Squamish - Alice Lake",
                                "http://www.trailforks.com/region/alice-lake--highlands/",
                                R.drawable.squamish_alice_lake,
                                "Brian K, Ryan B, Ashley S"));
    }
}
