package com.brianmk.simplecheck_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by rot on 2016-10-14.
 *
 * Brings up dialogue to edit a given trip's data
 */

public class TripDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = TripDetailActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_detail);

        final EditText tripView = (EditText) findViewById(R.id.trip_title);
        tripView.setText(getIntent().getStringExtra("TITLE"));

        Button  sendNoticeButton = (Button) findViewById(R.id.trip_save_button);
        sendNoticeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("TITLE", tripView.getText().toString());
                setResult(1, data);

                Toast.makeText(getApplicationContext(), tripView.getText().toString(), Toast.LENGTH_SHORT).show();

                finish();
            }

        });
    }
}
