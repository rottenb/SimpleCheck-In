package com.brianmk.simplecheck_in;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by rot on 2016-10-14.
 *
 * Brings up dialogue to edit a given trip's data
 */

public class TripEditorActivity extends AppCompatActivity {
    private static final String LOG_TAG = TripEditorActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_editor);


        Log.d(LOG_TAG, "clicked");
    }
}
