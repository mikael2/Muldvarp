/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.DetailActivity;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Video;
import no.hials.muldvarp.v2.fragments.ListFragment.ListType;

/**
 *
 * @author terje
 */
public class DetailFragment extends MuldvarpFragment {
    View fragmentView;
    DetailActivity detailactivity;
    private TextView textItemTitle;
    private TextView textItemDescription;
    Domain item;
    ListType type;
    String url;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailactivity = (DetailActivity) this.owningActivity;
        
        fragmentView = inflater.inflate(R.layout.metadata, container, false);
        textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
        textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
        
        //get extra
        if(getActivity().getIntent().getExtras().containsKey("Domain")){
            item = (Domain) getActivity().getIntent().getExtras().get("Domain");
            type = (ListType) getActivity().getIntent().getExtras().get("type");
        } else {
            item = (Domain) new Document("HEY", "YOU I DONT LIKE YOUR BOYFRIEND NO WAY NO WAY");
        }
        
        switch(type) {
            case VIDEO:
                item = new Video("Videotittel", "Beskrivelse");
                fragmentView = inflater.inflate(R.layout.metadata, container, false);
                textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
                textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
                break;
            case DOCUMENT:
                item = new Document(item.getName(), item.getDescription());
                fragmentView = inflater.inflate(R.layout.metadata, container, false);
                textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
                textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
                break;
        }
                
        detailactivity.getActionBar().setTitle(item.getName());
        textItemTitle.setText(item.getName());
        textItemDescription.setText(item.getDetail());
        final Button button = (Button) fragmentView.findViewById(R.id.item_button1);
        
        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                String url = "www.vg.no";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(url), "text/html");
                startActivity(i);
             }
         });
        
        return fragmentView;
    }
    
    
}
