/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 *
 * @author johan
 */
public class WebFragment extends MuldvarpFragment{
    
    View fragmentView;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            
            WebView webview = new WebView(getActivity().getApplicationContext());
            webview.loadUrl("http://master.uials.no/muldvarp/faces/news.xhtml");
            fragmentView = webview;
        }
        return fragmentView;
    }
    
}
