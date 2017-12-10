package com.brianmk.simplecheck_in;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by rot on 2017-12-07.
 *
 */

public class TripDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = TripDetailActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_detail);

        // This is necessary to have an img under the status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    } // onCreate()

    @Override
    protected void onResume() {
        super.onResume();

        final TripDataBase tripDataBase = new TripDataBase(this);
        final TripData tripData;

        final Intent returnIntent = new Intent();

        // Bring up data or create new
        if (getIntent().getStringExtra("TITLE") == null) {
            DialogFragment ntd = new NewTripDialog();
            ntd.show(getFragmentManager(), "Trip Details");

            tripData = new TripData();

        } else {
            tripData = tripDataBase.getTrip(getIntent().getStringExtra("TITLE"));
        }

        // Trip title
        ((EditText) findViewById(R.id.trip_title)).setText(tripData.getTitle());

        // Who's going
        ((EditText) findViewById(R.id.trip_who)).setText(tripData.getWho());

        // Activity type
        Spinner activitySpinner = (Spinner) findViewById(R.id.trip_activity);
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(this,
                R.array.activities_array, R.layout.spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(activityAdapter);
        int act = tripData.getActivity();
        activitySpinner.setSelection(act);

        // Set background as per season (ACTIVITY until I figure out DATEs)
        switch (act) {
            case TripData.BIKING_IDX:
                ((ImageView) findViewById(R.id.detail_season_img)).setImageResource(R.drawable.summer);
                break;
            case TripData.SKIING_IDX:
                ((ImageView) findViewById(R.id.detail_season_img)).setImageResource(R.drawable.winter);
                break;
            case TripData.HIKING_IDX:
                ((ImageView) findViewById(R.id.detail_season_img)).setImageResource(R.drawable.spring);
                break;
            case TripData.SNOWSHOEING_IDX:
                ((ImageView) findViewById(R.id.detail_season_img)).setImageResource(R.drawable.winter);
                break;
            case TripData.TRAIL_RUN_IDX:
                ((ImageView) findViewById(R.id.detail_season_img)).setImageResource(R.drawable.fall);
                break;
            case TripData.OTHER_ACT_IDX:
                ((ImageView) findViewById(R.id.detail_season_img)).setImageResource(R.drawable.all_seasons);
                break;
            default:
        }

        Button saveButton = (Button) findViewById(R.id.trip_save_button);

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
                Utility.sendMessage(TripDetailActivity.this, tripData);
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

    } // onResume()
}
