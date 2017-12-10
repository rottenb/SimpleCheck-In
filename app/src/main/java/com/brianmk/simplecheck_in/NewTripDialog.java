package com.brianmk.simplecheck_in;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by rot on 2017-12-09.
 *
 */

public class NewTripDialog extends DialogFragment {
    final static String LOG_TAG = NewTripDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.dialog_new_trip, null);

        builder.setView(content);

        Button okButton = (Button) content.findViewById(R.id.new_trip_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

}

