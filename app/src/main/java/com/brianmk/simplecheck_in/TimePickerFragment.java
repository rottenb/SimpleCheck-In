package com.brianmk.simplecheck_in;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by rot on 2016-10-18.
 * Create a time picker dialog
 */

public class TimePickerFragment extends DialogFragment
                                implements TimePickerDialog.OnTimeSetListener {
    private static final String LOG_TAG = TimePickerFragment.class.getSimpleName();

    private int resId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        resId = getArguments().getInt("WHEN_ID");

        return new TimePickerDialog(getActivity(), this, 14, 45, true);
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {

        Log.d(LOG_TAG, "time set");
        Button button = (Button) getActivity().findViewById(resId);
        button.setText(hour + ":" + minute);
        button.refreshDrawableState();

    }

}
