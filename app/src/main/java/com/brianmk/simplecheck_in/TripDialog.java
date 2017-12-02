package com.brianmk.simplecheck_in;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
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

        ImageView activity_icon = (ImageView) content.findViewById(R.id.dialog_activity_icon);
        switch (td.getActivity()) {
            case 0:
                activity_icon.setImageResource(R.drawable.ic_bike_black_24dp);
                break;
            case 1:
                activity_icon.setImageResource(R.drawable.ic_ski_black_24dp);
                break;
            case 2:
                activity_icon.setImageResource(R.drawable.ic_hiking_black_24dp);
                break;
            case 3:
                activity_icon.setImageResource(R.drawable.ic_snowshoe_black_24dp);
                break;
            case 4:
                activity_icon.setImageResource(R.drawable.ic_trail_run_black_24dp);
            default:
                activity_icon.setImageResource(R.drawable.ic_walk_black_24dp);
        }

        TextView text = (TextView) content.findViewById(R.id.dialog_where);
        text.setText(td.getTitle());


        tdb.close();

        return builder.create();
    } // onCreateDialog()



} // TripDialog
