package com.brianmk.simplecheck_in;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rot on 2016-10-17.
 *
 * Shows trip details, gives user option to SEND or EDIT
 */

public class TripDialog extends DialogFragment {
    private static final String LOG_TAG = TripDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View content = inflater.inflate(R.layout.dialog_trip, null);
        builder.setView(content);

        String title = getArguments().getString("LIST_TITLE");
        TripDataBase tdb = new TripDataBase(getActivity());
        TripData td = tdb.getTrip(title);

        TextView text = (TextView) content.findViewById(R.id.dialog_where);
        text.setText(td.getTitle());
        text = (TextView) content.findViewById(R.id.dialog_start);
        text.setText(td.getWhenStart());
        text = (TextView) content.findViewById(R.id.dialog_end);
        text.setText(td.getWhenEnd());

        ImageView image = (ImageView) content.findViewById(R.id.dialog_map);
        image.setImageResource(td.getDrawable());

        tdb.close();

        Log.d(LOG_TAG, "onCreateDialog end");

        return builder.create();
    } // onCreateDialog()


} // TripDialog
