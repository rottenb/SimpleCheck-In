package com.brianmk.simplecheck_in;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private List<TripData> tripDataList;
    private TripDataAdapter tripDataAdapter;

    //private int mPosition = 0;
    private boolean bFavouriteOnTop = false;

    final int EDIT_REQUEST_CODE = 100;

    private static int SORT_NONE = 0;
    private static int SORT_TITLE = 1;
    private static int SORT_ACTIVITY = 2;

    private int mSortType = SORT_NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is necessary to have an img under the status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        final TripDataBase tripDB = new TripDataBase(this);

        // If there's no trips, show the blank trip layout. Tapping anywhere will create the first
        //  trip.
        // If trips do exist, proceed as normal.
        tripDataList = tripDB.getAllTrips();
        if (tripDataList.size() == 0) {
            setContentView(R.layout.activity_main_blank);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            View blankTrip = findViewById(R.id.blank_trip_list);
            blankTrip.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent tripDetailIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
                    startActivityForResult(tripDetailIntent, EDIT_REQUEST_CODE);
                }
            });

            tripDB.close();
        } else {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            tripDataAdapter = new TripDataAdapter(this, tripDataList);

            ListView tripListView = (ListView) findViewById(R.id.trip_list);
            tripListView.setAdapter(tripDataAdapter);

            registerForContextMenu(tripListView);

            // Tapping on a list item brings up the trip details overview
            tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //mPosition = position;

                    //DialogFragment dialog = new TripDialog();
                    //Bundle args = new Bundle();
                    //args.putString("LIST_TITLE", tripDataList.get(position).getTitle());

                    // If failed to pull list entry from database, throw an error instead of
                    //  going further
                    String title = tripDataList.get(position).getTitle();
                    if (title == null) {
                        Toast.makeText(getApplication().getBaseContext(),
                                "ERROR! Trip [" + title + "] not found!", Toast.LENGTH_SHORT).show();
                    } else {

                        Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
                        tripIntent.putExtra("TITLE", title);
                        tripIntent.putExtra("ACTION", "SAVE");
                        startActivityForResult(tripIntent, EDIT_REQUEST_CODE);
/*
                        // Make the FAB disappear
                        final View myView = findViewById(R.id.trip_list_add);

                        // get the center for the clipping circle
                        int cx = myView.getWidth() / 2;
                        int cy = myView.getHeight() / 2;

                        // get the initial radius for the clipping circle
                        float initialRadius = (float) Math.hypot(cx, cy);

                        // create the animation (the final radius is zero)
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
                        anim.setDuration(200);

                        // make the view invisible when the animation is done
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                myView.setVisibility(View.INVISIBLE);
                            }
                        });

                        // start the animation
                        anim.start();
*/
                    }
                }
            });


            tripDB.close();
        }

    } // onCreate()

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    } // onCreateContextMenu()

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();

        switch (menuItem.getItemId()) {
            case R.id.context_menu_detail:
                Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
                tripIntent.putExtra("TITLE", tripDataList.get(menuInfo.position).getTitle());
                tripIntent.putExtra("ACTION", "SAVE");

                startActivityForResult(tripIntent, EDIT_REQUEST_CODE);
                break;
            case R.id.context_menu_send:
                Utility.sendMessage(this, tripDataList.get(menuInfo.position));
                break;
            case R.id.context_menu_delete:
                TripDataBase tbd = new TripDataBase(this);
                tbd.deleteTrip(tripDataList.get(menuInfo.position).getTitle());
                refreshData();
                tbd.close();
                if (tripDataList.size() == 0) {
                    setContentView(R.layout.activity_main_blank);
                    finish();
                    startActivity(getIntent());
                }
                break;
            default:
                Log.d(LOG_TAG, "onContextItemSelected: VIEW ITEM");
                break;
        }

        return true;
    } // onContextItemSelected()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                refreshData();
            }
        }
/*
        // Bring the FAB back
        View myView = findViewById(R.id.trip_list_add);

        // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
*/
    } // onActivityResult()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } // onCreateOptionsMenu()

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
                createNewTrip();
                break;
            case R.id.sort_title:
                mSortType = SORT_TITLE;
                refreshData();
                break;
            case R.id.sort_activity:
                mSortType = SORT_ACTIVITY;
                refreshData();
                break;
            case R.id.sort_favourites_top:
                item.setChecked(!item.isChecked());
                bFavouriteOnTop = !bFavouriteOnTop;
                refreshData();
                break;
            case R.id.main_menu_settings:
                Log.d(LOG_TAG, "user settings");
                break;
            case R.id.main_menu_about:
                DialogFragment dialog = new AboutDialog();
                dialog.show(getFragmentManager(), null);
                break;
            case R.id.main_menu_delete_db:
                // Delete all and restart the main activity with a blank layout
                tdb.deleteALLTripDB(this);
                finish();
                startActivity(getIntent());
                break;
            case R.id.main_menu_create_db:
                // Pop. database, change to pop'd layout, restart main activity
                Utility.populateTripDB(tdb);
                setContentView(R.layout.activity_main);
                finish();
                startActivity(getIntent());
                break;
            case R.id.main_menu_dump:
                for (int i = 0; i < tripDataList.size(); i++) {
                    Log.d(LOG_TAG, tripDataList.get(i).toString());
                }
                break;
            default:
                // gets called when the sub-menu gets tapped
                //  (do nothing)
                break;
        }

        tdb.close();

        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected()

    public void createNewTrip(View v) {
        Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
        tripIntent.putExtra("ACTION", "ADD");
        startActivityForResult(tripIntent, EDIT_REQUEST_CODE);
    } // createNewTrip(View)

    public void createNewTrip() {
        // If this is the first entry, change the layout and refresh
        if (tripDataList.size() == 0) {
            setContentView(R.layout.activity_main_blank);
            finish();
            startActivity(getIntent());
        }

        Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
        tripIntent.putExtra("ACTION", "ADD");
        startActivityForResult(tripIntent, EDIT_REQUEST_CODE);
    }  // createNewTrip()

    public void refreshData() {
        // Grab the new data
        TripDataBase tdb = new TripDataBase(this);
        tripDataList = tdb.getAllTrips();

        // Sort as appropriate
        if (mSortType == SORT_ACTIVITY) {
            Collections.sort(tripDataList, new TripActivityComparator());
        } else if (mSortType == SORT_TITLE) {
            Collections.sort(tripDataList, new TripTitleComparator());
        }

        if (bFavouriteOnTop) {
            Collections.sort(tripDataList, new TripFavouriteComparator());
        }

        // Update the list adapter
        tripDataAdapter.clear();
        tripDataAdapter.addAll(tripDataList);


        // Update display
        tripDataAdapter.notifyDataSetChanged();

        tdb.close();
    } // refreshData()



    public void saveSettings(View view) {
        Log.d(LOG_TAG, "SAVE SETTINGS");
    }

    public void cancelSettings(View view) {
        Log.d(LOG_TAG, "CANCEL SETTINGS");
    }
}
