package com.brianmk.simplecheck_in;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView webText = (TextView) content.findViewById(R.id.about_website);
        webText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "http://www.cbc.ca/news";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        TextView emailText = (TextView) content.findViewById(R.id.about_email);
        emailText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String subject = "Backcountry CheckIn info requested: HI!";

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("message/rfc822");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"backcountry.checkin@gmail.com"});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

                try {
                    getActivity().startActivity(Intent.createChooser(sendIntent, "Email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    // Lolwut?
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }
}
