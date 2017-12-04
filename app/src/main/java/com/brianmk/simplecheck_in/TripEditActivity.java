package com.brianmk.simplecheck_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by rot on 2016-10-14.
 *
 * Brings up dialogue to edit a given trip's data
 */

public class TripEditActivity extends AppCompatActivity {
    private static final String LOG_TAG = TripEditActivity.class.getSimpleName();

    TripDataBase tripDataBase = new TripDataBase(this);
    TripData tripData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_edit);

        final Intent returnIntent = new Intent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        toolbar.setTitle("Edit Trip");

        // Bring up data or create new
        if (getIntent().getStringExtra("TITLE") == null) {
            tripData = new TripData();
        } else {
            tripData = tripDataBase.getTrip(getIntent().getStringExtra("TITLE"));
        }

        // Trip title
        EditText tripTitle = (EditText) findViewById(R.id.trip_title);
        tripTitle.setText(tripData.getTitle());

        // Who
        EditText tripWho = (EditText) findViewById(R.id.trip_who);
        tripWho.setText(tripData.getWho());

        // Map
        ImageView tripMap = (ImageView) findViewById(R.id.trip_map);
        tripMap.setBackgroundResource(tripData.getMapDrawable());

        // Activity type
        Spinner activitySpinner = (Spinner) findViewById(R.id.trip_activity);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(this,
                R.array.activities_array, R.layout.spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(activityAdapter);
        activitySpinner.setSelection(tripData.getActivity());

        final ImageView activityIcon = (ImageView) findViewById(R.id.trip_activity_icon);
        activityIcon.setBackgroundResource(tripData.getActivityIcon());

        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        activityIcon.setBackgroundResource(R.drawable.ic_bike_black_24dp);
                        break;
                    case 1:
                        activityIcon.setBackgroundResource(R.drawable.ic_ski_black_24dp);
                        break;
                    case 2:
                        activityIcon.setBackgroundResource(R.drawable.ic_hiking_black_24dp);
                        break;
                    case 3:
                        activityIcon.setBackgroundResource(R.drawable.ic_snowshoe_black_24dp);
                        break;
                    case 4:
                        activityIcon.setBackgroundResource(R.drawable.ic_trail_run_black_24dp);
                        break;
                    default:
                        activityIcon.setBackgroundResource(R.drawable.ic_walk_black_24dp);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button  saveButton = (Button) findViewById(R.id.trip_save_button);

        if (getIntent().getStringExtra("ACTION").equals("SAVE")) {
            saveButton.setText("SAVE");
        } else {
            saveButton.setText("ADD");
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Update the trip data
                tripData.setTitle(((EditText) findViewById(R.id.trip_title)).getText().toString());
                tripData.setWho(((EditText) findViewById(R.id.trip_who)).getText().toString());
                tripData.setActivity(((Spinner) findViewById(R.id.trip_activity)).getSelectedItemPosition());

                if (getIntent().getStringExtra("ACTION").equals("SAVE")) {
                    tripDataBase.updateTrip(tripData);
                    returnIntent.putExtra("ACTION", "SAVED");

                } else {
                    tripDataBase.addTrip(tripData);
                    returnIntent.putExtra("ACTION", "ADDED");
                }

                tripDataBase.close();

                setResult(RESULT_OK, returnIntent);

                finish();

                }
            });

        Button sendButton = (Button) findViewById(R.id.trip_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility.sendMessage(TripEditActivity.this, tripData);
            }
        });

        Button cancelButton = (Button) findViewById(R.id.trip_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tripDataBase.close();

                returnIntent.putExtra("ACTION", "CANCELLED");
                setResult(RESULT_OK, returnIntent);

                finish();
            }
        });

        tripDataBase.close();

    } // onCreate()

    public void showTimePicker(View view) {
        TimePickerFragment timePicker = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putInt("WHEN_ID", view.getId());

        timePicker.setArguments(args);
        timePicker.show(getFragmentManager(), "timePicker");

    } // showTimePicker()

} // TripEditActivity
