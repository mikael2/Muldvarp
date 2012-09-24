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
import no.hials.muldvarp.v2.DetailActivity.Type;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.News;
import no.hials.muldvarp.v2.domain.Video;

/**
 *
 * @author terje
 */
public class DetailFragment extends MuldvarpFragment {
    View fragmentView;
    DetailActivity detailactivity;
    private TextView textItemTitle;
    private TextView textItemDescription;
    DetailActivity.Type type;
    Domain item;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailactivity = (DetailActivity) this.owningActivity;
        type = Type.valueOf(detailactivity.getIntent().getType());
        switch(type) {
            case NEWS:
                item = new News("Nyhetstittel", "Tekst");
                fragmentView = inflater.inflate(R.layout.v2_textlayout, container, false);
                textItemTitle = (TextView) fragmentView.findViewById(R.id.title);
                textItemDescription = (TextView) fragmentView.findViewById(R.id.text);
                break;
            case VIDEO:
                item = new Video("Videotittel", "Beskrivelse");
                fragmentView = inflater.inflate(R.layout.metadata, container, false);
                textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
                textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
                break;
            case DOCUMENTS:
                item = new Document("Dokumenttittel", "Beskrivelse");
                fragmentView = inflater.inflate(R.layout.metadata, container, false);
                textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
                textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);
                break;
        }
        System.out.println(item);
        System.out.println(item.getName());
        detailactivity.getActionBar().setTitle(item.getName());
        textItemTitle.setText(item.getName());
        textItemDescription.setText(item.getDetail());
        final Button button = (Button) fragmentView.findViewById(R.id.item_button1);
        
        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                String url = "http://www.youtube.com/watch?v=9bZkp7q19f0&feature=related";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse(url), "text/html");
                startActivity(i);
             }
         });
        
        return fragmentView;
    }
    
    
}
