package com.brianmk.simplecheck_in;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rot on 2017-12-12.
 *
 *  Partner class
 *
 */

public class Partner {
    private static final String LOG_TAG = Partner.class.getSimpleName();

    private String name;
    public Boolean checked;

    public Partner() {
        this.name = "";
        this.checked = false;
    }

    public Partner(String sn, Boolean c){
        this.name = sn;
        this.checked = c;
    }

    static public List<Partner> getPartnerList() {
        List<Partner> partnerList = new LinkedList<>();

        // TODO: dream up a better way
        Partner p = new Partner("Brian Kennedy", false);
        partnerList.add(p);

        p = new Partner("Ryan Busto", false);
        partnerList.add(p);

        p = new Partner("Craig Hollinger", false);
        partnerList.add(p);

        p = new Partner("Eric Car", false);
        partnerList.add(p);

        p = new Partner("Kay Cahill", false);
        partnerList.add(p);

        p = new Partner("Jon Dewald", false);
        partnerList.add(p);

        p = new Partner("Dylan Morgan", false);
        partnerList.add(p);

        p = new Partner("James Wade", false);
        partnerList.add(p);

        p = new Partner("Chris White", false);
        partnerList.add(p);

        p = new Partner("Christine Wallace", false);
        partnerList.add(p);

        p = new Partner("Travis Hauk", false);
        partnerList.add(p);

        p = new Partner("Cari Julien", false);
        partnerList.add(p);

        p = new Partner("Sierra Leflamme", false);
        partnerList.add(p);

        p = new Partner("Chris Duppenthaler", false);
        partnerList.add(p);

        p = new Partner("Jim Jamieson", false);
        partnerList.add(p);

        p = new Partner("Charles Arnold", false);
        partnerList.add(p);

        p = new Partner("Chris George", false);
        partnerList.add(p);

        p = new Partner("Clayton MacDonald", false);
        partnerList.add(p);

        p = new Partner("Jessica Heidleman", false);
        partnerList.add(p);

        p = new Partner("Kirsten Ruecker", false);
        partnerList.add(p);

        p = new Partner("RJ Kingston", false);
        partnerList.add(p);

        p = new Partner("Marc Thibault", false);
        partnerList.add(p);

        p = new Partner("Jose Gonzales", false);
        partnerList.add(p);


        return partnerList;
    }


    public String getName() {
        return this.name;
    }

    public Boolean getChecked() { return this.checked; }
    public void setChecked(Boolean c) { this.checked = c; }
}


