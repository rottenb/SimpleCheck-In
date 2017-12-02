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
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private List<TripData> tripDataList;

    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TripDataBase tripDB = new TripDataBase(this);

        tripDataList = tripDB.getAllTrips();
        TripDataAdapter tripDataAdapter = new TripDataAdapter(this, tripDataList);

        ListView tripListView = (ListView) findViewById(R.id.trip_list);
        tripListView.setAdapter(tripDataAdapter);

        registerForContextMenu(tripListView);

        // Tapping on a list item brings up the trip details overview
        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;

                DialogFragment dialog = new TripDialog();
                Bundle args = new Bundle();
                args.putString("LIST_TITLE", tripDataList.get(mPosition).getTitle());
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "Trip Details");
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
 /*       switch (item.getItemId()) {
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
  */
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

  //              mTripListAdapter.notifyDataSetChanged();
  //              tripListView.setAdapter(mTripListAdapter);

                tdb.close();

                break;
            case R.id.main_menu_create_db:
                Log.d(LOG_TAG, "CREATE DATABASE");

                Utility.populateTripDB(tdb);

    //            mTripListAdapter.notifyDataSetChanged();
    //            tripListView.setAdapter(mTripListAdapter);

                tdb.close();

                break;
            case R.id.main_menu_dump:
                List<TripData> td = tdb.getAllTrips();
      //          for (int i = 0; i < tripTitleList.size(); i++) {
      //              Log.d(LOG_TAG, td.get(i).toString());
      //          }
                tdb.close();
                break;
            default:
                Log.d(LOG_TAG, "Oops, fell through");
                tdb.close();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newTrip(View view) {
        Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
        startActivity(tripIntent);
    }

    public void editTrip(View view) {
        Intent tripIntent = new Intent(getApplicationContext(), TripDetailActivity.class);
        //tripIntent.putExtra("LIST_TITLE", tripTitleList.get(mPosition));
        startActivity(tripIntent);
    }

    public void sendTrip(View view) {
        TripDataBase tdb = new TripDataBase(this);
        //TripData tripData = tdb.getTrip(tripTitleList.get(mPosition));

        //Utility.sendMessage(this, tripData);
        tdb.close();
    }

}
