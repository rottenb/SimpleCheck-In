package com.brianmk.simplecheck_in;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.TimePicker;

/**
 * Created by rot on 2016-10-18.
 * Create a time picker dialog
 */

public class TimePickerFragment extends DialogFragment
                                implements TimePickerDialog.OnTimeSetListener {
    private static final String LOG_TAG = TimePickerFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Current time as default
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, true);

    }

    public void onTimeSet(TimePicker view, int hour, int minute) {

    }
}
