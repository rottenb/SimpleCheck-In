package com.brianmk.simplecheck_in;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class TripDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = TripDetailActivity.class.getSimpleName();

    TripData tripData;
    TripDataBase tripDataBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_edit);

        Spinner activity_spinner = (Spinner) findViewById(R.id.trip_activity);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(this,
                R.array.activities_array, R.layout.spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_spinner.setAdapter(activityAdapter);
        activity_spinner.setSelection(0);

        tripDataBase = new TripDataBase(this);

        if (getIntent().getStringExtra("LIST_TITLE") == null) {
            tripData = new TripData();
        } else {
            tripData = tripDataBase.getTrip(getIntent().getStringExtra("LIST_TITLE"));
        }

        setTripView(tripData);

        Button  saveButton = (Button) findViewById(R.id.trip_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tripDataBase.updateTrip(tripData);
                }
            });

        Button sendButton = (Button) findViewById(R.id.trip_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

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

    private void setTripView(TripData tripData) {
        EditText tripView = (EditText) findViewById(R.id.trip_title);
        tripView.setText(tripData.getTitle());
        tripView = (EditText) findViewById(R.id.trip_location);
        tripView.setText(tripData.getLocation());

        ImageView mapView = (ImageView) findViewById(R.id.trip_map_view);
        mapView.setImageResource(tripData.getDrawable());

        Spinner activity_spinner = (Spinner) findViewById(R.id.trip_activity);
        activity_spinner.setSelection(tripData.getActivity());

        ImageView activity_icon = (ImageView) findViewById(R.id.trip_activity_icon);
        switch (tripData.getActivity()) {
            case 0:
                activity_icon.setImageResource(R.drawable.ic_bike_black_24dp);
                break;
            case 1:
                activity_icon.setImageResource(R.drawable.ic_ski_black_24dp);
                break;
            case 2:
                activity_icon.setImageResource(R.drawable.ic_hiking_black_24dp);
                break;
            case 3:
                activity_icon.setImageResource(R.drawable.ic_snowshoe_black_24dp);
                break;
            case 4:
                activity_icon.setImageResource(R.drawable.ic_trail_run_black_24dp);
            default:
                activity_icon.setImageResource(R.drawable.ic_walk_black_24dp);
        }

        tripView = (EditText) findViewById(R.id.trip_who);
        tripView.setText(tripData.getWho());

        Button button = (Button) findViewById(R.id.trip_when_start);
        button.setText(tripData.getWhenStart());
        button = (Button) findViewById(R.id.trip_when_end);
        button.setText(tripData.getWhenEnd());
        button = (Button) findViewById(R.id.trip_when_panic);
        button.setText(tripData.getWhenPanic());
    } // setTripView

    private void setTripData(TripData tripData) {
        EditText tripView = (EditText) findViewById(R.id.trip_title);
        tripData.setTitle(tripView.getText().toString());
        tripView = (EditText) findViewById(R.id.trip_location);
        tripData.setLocation(tripView.getText().toString());
        tripView = (EditText) findViewById(R.id.trip_who);
        tripData.setWho(tripView.getText().toString());

        Spinner activity = (Spinner) findViewById(R.id.trip_activity);
        tripData.setActivity(activity.getSelectedItemPosition());

        Button button = (Button) findViewById(R.id.trip_when_start);
        tripData.setWhenStart(button.getText().toString());
        button = (Button) findViewById(R.id.trip_when_end);
        tripData.setWhenEnd(button.getText().toString());
        button = (Button) findViewById(R.id.trip_when_panic);
        tripData.setWhenPanic(button.getText().toString());
    } // setTripData
} // TripDetailActivity
