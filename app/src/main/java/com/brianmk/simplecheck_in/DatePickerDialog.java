package com.brianmk.simplecheck_in;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by rot on 2017-12-15.
 *
 */

public class DatePickerDialog extends DialogFragment
                                implements android.app.DatePickerDialog.OnDateSetListener {
    private static final String LOG_TAG = DatePickerDialog.class.getSimpleName();

    private int resId;

    public interface DateTimeDialogListener {
        void onFinishDatePick(int resId);
    }

    private DateTimeDialogListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            listener = (DateTimeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DateTimeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        resId = getArguments().getInt("RES_ID");

        Calendar c = Calendar.getInstance();

        return new android.app.DatePickerDialog(getActivity(), this,
                                                            c.get(Calendar.YEAR),
                                                            c.get(Calendar.MONTH),
                                                            c.get(Calendar.DAY_OF_MONTH));
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);

        SimpleDateFormat sd = new SimpleDateFormat("MMM", Locale.getDefault());
        sd.setCalendar(cal);

        TextView date = (TextView) getActivity().findViewById(resId);
        date.setText(String.format(Locale.getDefault(), "%02d %s %04d", day,
                                                        sd.format(cal.getTime()), year));

        if (resId == R.id.start_date) {
            listener.onFinishDatePick(R.id.start_time);
        } else {
            listener.onFinishDatePick(R.id.end_time);
        }
    }
}
