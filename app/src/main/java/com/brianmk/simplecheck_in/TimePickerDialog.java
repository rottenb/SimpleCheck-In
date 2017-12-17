package com.brianmk.simplecheck_in;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by rot on 2016-10-18.
 * Create a time picker dialog
 */

public class TimePickerDialog extends DialogFragment
                                implements android.app.TimePickerDialog.OnTimeSetListener {
    private static final String LOG_TAG = TimePickerDialog.class.getSimpleName();

    private int resId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        resId = getArguments().getInt("RES_ID");

        Calendar t = Calendar.getInstance();

        return new android.app.TimePickerDialog(getActivity(), this,
                                                                t.get(Calendar.HOUR_OF_DAY),
                                                                t.get(Calendar.MINUTE),
                                                                true);
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {
        ((TextView) getActivity().findViewById(resId)).setText(String.format(Locale.getDefault(),
                                                            "%02d:%02d", hour, minute));
    }

}
