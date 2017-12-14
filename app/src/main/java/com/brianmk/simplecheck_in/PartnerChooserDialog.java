package com.brianmk.simplecheck_in;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by rot on 2017-12-12.
 *
 *  Show this to give the user a list of people that are coming along
 *  on the trip
 *
 */

public class PartnerChooserDialog extends DialogFragment {
    final static String LOG_TAG = NewTripDialog.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final List<Partner> partnerList = Partner.getPartners();
        final PartnerListAdapter partnerListAdapter = new PartnerListAdapter(this.getActivity(), partnerList);

        View content = getActivity().getLayoutInflater().inflate(R.layout.dialog_partner_chooser, null);
        builder.setView(content);

        ListView partnerListView = (ListView) content.findViewById(R.id.partner_list);
        partnerListView.setAdapter(partnerListAdapter);

        partnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                partnerList.get(i).checked = !partnerList.get(i).checked;
                partnerListAdapter.notifyDataSetChanged();
            }
        });

        Button ok = (Button) content.findViewById(R.id.partner_list_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: curate list of partners
                dismiss();
            }
        });

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
