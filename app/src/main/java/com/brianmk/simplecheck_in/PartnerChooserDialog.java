package com.brianmk.simplecheck_in;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rot on 2017-12-12.
 *
 *  Show this to give the user a list of people that are coming along
 *  on the trip
 *
 */

public class PartnerChooserDialog extends DialogFragment {
    final static String LOG_TAG = PartnerChooserDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final List<Partner> partnerList = Partner.getPartnerList();
        List<String> tripPartners = Arrays.asList(getArguments().getString("PARTNERS").split("\\s*,\\s*"));
        for (int i = 0; i < tripPartners.size(); i++) {
            for (int j = 0; j < partnerList.size(); j++) {
                if (tripPartners.get(i).equals(partnerList.get(j).getName())) {
                        partnerList.get(j).setChecked(true);
                }
            }
        }

        View content = getActivity().getLayoutInflater().inflate(R.layout.dialog_partner_chooser, null);
        builder.setView(content);

        final PartnerListAdapter partnerListAdapter = new PartnerListAdapter(this.getActivity(), partnerList);
        ListView partnerListView = (ListView) content.findViewById(R.id.partner_list);
        partnerListView.setAdapter(partnerListAdapter);

        partnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                partnerList.get(i).checked = !partnerList.get(i).checked;
                partnerListAdapter.notifyDataSetChanged();
            }
        });

        // Add (or subtract) partners from the list
        Button ok = (Button) content.findViewById(R.id.partner_list_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPartners = "";
                for (int i = 0; i < partnerList.size(); i++) {
                    if (partnerList.get(i).checked) {
                        newPartners = newPartners + partnerList.get(i).getName() + ", ";
                    }
                }

                // Set the "who" view (remove the final ", " while we're at it)
                ((TextView) getActivity().findViewById(R.id.trip_who)).setText(newPartners.substring(0, newPartners.length() - 2));

                dismiss();
            }
        });

        // Just go back without committing changes to partner list
        Button cancel = (Button) content.findViewById(R.id.partner_list_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

}
