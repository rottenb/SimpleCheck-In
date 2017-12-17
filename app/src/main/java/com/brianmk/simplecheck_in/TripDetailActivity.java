package com.brianmk.simplecheck_in;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by rot on 2017-12-07.
 *
 */

public class TripDetailActivity extends AppCompatActivity implements DatePickerDialog.DateTimeDialogListener {
    private static final String LOG_TAG = TripDetailActivity.class.getSimpleName();

    // When the date picker is done, bring up the time picker
    @Override
    public void onFinishDatePick(int resId) {
        DialogFragment tripStartDialog = new TimePickerDialog();
        Bundle args = new Bundle();
        args.putInt("RES_ID", resId);
        tripStartDialog.setArguments(args);
        tripStartDialog.show(getFragmentManager(), "trip time");
    }

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
            tripData = new TripData();
            LinearLayout linLayout = (LinearLayout) findViewById(R.id.detail_linear_layout);
            linLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        } else {
            tripData = tripDataBase.getTrip(getIntent().getStringExtra("TITLE"));
        }

        ((TextView) findViewById(R.id.start_date)).setText(tripData.getStartDate());
        ((TextView) findViewById(R.id.start_time)).setText(tripData.getStartTime());

        LinearLayout tripStart = (LinearLayout) findViewById(R.id.trip_start);
        tripStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment tripStartDialog = new DatePickerDialog();
                Bundle args = new Bundle();
                args.putInt("RES_ID", R.id.start_date);
                tripStartDialog.setArguments(args);
                tripStartDialog.show(getFragmentManager(), "trip start");
            }
        });

        ((TextView) findViewById(R.id.end_date)).setText(tripData.getEndDate());
        ((TextView) findViewById(R.id.end_time)).setText(tripData.getEndTime());

        LinearLayout tripEnd = (LinearLayout) findViewById(R.id.trip_end);
        tripEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment tripEndDialog = new DatePickerDialog();
                Bundle args = new Bundle();
                args.putInt("RES_ID", R.id.end_date);
                tripEndDialog.setArguments(args);
                tripEndDialog.show(getFragmentManager(), "trip end");
            }
        });

        // Trip title
        ((EditText) findViewById(R.id.trip_title)).setText(tripData.getTitle());

        // Who's going
        ((TextView) findViewById(R.id.trip_who)).setText(tripData.getWho());
        final TextView who = (TextView) findViewById(R.id.trip_who);
        who.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment partnerDialog = new PartnerChooserDialog();
                Bundle args = new Bundle();
                args.putString("PARTNERS", who.getText().toString());
                partnerDialog.setArguments(args);
                partnerDialog.show(getFragmentManager(), "Choose Partners");
            }
        });

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

        // TODO: deal with google maps API
        ImageView mapImg = (ImageView) findViewById(R.id.trip_map_img);
        mapImg.setImageResource(tripData.getMapDrawable());

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
            tripData.setWho(((TextView) findViewById(R.id.trip_who)).getText().toString());
            tripData.setActivity(((Spinner) findViewById(R.id.trip_activity)).getSelectedItemPosition());
            tripData.setStartDate(((TextView) findViewById(R.id.start_date)).getText().toString());
            tripData.setStartTime(((TextView) findViewById(R.id.start_time)).getText().toString());
            tripData.setEndDate(((TextView) findViewById(R.id.end_date)).getText().toString());
            tripData.setEndTime(((TextView) findViewById(R.id.end_time)).getText().toString());

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
