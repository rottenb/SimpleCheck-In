package com.brianmk.simplecheck_in;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by rot on 2017-12-03.
 *  Quick blurb about this app
 */

public class AboutDialog extends DialogFragment {
    final static String LOG_TAG = AboutDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.dialog_about, null);

        builder.setView(content);

        Button okButton = (Button) content.findViewById(R.id.about_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
}
