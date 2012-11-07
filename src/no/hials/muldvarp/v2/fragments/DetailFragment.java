/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URI;
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
                Document d = (Document)item;
                fragmentView = inflater.inflate(R.layout.metadata, container, false);
                textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
                textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
                url = d.getURI();
                break;
        }
                
        detailactivity.getActionBar().setTitle(item.getName());
        textItemTitle.setText(item.getName());
        textItemDescription.setText(item.getDescription());
        final Button button = (Button) fragmentView.findViewById(R.id.item_button1);
        
        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 System.out.println(url);
                Uri path = Uri.parse("http://docs.google.com/viewer?url=" + url);
                if (true) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    } 
                    catch (ActivityNotFoundException e) {
                        System.out.println("godamnit!");
                    }
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(path, "text/html");
                startActivity(i);
             }
         });
        
        final Button button2 = (Button) fragmentView.findViewById(R.id.item_button2);
        
        button2.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 System.out.println(url);
                Uri path = Uri.parse(url);
                if (true) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    } 
                    catch (ActivityNotFoundException e) {
                        System.out.println("godamnit!");
                    }
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(path, "text/html");
                startActivity(i);
             }
         });
        
        return fragmentView;
    }
}
