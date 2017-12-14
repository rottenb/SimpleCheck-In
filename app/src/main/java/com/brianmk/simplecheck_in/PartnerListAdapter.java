package com.brianmk.simplecheck_in;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rot on 2017-12-13.
 *
 *  Custom adapter for list of trip partners
 */

public class PartnerListAdapter extends ArrayAdapter<Partner> {
    final static String LOG_TAG = NewTripDialog.class.getSimpleName();

    public PartnerListAdapter(Context context, List<Partner> partners) {
        super(context, 0, partners);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get data from this position
        Partner partner = getItem(position);

        // Check if existing view is being reused, otherwise -> inflate view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.partner_list_item,
                                                parent,false);
        }

        // Get the views of the list
        TextView name = (TextView) convertView.findViewById(R.id.partner_name_short);
        ImageView checked = (ImageView) convertView.findViewById(R.id.partner_checked);

        if (partner.checked) {
            checked.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            checked.setImageResource(R.drawable.ic_circle_black_24dp);
        }

        // Populate the views with data
        name.setText(partner.getName());


        return convertView;
    }
}
