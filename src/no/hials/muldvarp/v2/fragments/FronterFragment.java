/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.view.View;

/**
 *
 * @author kb
 */
public class FronterFragment extends MuldvarpFragment {
    View fragmentView;
    int id;

    public FronterFragment(String title, int iconResourceID, int id) {
        super.fragmentTitle = title;
        super.iconResourceID = iconResourceID;
        this.id = id;
    }
}
