/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MuldvarpService;
import no.hials.muldvarp.v2.domain.BibSys;

/**
 *
 * @author kb
 */
public class BibSysFragment extends MuldvarpFragment {
    View fragmentView;
    int id;
    private EditText searchBox;
    private TextView displayText;

    public BibSysFragment(String title, int iconResourceID, int id) {
        super.fragmentTitle = title;
        super.iconResourceID = iconResourceID;
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.bibsysview, container, false);
            searchBox = (EditText) fragmentView.findViewById(R.id.editText);
            displayText = (TextView) fragmentView.findViewById(R.id.displaytext);
            displayText.setMovementMethod(new ScrollingMovementMethod());
            
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
            IntentFilter filter = new IntentFilter();
            filter.addAction(MuldvarpService.ACTION_BIBSYS_UPDATE);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if(owningActivity.getService() != null) {
                        String s = "\nSøkeresultater:\n\n";
                        
                        for(int i = 0; i < owningActivity.getService().getBibSys().size(); i++ ) {
                            BibSys b = (BibSys)owningActivity.getService().getBibSys().get(i);
                            s += "Tittel:        " + b.getTitle() + "\n";
                            s += "Forfatter:  " + b.getAuthor() + "\n";
                            s += "\n";
                        }
                        
                        if(owningActivity.getService().getBibSys().isEmpty()) {
                            s += "Ingen resultater\n";
                        }

                        displayText.setText(s);
                    }
                }
            };
            mLocalBroadcastManager.registerReceiver(mReceiver, filter);
            
            
            final Button button = (Button) fragmentView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     owningActivity.getService().updateBibSys(searchBox.getText().toString());
                 }
             });
        }
        
        return fragmentView;
    }
    
}
