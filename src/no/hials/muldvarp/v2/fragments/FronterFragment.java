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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MuldvarpService;
import no.hials.muldvarp.v2.domain.Fronter;
import no.hials.muldvarp.v2.domain.Fronter.Innlevering;

/**
 *
 * @author kb
 */
public class FronterFragment extends MuldvarpFragment {
    View fragmentView;
    int id;
    TextView displayTextMessages;
    TextView displayTextDocuments;
    TextView displayTextInnleveringer;
    
    public FronterFragment(String title, int iconResourceID, int id) {
        super.fragmentTitle = title;
        super.iconResourceID = iconResourceID;
        this.id = id;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fronterview, container, false);
            displayTextMessages = (TextView) fragmentView.findViewById(R.id.messages);
            displayTextDocuments = (TextView) fragmentView.findViewById(R.id.documents);
            displayTextInnleveringer = (TextView) fragmentView.findViewById(R.id.innleveringer);
            
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
            IntentFilter filter = new IntentFilter();
            filter.addAction(MuldvarpService.ACTION_FRONTER_UPDATE);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if(owningActivity.getService() != null) {
                        Fronter f = (Fronter)owningActivity.getService().getFronter();
                        String s = "\nMeldinger fra læreren:\n\n";
                        List<Fronter.Message> messages = f.getMessages();
                        List<Fronter.Document> documents = f.getDocuments();
                        List<Innlevering> innleveringer = f.getInnleveringer();
                        
                        for(int i = 0; i < messages.size(); i++ ) {
                            s += messages.get(i).getDate() + "\n";
                            s += messages.get(i).getMessage() + "\n";
                            s += "\n";
                        }
                        
                        displayTextMessages.setText(s);
                        
                        
                        String ss = "\nDokumenter:\n\n";
                        for(int i = 0; i < documents.size(); i++ ) {
                            ss += documents.get(i).getName() + "    Lastet opp: " + documents.get(i).getDate() + "\n";
                        }
                        
                        displayTextDocuments.setText(ss);
                        
                        
                        String sss = "\nInnleveringer:\n\n";
                        for(int i = 0; i < innleveringer.size(); i++ ) {
                            sss += innleveringer.get(i).getNavn() + "      Status: " + innleveringer.get(i).getStatus() + "\n";
                        }
                        
                        displayTextInnleveringer.setText(sss);
                    }
                }
            };
            mLocalBroadcastManager.registerReceiver(mReceiver, filter);
            
            owningActivity.getService().updateFronter();
        }
        
        return fragmentView;
    }
}
