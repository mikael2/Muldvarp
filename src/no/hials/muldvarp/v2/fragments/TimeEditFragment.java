/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

/**
 * This class is a MuldvarpFragment which represents TimeEdit 
 * @author johan
 */
public class TimeEditFragment extends MuldvarpFragment {
    
    int id;
    
    public TimeEditFragment(String title, int iconResourceID, int id) {
        super.fragmentTitle = title;
        super.iconResourceID = iconResourceID;
        this.id = id;
    }
    
    
    
}
