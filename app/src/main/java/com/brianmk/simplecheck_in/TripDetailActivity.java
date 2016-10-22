package com.brianmk.simplecheck_in;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by rot on 2016-10-14.
 *
 * Brings up dialogue to edit a given trip's data
 */

public class TripDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = TripDetailActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_edit);
        Spinner activity_spinner = (Spinner) findViewById(R.id.trip_what);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(this,
                R.array.activities_array, R.layout.spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity_spinner.setAdapter(activityAdapter);
        activity_spinner.setSelection(0);

        final int position = getIntent().getIntExtra("LIST_POSITION", 0);
        final String title = getIntent().getStringExtra("LIST_TITLE");

        final TripDataBase tripDataBase = new TripDataBase(this);

        if (position != 0) {
            setTripView(tripDataBase.getTrip(title));
        }

        Button  saveButton = (Button) findViewById(R.id.trip_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TripData tripData = tripDataBase.getTrip(position);
                setTripData(tripData);

                if (position == 0) {
                    tripDataBase.addTrip(tripData);
                } else {
                    tripData.setId(position);
                    tripDataBase.updateTrip(tripData);
                }
            }
        });

        tripDataBase.close();
    } // onCreate()

    public void showTimePicker(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "timePicker");
    } // showTimePicker()

    private void setTripView(TripData tripData) {

        EditText tripView = (EditText) findViewById(R.id.trip_title);
        tripView.setText(tripData.getTitle());
        tripView = (EditText) findViewById(R.id.trip_location);
        tripView.setText(tripData.getLocation());
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

        Button button = (Button) findViewById(R.id.trip_when_start);
        tripData.setWhenStart(button.getText().toString());
        button = (Button) findViewById(R.id.trip_when_end);
        tripData.setWhenEnd(button.getText().toString());
        button = (Button) findViewById(R.id.trip_when_panic);
        tripData.setWhenPanic(button.getText().toString());
    } // setTripData
} // TripDetailActivity
