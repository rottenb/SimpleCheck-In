package com.brianmk.simplecheck_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

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

        setContentView(R.layout.trip_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");

        EditText tripView = (EditText) findViewById(R.id.trip_title_edit);
        tripView.setHint(title);

        tripView.setEnabled(intent.getBooleanExtra("EDITABLE", true));

        Log.d(LOG_TAG, title);
    }
}
