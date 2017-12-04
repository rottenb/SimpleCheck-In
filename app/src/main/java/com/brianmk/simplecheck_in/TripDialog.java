package com.brianmk.simplecheck_in;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rot on 2016-10-17.
 *
 * Shows trip details, gives user option to SEND or EDIT
 */

public class TripDialog extends DialogFragment {
    private static final String LOG_TAG = TripDialog.class.getSimpleName();

    final int EDIT_REQUEST_CODE = 100;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View content = inflater.inflate(R.layout.dialog_trip, null);
        builder.setView(content);

        // Pull an entry out of the database
        final TripDataBase tdb = new TripDataBase(getActivity());

        final TripData td = tdb.getTrip(getArguments().getString("LIST_TITLE"));

        // Set dialog items as per trip entry
        ImageView activityIcon = (ImageView) content.findViewById(R.id.dialog_activity_icon);
        activityIcon.setBackgroundResource(td.getActivityIcon());

        final TextView text = (TextView) content.findViewById(R.id.dialog_where);
        text.setText(td.getTitle());

        ImageView mapImage = (ImageView) content.findViewById(R.id.dialog_map);
        mapImage.setBackgroundResource(td.getMapDrawable());

        Button editButton = (Button) content.findViewById(R.id.dialog_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent tripIntent = new Intent(getContext(), TripEditActivity.class);
                tripIntent.putExtra("TITLE", text.getText().toString());
                tripIntent.putExtra("ACTION", "SAVE");
                startActivityForResult(tripIntent, EDIT_REQUEST_CODE);
                tdb.close();
                //dismiss();
            }
        });

        Button sendButton = (Button) content.findViewById(R.id.dialog_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility.sendMessage(getActivity(), td);
                tdb.close();
                dismiss();
            }
        });

        Button viewButton = (Button) content.findViewById(R.id.dialog_details);
        viewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tdb.close();
                dismiss();
            }
        });

        tdb.close();

        return builder.create();
    } // onCreateDialog()


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDIT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getStringExtra("ACTION").equals("SAVED")) {
                    ((MainActivity)getActivity()).refreshData();
                    dismiss();
                }
            }
        }

    } // onActivityResult()
} // TripDialog
