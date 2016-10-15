package com.brianmk.simplecheck_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private ArrayAdapter<String> mTripListAdapter;

    private ListView tripListView;

    private String[] dummyTripListData = {
            "Bellingham - Chuckanut",
            "Bellingham - Galbraith",
            "Nelson - Giveout/Gold Creek",
            "Nelson - Mountain Station",
            "Nelson - North Shore",
            "Nelson - Svoboda Road",
            "North Vancouver - Fromme",
            "North Vancouver - Seymour",
            "West Vancouver - Cypress",
            "Squamish - Alice Lake",
            "Squamish - Diamond Head",
            "Squamish - Red Heather",
            "Fraser Valley - Sumas",
            "Fraser Valley - Ledge View",
            "Whistler - Bike Park",
            "Whistler - Cheakamus",
            "Whistler - Lost Lake" };

    private int nPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        List<String> dummyTripList = new ArrayList<>(Arrays.asList(dummyTripListData));

        mTripListAdapter = new ArrayAdapter<> (this, R.layout.trip_list_item,
                                                        R.id.trip_list_item_textview,
                                                         dummyTripList );

        tripListView = (ListView) findViewById(R.id.trip_list);

        tripListView.setAdapter(mTripListAdapter);

        final Intent tripIntent = new Intent(this, TripDetailActivity.class);

        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nPosition = position;
                tripIntent.putExtra("TITLE", mTripListAdapter.getItem(position));

                startActivityForResult(tripIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {
            dummyTripListData[nPosition] = data.getStringExtra("TITLE");
            mTripListAdapter.notifyDataSetChanged();
            tripListView.setAdapter(mTripListAdapter);
        }
    }

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
            default:
                Log.d(LOG_TAG, "Oops, fell through");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
